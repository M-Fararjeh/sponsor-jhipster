package com.mitrol.sponsor.service;

import com.mitrol.sponsor.service.dto.BusinessActivityDTO;

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
     * @param businessActivityDTO the entity to save
     * @return the persisted entity
     */
    BusinessActivityDTO save(BusinessActivityDTO businessActivityDTO);

    /**
     * Get all the businessActivities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BusinessActivityDTO> findAll(Pageable pageable);


    /**
     * Get the "id" businessActivity.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BusinessActivityDTO> findOne(Long id);

    /**
     * Delete the "id" businessActivity.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the businessActivity corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BusinessActivityDTO> search(String query, Pageable pageable);
}
