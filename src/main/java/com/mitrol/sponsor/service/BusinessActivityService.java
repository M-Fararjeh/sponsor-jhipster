package com.mitrol.sponsor.service;

import com.mitrol.sponsor.domain.BusinessActivity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing BusinessActivity.
 */
public interface BusinessActivityService {

    /**
     * Save a businessActivity.
     *
     * @param businessActivity the entity to save
     * @return the persisted entity
     */
    BusinessActivity save(BusinessActivity businessActivity);

    /**
     * Get all the businessActivities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BusinessActivity> findAll(Pageable pageable);


    /**
     * Get the "id" businessActivity.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BusinessActivity> findOne(Long id);

    /**
     * Delete the "id" businessActivity.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
