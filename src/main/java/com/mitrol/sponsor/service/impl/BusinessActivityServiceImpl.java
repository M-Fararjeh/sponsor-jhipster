package com.mitrol.sponsor.service.impl;

import com.mitrol.sponsor.service.BusinessActivityService;
import com.mitrol.sponsor.domain.BusinessActivity;
import com.mitrol.sponsor.repository.BusinessActivityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing BusinessActivity.
 */
@Service
@Transactional
public class BusinessActivityServiceImpl implements BusinessActivityService {

    private final Logger log = LoggerFactory.getLogger(BusinessActivityServiceImpl.class);

    private final BusinessActivityRepository businessActivityRepository;

    public BusinessActivityServiceImpl(BusinessActivityRepository businessActivityRepository) {
        this.businessActivityRepository = businessActivityRepository;
    }

    /**
     * Save a businessActivity.
     *
     * @param businessActivity the entity to save
     * @return the persisted entity
     */
    @Override
    public BusinessActivity save(BusinessActivity businessActivity) {
        log.debug("Request to save BusinessActivity : {}", businessActivity);
        return businessActivityRepository.save(businessActivity);
    }

    /**
     * Get all the businessActivities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BusinessActivity> findAll(Pageable pageable) {
        log.debug("Request to get all BusinessActivities");
        return businessActivityRepository.findAll(pageable);
    }


    /**
     * Get one businessActivity by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BusinessActivity> findOne(Long id) {
        log.debug("Request to get BusinessActivity : {}", id);
        return businessActivityRepository.findById(id);
    }

    /**
     * Delete the businessActivity by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BusinessActivity : {}", id);
        businessActivityRepository.deleteById(id);
    }
}
