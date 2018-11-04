package com.mitrol.sponsor.web.rest;

import com.mitrol.sponsor.SponsorApp;

import com.mitrol.sponsor.domain.BusinessContactProfile;
import com.mitrol.sponsor.repository.BusinessContactProfileRepository;
import com.mitrol.sponsor.service.BusinessContactProfileService;
import com.mitrol.sponsor.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.mitrol.sponsor.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BusinessContactProfileResource REST controller.
 *
 * @see BusinessContactProfileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SponsorApp.class)
public class BusinessContactProfileResourceIntTest {

    private static final String DEFAULT_ATTENDING = "AAAAAAAAAA";
    private static final String UPDATED_ATTENDING = "BBBBBBBBBB";

    private static final String DEFAULT_RETENTION = "AAAAAAAAAA";
    private static final String UPDATED_RETENTION = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_SERVICE = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_SERVICE = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_SERVICE_SPECIAL = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_SERVICE_SPECIAL = "BBBBBBBBBB";

    @Autowired
    private BusinessContactProfileRepository businessContactProfileRepository;
    
    @Autowired
    private BusinessContactProfileService businessContactProfileService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBusinessContactProfileMockMvc;

    private BusinessContactProfile businessContactProfile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BusinessContactProfileResource businessContactProfileResource = new BusinessContactProfileResource(businessContactProfileService);
        this.restBusinessContactProfileMockMvc = MockMvcBuilders.standaloneSetup(businessContactProfileResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BusinessContactProfile createEntity(EntityManager em) {
        BusinessContactProfile businessContactProfile = new BusinessContactProfile()
            .attending(DEFAULT_ATTENDING)
            .retention(DEFAULT_RETENTION)
            .customerService(DEFAULT_CUSTOMER_SERVICE)
            .customerServiceSpecial(DEFAULT_CUSTOMER_SERVICE_SPECIAL);
        return businessContactProfile;
    }

    @Before
    public void initTest() {
        businessContactProfile = createEntity(em);
    }

    @Test
    @Transactional
    public void createBusinessContactProfile() throws Exception {
        int databaseSizeBeforeCreate = businessContactProfileRepository.findAll().size();

        // Create the BusinessContactProfile
        restBusinessContactProfileMockMvc.perform(post("/api/business-contact-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessContactProfile)))
            .andExpect(status().isCreated());

        // Validate the BusinessContactProfile in the database
        List<BusinessContactProfile> businessContactProfileList = businessContactProfileRepository.findAll();
        assertThat(businessContactProfileList).hasSize(databaseSizeBeforeCreate + 1);
        BusinessContactProfile testBusinessContactProfile = businessContactProfileList.get(businessContactProfileList.size() - 1);
        assertThat(testBusinessContactProfile.getAttending()).isEqualTo(DEFAULT_ATTENDING);
        assertThat(testBusinessContactProfile.getRetention()).isEqualTo(DEFAULT_RETENTION);
        assertThat(testBusinessContactProfile.getCustomerService()).isEqualTo(DEFAULT_CUSTOMER_SERVICE);
        assertThat(testBusinessContactProfile.getCustomerServiceSpecial()).isEqualTo(DEFAULT_CUSTOMER_SERVICE_SPECIAL);
    }

    @Test
    @Transactional
    public void createBusinessContactProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = businessContactProfileRepository.findAll().size();

        // Create the BusinessContactProfile with an existing ID
        businessContactProfile.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusinessContactProfileMockMvc.perform(post("/api/business-contact-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessContactProfile)))
            .andExpect(status().isBadRequest());

        // Validate the BusinessContactProfile in the database
        List<BusinessContactProfile> businessContactProfileList = businessContactProfileRepository.findAll();
        assertThat(businessContactProfileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBusinessContactProfiles() throws Exception {
        // Initialize the database
        businessContactProfileRepository.saveAndFlush(businessContactProfile);

        // Get all the businessContactProfileList
        restBusinessContactProfileMockMvc.perform(get("/api/business-contact-profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessContactProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].attending").value(hasItem(DEFAULT_ATTENDING.toString())))
            .andExpect(jsonPath("$.[*].retention").value(hasItem(DEFAULT_RETENTION.toString())))
            .andExpect(jsonPath("$.[*].customerService").value(hasItem(DEFAULT_CUSTOMER_SERVICE.toString())))
            .andExpect(jsonPath("$.[*].customerServiceSpecial").value(hasItem(DEFAULT_CUSTOMER_SERVICE_SPECIAL.toString())));
    }
    
    @Test
    @Transactional
    public void getBusinessContactProfile() throws Exception {
        // Initialize the database
        businessContactProfileRepository.saveAndFlush(businessContactProfile);

        // Get the businessContactProfile
        restBusinessContactProfileMockMvc.perform(get("/api/business-contact-profiles/{id}", businessContactProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(businessContactProfile.getId().intValue()))
            .andExpect(jsonPath("$.attending").value(DEFAULT_ATTENDING.toString()))
            .andExpect(jsonPath("$.retention").value(DEFAULT_RETENTION.toString()))
            .andExpect(jsonPath("$.customerService").value(DEFAULT_CUSTOMER_SERVICE.toString()))
            .andExpect(jsonPath("$.customerServiceSpecial").value(DEFAULT_CUSTOMER_SERVICE_SPECIAL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBusinessContactProfile() throws Exception {
        // Get the businessContactProfile
        restBusinessContactProfileMockMvc.perform(get("/api/business-contact-profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBusinessContactProfile() throws Exception {
        // Initialize the database
        businessContactProfileService.save(businessContactProfile);

        int databaseSizeBeforeUpdate = businessContactProfileRepository.findAll().size();

        // Update the businessContactProfile
        BusinessContactProfile updatedBusinessContactProfile = businessContactProfileRepository.findById(businessContactProfile.getId()).get();
        // Disconnect from session so that the updates on updatedBusinessContactProfile are not directly saved in db
        em.detach(updatedBusinessContactProfile);
        updatedBusinessContactProfile
            .attending(UPDATED_ATTENDING)
            .retention(UPDATED_RETENTION)
            .customerService(UPDATED_CUSTOMER_SERVICE)
            .customerServiceSpecial(UPDATED_CUSTOMER_SERVICE_SPECIAL);

        restBusinessContactProfileMockMvc.perform(put("/api/business-contact-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBusinessContactProfile)))
            .andExpect(status().isOk());

        // Validate the BusinessContactProfile in the database
        List<BusinessContactProfile> businessContactProfileList = businessContactProfileRepository.findAll();
        assertThat(businessContactProfileList).hasSize(databaseSizeBeforeUpdate);
        BusinessContactProfile testBusinessContactProfile = businessContactProfileList.get(businessContactProfileList.size() - 1);
        assertThat(testBusinessContactProfile.getAttending()).isEqualTo(UPDATED_ATTENDING);
        assertThat(testBusinessContactProfile.getRetention()).isEqualTo(UPDATED_RETENTION);
        assertThat(testBusinessContactProfile.getCustomerService()).isEqualTo(UPDATED_CUSTOMER_SERVICE);
        assertThat(testBusinessContactProfile.getCustomerServiceSpecial()).isEqualTo(UPDATED_CUSTOMER_SERVICE_SPECIAL);
    }

    @Test
    @Transactional
    public void updateNonExistingBusinessContactProfile() throws Exception {
        int databaseSizeBeforeUpdate = businessContactProfileRepository.findAll().size();

        // Create the BusinessContactProfile

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusinessContactProfileMockMvc.perform(put("/api/business-contact-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessContactProfile)))
            .andExpect(status().isBadRequest());

        // Validate the BusinessContactProfile in the database
        List<BusinessContactProfile> businessContactProfileList = businessContactProfileRepository.findAll();
        assertThat(businessContactProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBusinessContactProfile() throws Exception {
        // Initialize the database
        businessContactProfileService.save(businessContactProfile);

        int databaseSizeBeforeDelete = businessContactProfileRepository.findAll().size();

        // Get the businessContactProfile
        restBusinessContactProfileMockMvc.perform(delete("/api/business-contact-profiles/{id}", businessContactProfile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BusinessContactProfile> businessContactProfileList = businessContactProfileRepository.findAll();
        assertThat(businessContactProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessContactProfile.class);
        BusinessContactProfile businessContactProfile1 = new BusinessContactProfile();
        businessContactProfile1.setId(1L);
        BusinessContactProfile businessContactProfile2 = new BusinessContactProfile();
        businessContactProfile2.setId(businessContactProfile1.getId());
        assertThat(businessContactProfile1).isEqualTo(businessContactProfile2);
        businessContactProfile2.setId(2L);
        assertThat(businessContactProfile1).isNotEqualTo(businessContactProfile2);
        businessContactProfile1.setId(null);
        assertThat(businessContactProfile1).isNotEqualTo(businessContactProfile2);
    }
}
