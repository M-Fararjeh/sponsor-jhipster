package com.mitrol.sponsor.web.rest;

import com.mitrol.sponsor.SponsorApp;

import com.mitrol.sponsor.domain.BusinessActivity;
import com.mitrol.sponsor.repository.BusinessActivityRepository;
import com.mitrol.sponsor.repository.search.BusinessActivitySearchRepository;
import com.mitrol.sponsor.service.BusinessActivityService;
import com.mitrol.sponsor.service.dto.BusinessActivityDTO;
import com.mitrol.sponsor.service.mapper.BusinessActivityMapper;
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
 * Test class for the BusinessActivityResource REST controller.
 *
 * @see BusinessActivityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SponsorApp.class)
public class BusinessActivityResourceIntTest {

    private static final String DEFAULT_ACTIVITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY_NAME = "BBBBBBBBBB";

    @Autowired
    private BusinessActivityRepository businessActivityRepository;

    @Autowired
    private BusinessActivityMapper businessActivityMapper;

    @Autowired
    private BusinessActivityService businessActivityService;

    /**
     * This repository is mocked in the com.mitrol.sponsor.repository.search test package.
     *
     * @see com.mitrol.sponsor.repository.search.BusinessActivitySearchRepositoryMockConfiguration
     */
    @Autowired
    private BusinessActivitySearchRepository mockBusinessActivitySearchRepository;

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

    private MockMvc restBusinessActivityMockMvc;

    private BusinessActivity businessActivity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BusinessActivityResource businessActivityResource = new BusinessActivityResource(businessActivityService);
        this.restBusinessActivityMockMvc = MockMvcBuilders.standaloneSetup(businessActivityResource)
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
    public static BusinessActivity createEntity(EntityManager em) {
        BusinessActivity businessActivity = new BusinessActivity()
            .activityName(DEFAULT_ACTIVITY_NAME);
        return businessActivity;
    }

    @Before
    public void initTest() {
        businessActivity = createEntity(em);
    }

