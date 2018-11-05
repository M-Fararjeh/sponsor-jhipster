package com.mitrol.sponsor.web.rest;

import com.mitrol.sponsor.SponsorApp;

import com.mitrol.sponsor.domain.Sponsor;
import com.mitrol.sponsor.repository.SponsorRepository;
import com.mitrol.sponsor.repository.search.SponsorSearchRepository;
import com.mitrol.sponsor.service.SponsorService;
import com.mitrol.sponsor.service.dto.SponsorDTO;
import com.mitrol.sponsor.service.mapper.SponsorMapper;
import com.mitrol.sponsor.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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

import javax.persistence.EntityManager;
import java.util.ArrayList;
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
 * Test class for the SponsorResource REST controller.
 *
 * @see SponsorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SponsorApp.class)
public class SponsorResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_HOME_PAGE = "AAAAAAAAAA";
    private static final String UPDATED_HOME_PAGE = "BBBBBBBBBB";

    @Autowired
    private SponsorRepository sponsorRepository;

    @Mock
    private SponsorRepository sponsorRepositoryMock;

    @Autowired
    private SponsorMapper sponsorMapper;
    

    @Mock
    private SponsorService sponsorServiceMock;

    @Autowired
    private SponsorService sponsorService;

    /**
     * This repository is mocked in the com.mitrol.sponsor.repository.search test package.
     *
     * @see com.mitrol.sponsor.repository.search.SponsorSearchRepositoryMockConfiguration
     */
    @Autowired
    private SponsorSearchRepository mockSponsorSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSponsorMockMvc;

    private Sponsor sponsor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SponsorResource sponsorResource = new SponsorResource(sponsorService);
        this.restSponsorMockMvc = MockMvcBuilders.standaloneSetup(sponsorResource)
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
    public static Sponsor createEntity(EntityManager em) {
        Sponsor sponsor = new Sponsor()
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS)
            .city(DEFAULT_CITY)
            .region(DEFAULT_REGION)
            .postalCode(DEFAULT_POSTAL_CODE)
            .country(DEFAULT_COUNTRY)
            .phone(DEFAULT_PHONE)
            .fax(DEFAULT_FAX)
            .homePage(DEFAULT_HOME_PAGE);
        return sponsor;
    }

    @Before
    public void initTest() {
        sponsor = createEntity(em);
    }

    @Test
    @Transactional
    public void createSponsor() throws Exception {
        int databaseSizeBeforeCreate = sponsorRepository.findAll().size();

        // Create the Sponsor
        SponsorDTO sponsorDTO = sponsorMapper.toDto(sponsor);
        restSponsorMockMvc.perform(post("/api/sponsors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sponsorDTO)))
            .andExpect(status().isCreated());

        // Validate the Sponsor in the database
        List<Sponsor> sponsorList = sponsorRepository.findAll();
        assertThat(sponsorList).hasSize(databaseSizeBeforeCreate + 1);
        Sponsor testSponsor = sponsorList.get(sponsorList.size() - 1);
        assertThat(testSponsor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSponsor.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testSponsor.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testSponsor.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testSponsor.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testSponsor.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testSponsor.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testSponsor.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testSponsor.getHomePage()).isEqualTo(DEFAULT_HOME_PAGE);

        // Validate the Sponsor in Elasticsearch
        verify(mockSponsorSearchRepository, times(1)).save(testSponsor);
    }

    @Test
    @Transactional
    public void createSponsorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sponsorRepository.findAll().size();

        // Create the Sponsor with an existing ID
        sponsor.setId(1L);
        SponsorDTO sponsorDTO = sponsorMapper.toDto(sponsor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSponsorMockMvc.perform(post("/api/sponsors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sponsorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sponsor in the database
        List<Sponsor> sponsorList = sponsorRepository.findAll();
        assertThat(sponsorList).hasSize(databaseSizeBeforeCreate);

        // Validate the Sponsor in Elasticsearch
        verify(mockSponsorSearchRepository, times(0)).save(sponsor);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sponsorRepository.findAll().size();
        // set the field null
        sponsor.setName(null);

        // Create the Sponsor, which fails.
        SponsorDTO sponsorDTO = sponsorMapper.toDto(sponsor);

        restSponsorMockMvc.perform(post("/api/sponsors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sponsorDTO)))
            .andExpect(status().isBadRequest());

        List<Sponsor> sponsorList = sponsorRepository.findAll();
        assertThat(sponsorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSponsors() throws Exception {
        // Initialize the database
        sponsorRepository.saveAndFlush(sponsor);

        // Get all the sponsorList
        restSponsorMockMvc.perform(get("/api/sponsors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sponsor.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX.toString())))
            .andExpect(jsonPath("$.[*].homePage").value(hasItem(DEFAULT_HOME_PAGE.toString())));
    }
    
    public void getAllSponsorsWithEagerRelationshipsIsEnabled() throws Exception {
        SponsorResource sponsorResource = new SponsorResource(sponsorServiceMock);
        when(sponsorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restSponsorMockMvc = MockMvcBuilders.standaloneSetup(sponsorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restSponsorMockMvc.perform(get("/api/sponsors?eagerload=true"))
        .andExpect(status().isOk());

        verify(sponsorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllSponsorsWithEagerRelationshipsIsNotEnabled() throws Exception {
        SponsorResource sponsorResource = new SponsorResource(sponsorServiceMock);
            when(sponsorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restSponsorMockMvc = MockMvcBuilders.standaloneSetup(sponsorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restSponsorMockMvc.perform(get("/api/sponsors?eagerload=true"))
        .andExpect(status().isOk());

            verify(sponsorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getSponsor() throws Exception {
        // Initialize the database
        sponsorRepository.saveAndFlush(sponsor);

        // Get the sponsor
        restSponsorMockMvc.perform(get("/api/sponsors/{id}", sponsor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sponsor.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX.toString()))
            .andExpect(jsonPath("$.homePage").value(DEFAULT_HOME_PAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSponsor() throws Exception {
        // Get the sponsor
        restSponsorMockMvc.perform(get("/api/sponsors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSponsor() throws Exception {
        // Initialize the database
        sponsorRepository.saveAndFlush(sponsor);

        int databaseSizeBeforeUpdate = sponsorRepository.findAll().size();

        // Update the sponsor
        Sponsor updatedSponsor = sponsorRepository.findById(sponsor.getId()).get();
        // Disconnect from session so that the updates on updatedSponsor are not directly saved in db
        em.detach(updatedSponsor);
        updatedSponsor
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .region(UPDATED_REGION)
            .postalCode(UPDATED_POSTAL_CODE)
            .country(UPDATED_COUNTRY)
            .phone(UPDATED_PHONE)
            .fax(UPDATED_FAX)
            .homePage(UPDATED_HOME_PAGE);
        SponsorDTO sponsorDTO = sponsorMapper.toDto(updatedSponsor);

        restSponsorMockMvc.perform(put("/api/sponsors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sponsorDTO)))
            .andExpect(status().isOk());

        // Validate the Sponsor in the database
        List<Sponsor> sponsorList = sponsorRepository.findAll();
        assertThat(sponsorList).hasSize(databaseSizeBeforeUpdate);
        Sponsor testSponsor = sponsorList.get(sponsorList.size() - 1);
        assertThat(testSponsor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSponsor.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testSponsor.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testSponsor.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testSponsor.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testSponsor.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testSponsor.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testSponsor.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testSponsor.getHomePage()).isEqualTo(UPDATED_HOME_PAGE);

        // Validate the Sponsor in Elasticsearch
        verify(mockSponsorSearchRepository, times(1)).save(testSponsor);
    }

    @Test
    @Transactional
    public void updateNonExistingSponsor() throws Exception {
        int databaseSizeBeforeUpdate = sponsorRepository.findAll().size();

        // Create the Sponsor
        SponsorDTO sponsorDTO = sponsorMapper.toDto(sponsor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSponsorMockMvc.perform(put("/api/sponsors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sponsorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sponsor in the database
        List<Sponsor> sponsorList = sponsorRepository.findAll();
        assertThat(sponsorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Sponsor in Elasticsearch
        verify(mockSponsorSearchRepository, times(0)).save(sponsor);
    }

    @Test
    @Transactional
    public void deleteSponsor() throws Exception {
        // Initialize the database
        sponsorRepository.saveAndFlush(sponsor);

        int databaseSizeBeforeDelete = sponsorRepository.findAll().size();

        // Get the sponsor
        restSponsorMockMvc.perform(delete("/api/sponsors/{id}", sponsor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Sponsor> sponsorList = sponsorRepository.findAll();
        assertThat(sponsorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Sponsor in Elasticsearch
        verify(mockSponsorSearchRepository, times(1)).deleteById(sponsor.getId());
    }

    @Test
    @Transactional
    public void searchSponsor() throws Exception {
        // Initialize the database
        sponsorRepository.saveAndFlush(sponsor);
        when(mockSponsorSearchRepository.search(queryStringQuery("id:" + sponsor.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(sponsor), PageRequest.of(0, 1), 1));
        // Search the sponsor
        restSponsorMockMvc.perform(get("/api/_search/sponsors?query=id:" + sponsor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sponsor.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX.toString())))
            .andExpect(jsonPath("$.[*].homePage").value(hasItem(DEFAULT_HOME_PAGE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sponsor.class);
        Sponsor sponsor1 = new Sponsor();
        sponsor1.setId(1L);
        Sponsor sponsor2 = new Sponsor();
        sponsor2.setId(sponsor1.getId());
        assertThat(sponsor1).isEqualTo(sponsor2);
        sponsor2.setId(2L);
        assertThat(sponsor1).isNotEqualTo(sponsor2);
        sponsor1.setId(null);
        assertThat(sponsor1).isNotEqualTo(sponsor2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SponsorDTO.class);
        SponsorDTO sponsorDTO1 = new SponsorDTO();
        sponsorDTO1.setId(1L);
        SponsorDTO sponsorDTO2 = new SponsorDTO();
        assertThat(sponsorDTO1).isNotEqualTo(sponsorDTO2);
        sponsorDTO2.setId(sponsorDTO1.getId());
        assertThat(sponsorDTO1).isEqualTo(sponsorDTO2);
        sponsorDTO2.setId(2L);
        assertThat(sponsorDTO1).isNotEqualTo(sponsorDTO2);
        sponsorDTO1.setId(null);
        assertThat(sponsorDTO1).isNotEqualTo(sponsorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sponsorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sponsorMapper.fromId(null)).isNull();
    }
}
