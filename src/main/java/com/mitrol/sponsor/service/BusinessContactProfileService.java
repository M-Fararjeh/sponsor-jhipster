package com.mitrol.sponsor.service;

import com.mitrol.sponsor.service.dto.BusinessContactProfileDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing BusinessContactProfile.
 */
public interface BusinessContactProfileService {

    /**
     * Save a businessContactProfile.
     *
     * @param businessContactProfileDTO the entity to save
     * @return the persisted entity
     */
    BusinessContactProfileDTO save(BusinessContactProfileDTO businessContactProfileDTO);

    /**
     * Get all the businessContactProfiles.
     *
     * @return the list of entities
     */
    List<BusinessContactProfileDTO> findAll();


    /**
     * Get the "id" businessContactProfile.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BusinessContactProfileDTO> findOne(Long id);

    /**
     * Delete the "id" businessContactProfile.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the businessContactProfile corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<BusinessContactProfileDTO> search(String query);
}
