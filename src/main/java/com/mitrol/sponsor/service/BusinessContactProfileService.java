package com.mitrol.sponsor.service;

import com.mitrol.sponsor.domain.BusinessContactProfile;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing BusinessContactProfile.
 */
public interface BusinessContactProfileService {

    /**
     * Save a businessContactProfile.
     *
     * @param businessContactProfile the entity to save
     * @return the persisted entity
     */
    BusinessContactProfile save(BusinessContactProfile businessContactProfile);

    /**
     * Get all the businessContactProfiles.
     *
     * @return the list of entities
     */
    List<BusinessContactProfile> findAll();


    /**
     * Get the "id" businessContactProfile.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BusinessContactProfile> findOne(Long id);

    /**
     * Delete the "id" businessContactProfile.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
