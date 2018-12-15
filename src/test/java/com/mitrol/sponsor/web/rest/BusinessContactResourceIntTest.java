package com.mitrol.sponsor.web.rest;

import com.mitrol.sponsor.SponsorApp;

import com.mitrol.sponsor.domain.BusinessContact;
import com.mitrol.sponsor.repository.BusinessContactRepository;
import com.mitrol.sponsor.repository.search.BusinessContactSearchRepository;
import com.mitrol.sponsor.service.BusinessContactService;
import com.mitrol.sponsor.service.dto.BusinessContactDTO;
import com.mitrol.sponsor.service.mapper.BusinessContactMapper;
import com.mitrol.sponsor.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static com.mitrol.sponsor.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BusinessContactResource REST controller.
 *
 * @see BusinessContactResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SponsorApp.class)
public class BusinessContactResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PERSONAL_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PERSONAL_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_WORK_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_WORK_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private BusinessContactRepository businessContactRepository;

    @Autowired
    private BusinessContactMapper businessContactMapper;

    @Autowired
    private BusinessContactService businessContactService;

    /**
     * This repository is mocked in the com.mitrol.sponsor.repository.search test package.
     *
     * @see com.mitrol.sponsor.repository.search.BusinessContactSearchRepositoryMockConfiguration
     */
    @Autowired
    private BusinessContactSearchRepository mockBusinessContactSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restBusinessContactMockMvc;

    private BusinessContact businessContact;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BusinessContactResource businessContactResource = new BusinessContactResource(businessContactService);
        this.restBusinessContactMockMvc = MockMvcBuilders.standaloneSetup(businessContactResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BusinessContact createEntity(EntityManager em) {
        BusinessContact businessContact = new BusinessContact()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .personalPhone(DEFAULT_PERSONAL_PHONE)
            .workPhone(DEFAULT_WORK_PHONE)
            .email(DEFAULT_EMAIL);
        return businessContact;
    }

    @Before
    public void initTest() {
        businessContact = createEntity(em);
    }

    @Test
    @Transactional
    public void createBusinessContact() throws Exception {
        int databaseSizeBeforeCreate = businessContactRepository.findAll().size();

        // Create the BusinessContact
        BusinessContactDTO businessContactDTO = businessContactMapper.toDto(businessContact);
        restBusinessContactMockMvc.perform(post("/api/business-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessContactDTO)))
            .andExpect(status().isCreated());

        // Validate the BusinessContact in the database
        List<BusinessContact> businessContactList = businessContactRepository.findAll();
        assertThat(businessContactList).hasSize(databaseSizeBeforeCreate + 1);
        BusinessContact testBusinessContact = businessContactList.get(businessContactList.size() - 1);
        assertThat(testBusinessContact.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testBusinessContact.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testBusinessContact.getPersonalPhone()).isEqualTo(DEFAULT_PERSONAL_PHONE);
        assertThat(testBusinessContact.getWorkPhone()).isEqualTo(DEFAULT_WORK_PHONE);
        assertThat(testBusinessContact.getEmail()).isEqualTo(DEFAULT_EMAIL);

        // Validate the BusinessContact in Elasticsearch
        verify(mockBusinessContactSearchRepository, times(1)).save(testBusinessContact);
    }

    @Test
    @Transactional
    public void createBusinessContactWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = businessContactRepository.findAll().size();

        // Create the BusinessContact with an existing ID
        businessContact.setId(1L);
        BusinessContactDTO businessContactDTO = businessContactMapper.toDto(businessContact);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusinessContactMockMvc.perform(post("/api/business-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessContactDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BusinessContact in the database
        List<BusinessContact> businessContactList = businessContactRepository.findAll();
        assertThat(businessContactList).hasSize(databaseSizeBeforeCreate);

        // Validate the BusinessContact in Elasticsearch
        verify(mockBusinessContactSearchRepository, times(0)).save(businessContact);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = businessContactRepository.findAll().size();
        // set the field null
        businessContact.setFirstName(null);

        // Create the BusinessContact, which fails.
        BusinessContactDTO businessContactDTO = businessContactMapper.toDto(businessContact);

        restBusinessContactMockMvc.perform(post("/api/business-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessContactDTO)))
            .andExpect(status().isBadRequest());

        List<BusinessContact> businessContactList = businessContactRepository.findAll();
        assertThat(businessContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = businessContactRepository.findAll().size();
        // set the field null
        businessContact.setLastName(null);

        // Create the BusinessContact, which fails.
        BusinessContactDTO businessContactDTO = businessContactMapper.toDto(businessContact);

        restBusinessContactMockMvc.perform(post("/api/business-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessContactDTO)))
            .andExpect(status().isBadRequest());

        List<BusinessContact> businessContactList = businessContactRepository.findAll();
        assertThat(businessContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBusinessContacts() throws Exception {
        // Initialize the database
        businessContactRepository.saveAndFlush(businessContact);

        // Get all the businessContactList
        restBusinessContactMockMvc.perform(get("/api/business-contacts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].personalPhone").value(hasItem(DEFAULT_PERSONAL_PHONE.toString())))
            .andExpect(jsonPath("$.[*].workPhone").value(hasItem(DEFAULT_WORK_PHONE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }
    
    @Test
    @Transactional
    public void getBusinessContact() throws Exception {
        // Initialize the database
        businessContactRepository.saveAndFlush(businessContact);

        // Get the businessContact
        restBusinessContactMockMvc.perform(get("/api/business-contacts/{id}", businessContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(businessContact.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.personalPhone").value(DEFAULT_PERSONAL_PHONE.toString()))
            .andExpect(jsonPath("$.workPhone").value(DEFAULT_WORK_PHONE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBusinessContact() throws Exception {
        // Get the businessContact
        restBusinessContactMockMvc.perform(get("/api/business-contacts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBusinessContact() throws Exception {
        // Initialize the database
        businessContactRepository.saveAndFlush(businessContact);

        int databaseSizeBeforeUpdate = businessContactRepository.findAll().size();

        // Update the businessContact
        BusinessContact updatedBusinessContact = businessContactRepository.findById(businessContact.getId()).get();
        // Disconnect from session so that the updates on updatedBusinessContact are not directly saved in db
        em.detach(updatedBusinessContact);
        updatedBusinessContact
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .personalPhone(UPDATED_PERSONAL_PHONE)
            .workPhone(UPDATED_WORK_PHONE)
            .email(UPDATED_EMAIL);
        BusinessContactDTO businessContactDTO = businessContactMapper.toDto(updatedBusinessContact);

        restBusinessContactMockMvc.perform(put("/api/business-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessContactDTO)))
            .andExpect(status().isOk());

        // Validate the BusinessContact in the database
        List<BusinessContact> businessContactList = businessContactRepository.findAll();
        assertThat(businessContactList).hasSize(databaseSizeBeforeUpdate);
        BusinessContact testBusinessContact = businessContactList.get(businessContactList.size() - 1);
        assertThat(testBusinessContact.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testBusinessContact.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testBusinessContact.getPersonalPhone()).isEqualTo(UPDATED_PERSONAL_PHONE);
        assertThat(testBusinessContact.getWorkPhone()).isEqualTo(UPDATED_WORK_PHONE);
        assertThat(testBusinessContact.getEmail()).isEqualTo(UPDATED_EMAIL);

        // Validate the BusinessContact in Elasticsearch
        verify(mockBusinessContactSearchRepository, times(1)).save(testBusinessContact);
    }

    @Test
    @Transactional
    public void updateNonExistingBusinessContact() throws Exception {
        int databaseSizeBeforeUpdate = businessContactRepository.findAll().size();

        // Create the BusinessContact
        BusinessContactDTO businessContactDTO = businessContactMapper.toDto(businessContact);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusinessContactMockMvc.perform(put("/api/business-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessContactDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BusinessContact in the database
        List<BusinessContact> businessContactList = businessContactRepository.findAll();
        assertThat(businessContactList).hasSize(databaseSizeBeforeUpdate);

        // Validate the BusinessContact in Elasticsearch
        verify(mockBusinessContactSearchRepository, times(0)).save(businessContact);
    }

    @Test
    @Transactional
    public void deleteBusinessContact() throws Exception {
        // Initialize the database
        businessContactRepository.saveAndFlush(businessContact);

        int databaseSizeBeforeDelete = businessContactRepository.findAll().size();

        // Get the businessContact
        restBusinessContactMockMvc.perform(delete("/api/business-contacts/{id}", businessContact.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BusinessContact> businessContactList = businessContactRepository.findAll();
        assertThat(businessContactList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the BusinessContact in Elasticsearch
        verify(mockBusinessContactSearchRepository, times(1)).deleteById(businessContact.getId());
    }

    @Test
    @Transactional
    public void searchBusinessContact() throws Exception {
        // Initialize the database
        businessContactRepository.saveAndFlush(businessContact);
        when(mockBusinessContactSearchRepository.search(queryStringQuery("id:" + businessContact.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(businessContact), PageRequest.of(0, 1), 1));
        // Search the businessContact
        restBusinessContactMockMvc.perform(get("/api/_search/business-contacts?query=id:" + businessContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].personalPhone").value(hasItem(DEFAULT_PERSONAL_PHONE)))
            .andExpect(jsonPath("$.[*].workPhone").value(hasItem(DEFAULT_WORK_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessContact.class);
        BusinessContact businessContact1 = new BusinessContact();
        businessContact1.setId(1L);
        BusinessContact businessContact2 = new BusinessContact();
        businessContact2.setId(businessContact1.getId());
        assertThat(businessContact1).isEqualTo(businessContact2);
        businessContact2.setId(2L);
        assertThat(businessContact1).isNotEqualTo(businessContact2);
        businessContact1.setId(null);
        assertThat(businessContact1).isNotEqualTo(businessContact2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessContactDTO.class);
        BusinessContactDTO businessContactDTO1 = new BusinessContactDTO();
        businessContactDTO1.setId(1L);
        BusinessContactDTO businessContactDTO2 = new BusinessContactDTO();
        assertThat(businessContactDTO1).isNotEqualTo(businessContactDTO2);
        businessContactDTO2.setId(businessContactDTO1.getId());
        assertThat(businessContactDTO1).isEqualTo(businessContactDTO2);
        businessContactDTO2.setId(2L);
        assertThat(businessContactDTO1).isNotEqualTo(businessContactDTO2);
        businessContactDTO1.setId(null);
        assertThat(businessContactDTO1).isNotEqualTo(businessContactDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(businessContactMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(businessContactMapper.fromId(null)).isNull();
    }
}