    @Test
    @Transactional
    public void createBusinessActivity() throws Exception {
        int databaseSizeBeforeCreate = businessActivityRepository.findAll().size();

        // Create the BusinessActivity
        BusinessActivityDTO businessActivityDTO = businessActivityMapper.toDto(businessActivity);
        restBusinessActivityMockMvc.perform(post("/api/business-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessActivityDTO)))
            .andExpect(status().isCreated());

        // Validate the BusinessActivity in the database
        List<BusinessActivity> businessActivityList = businessActivityRepository.findAll();
        assertThat(businessActivityList).hasSize(databaseSizeBeforeCreate + 1);
        BusinessActivity testBusinessActivity = businessActivityList.get(businessActivityList.size() - 1);
        assertThat(testBusinessActivity.getActivityName()).isEqualTo(DEFAULT_ACTIVITY_NAME);

        // Validate the BusinessActivity in Elasticsearch
        verify(mockBusinessActivitySearchRepository, times(1)).save(testBusinessActivity);
    }

    @Test
    @Transactional
    public void createBusinessActivityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = businessActivityRepository.findAll().size();

        // Create the BusinessActivity with an existing ID
        businessActivity.setId(1L);
        BusinessActivityDTO businessActivityDTO = businessActivityMapper.toDto(businessActivity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusinessActivityMockMvc.perform(post("/api/business-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessActivityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BusinessActivity in the database
        List<BusinessActivity> businessActivityList = businessActivityRepository.findAll();
        assertThat(businessActivityList).hasSize(databaseSizeBeforeCreate);

        // Validate the BusinessActivity in Elasticsearch
        verify(mockBusinessActivitySearchRepository, times(0)).save(businessActivity);
    }

    @Test
    @Transactional
    public void getAllBusinessActivities() throws Exception {
        // Initialize the database
        businessActivityRepository.saveAndFlush(businessActivity);

        // Get all the businessActivityList
        restBusinessActivityMockMvc.perform(get("/api/business-activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessActivity.getId().intValue())))
            .andExpect(jsonPath("$.[*].activityName").value(hasItem(DEFAULT_ACTIVITY_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getBusinessActivity() throws Exception {
        // Initialize the database
        businessActivityRepository.saveAndFlush(businessActivity);

        // Get the businessActivity
        restBusinessActivityMockMvc.perform(get("/api/business-activities/{id}", businessActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(businessActivity.getId().intValue()))
            .andExpect(jsonPath("$.activityName").value(DEFAULT_ACTIVITY_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBusinessActivity() throws Exception {
        // Get the businessActivity
        restBusinessActivityMockMvc.perform(get("/api/business-activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBusinessActivity() throws Exception {
        // Initialize the database
        businessActivityRepository.saveAndFlush(businessActivity);

        int databaseSizeBeforeUpdate = businessActivityRepository.findAll().size();

        // Update the businessActivity
        BusinessActivity updatedBusinessActivity = businessActivityRepository.findById(businessActivity.getId()).get();
        // Disconnect from session so that the updates on updatedBusinessActivity are not directly saved in db
        em.detach(updatedBusinessActivity);
        updatedBusinessActivity
            .activityName(UPDATED_ACTIVITY_NAME);
        BusinessActivityDTO businessActivityDTO = businessActivityMapper.toDto(updatedBusinessActivity);

        restBusinessActivityMockMvc.perform(put("/api/business-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessActivityDTO)))
            .andExpect(status().isOk());

        // Validate the BusinessActivity in the database
        List<BusinessActivity> businessActivityList = businessActivityRepository.findAll();
        assertThat(businessActivityList).hasSize(databaseSizeBeforeUpdate);
        BusinessActivity testBusinessActivity = businessActivityList.get(businessActivityList.size() - 1);
        assertThat(testBusinessActivity.getActivityName()).isEqualTo(UPDATED_ACTIVITY_NAME);

        // Validate the BusinessActivity in Elasticsearch
        verify(mockBusinessActivitySearchRepository, times(1)).save(testBusinessActivity);
    }

    @Test
    @Transactional
    public void updateNonExistingBusinessActivity() throws Exception {
        int databaseSizeBeforeUpdate = businessActivityRepository.findAll().size();

        // Create the BusinessActivity
        BusinessActivityDTO businessActivityDTO = businessActivityMapper.toDto(businessActivity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusinessActivityMockMvc.perform(put("/api/business-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessActivityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BusinessActivity in the database
        List<BusinessActivity> businessActivityList = businessActivityRepository.findAll();
        assertThat(businessActivityList).hasSize(databaseSizeBeforeUpdate);

        // Validate the BusinessActivity in Elasticsearch
        verify(mockBusinessActivitySearchRepository, times(0)).save(businessActivity);
    }

    @Test
    @Transactional
    public void deleteBusinessActivity() throws Exception {
        // Initialize the database
        businessActivityRepository.saveAndFlush(businessActivity);

        int databaseSizeBeforeDelete = businessActivityRepository.findAll().size();

        // Get the businessActivity
        restBusinessActivityMockMvc.perform(delete("/api/business-activities/{id}", businessActivity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BusinessActivity> businessActivityList = businessActivityRepository.findAll();
        assertThat(businessActivityList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the BusinessActivity in Elasticsearch
        verify(mockBusinessActivitySearchRepository, times(1)).deleteById(businessActivity.getId());
    }

    @Test
    @Transactional
    public void searchBusinessActivity() throws Exception {
        // Initialize the database
        businessActivityRepository.saveAndFlush(businessActivity);
        when(mockBusinessActivitySearchRepository.search(queryStringQuery("id:" + businessActivity.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(businessActivity), PageRequest.of(0, 1), 1));
        // Search the businessActivity
        restBusinessActivityMockMvc.perform(get("/api/_search/business-activities?query=id:" + businessActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessActivity.getId().intValue())))
            .andExpect(jsonPath("$.[*].activityName").value(hasItem(DEFAULT_ACTIVITY_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessActivity.class);
        BusinessActivity businessActivity1 = new BusinessActivity();
        businessActivity1.setId(1L);
        BusinessActivity businessActivity2 = new BusinessActivity();
        businessActivity2.setId(businessActivity1.getId());
        assertThat(businessActivity1).isEqualTo(businessActivity2);
        businessActivity2.setId(2L);
        assertThat(businessActivity1).isNotEqualTo(businessActivity2);
        businessActivity1.setId(null);
        assertThat(businessActivity1).isNotEqualTo(businessActivity2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessActivityDTO.class);
        BusinessActivityDTO businessActivityDTO1 = new BusinessActivityDTO();
        businessActivityDTO1.setId(1L);
        BusinessActivityDTO businessActivityDTO2 = new BusinessActivityDTO();
        assertThat(businessActivityDTO1).isNotEqualTo(businessActivityDTO2);
        businessActivityDTO2.setId(businessActivityDTO1.getId());
        assertThat(businessActivityDTO1).isEqualTo(businessActivityDTO2);
        businessActivityDTO2.setId(2L);
        assertThat(businessActivityDTO1).isNotEqualTo(businessActivityDTO2);
        businessActivityDTO1.setId(null);
        assertThat(businessActivityDTO1).isNotEqualTo(businessActivityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(businessActivityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(businessActivityMapper.fromId(null)).isNull();
    }
}
