package com.mitrol.sponsor.web.rest;

import com.mitrol.sponsor.SponsorApp;

import com.mitrol.sponsor.domain.BusinessActivity;
import com.mitrol.sponsor.repository.BusinessActivityRepository;
import com.mitrol.sponsor.service.BusinessActivityService;
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
    private BusinessActivityService businessActivityService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

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
            .setMessageConverters(jacksonMessageConverter).build();
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
        restBusinessActivityMockMvc.perform(post("/api/business-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessActivity)))
            .andExpect(status().isCreated());

        // Validate the BusinessActivity in the database
        List<BusinessActivity> businessActivityList = businessActivityRepository.findAll();
        assertThat(businessActivityList).hasSize(databaseSizeBeforeCreate + 1);
        BusinessActivity testBusinessActivity = businessActivityList.get(businessActivityList.size() - 1);
        assertThat(testBusinessActivity.getActivityName()).isEqualTo(DEFAULT_ACTIVITY_NAME);
    }

    @Test
    @Transactional
    public void createBusinessActivityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = businessActivityRepository.findAll().size();

        // Create the BusinessActivity with an existing ID
        businessActivity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusinessActivityMockMvc.perform(post("/api/business-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessActivity)))
            .andExpect(status().isBadRequest());

        // Validate the BusinessActivity in the database
        List<BusinessActivity> businessActivityList = businessActivityRepository.findAll();
        assertThat(businessActivityList).hasSize(databaseSizeBeforeCreate);
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
        businessActivityService.save(businessActivity);

        int databaseSizeBeforeUpdate = businessActivityRepository.findAll().size();

        // Update the businessActivity
        BusinessActivity updatedBusinessActivity = businessActivityRepository.findById(businessActivity.getId()).get();
        // Disconnect from session so that the updates on updatedBusinessActivity are not directly saved in db
        em.detach(updatedBusinessActivity);
        updatedBusinessActivity
            .activityName(UPDATED_ACTIVITY_NAME);

        restBusinessActivityMockMvc.perform(put("/api/business-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBusinessActivity)))
            .andExpect(status().isOk());

        // Validate the BusinessActivity in the database
        List<BusinessActivity> businessActivityList = businessActivityRepository.findAll();
        assertThat(businessActivityList).hasSize(databaseSizeBeforeUpdate);
        BusinessActivity testBusinessActivity = businessActivityList.get(businessActivityList.size() - 1);
        assertThat(testBusinessActivity.getActivityName()).isEqualTo(UPDATED_ACTIVITY_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingBusinessActivity() throws Exception {
        int databaseSizeBeforeUpdate = businessActivityRepository.findAll().size();

        // Create the BusinessActivity

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusinessActivityMockMvc.perform(put("/api/business-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessActivity)))
            .andExpect(status().isBadRequest());

        // Validate the BusinessActivity in the database
        List<BusinessActivity> businessActivityList = businessActivityRepository.findAll();
        assertThat(businessActivityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBusinessActivity() throws Exception {
        // Initialize the database
        businessActivityService.save(businessActivity);

        int databaseSizeBeforeDelete = businessActivityRepository.findAll().size();

        // Get the businessActivity
        restBusinessActivityMockMvc.perform(delete("/api/business-activities/{id}", businessActivity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BusinessActivity> businessActivityList = businessActivityRepository.findAll();
        assertThat(businessActivityList).hasSize(databaseSizeBeforeDelete - 1);
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
}
