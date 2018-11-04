package com.mitrol.sponsor.service;

import com.mitrol.sponsor.domain.BusinessContact;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing BusinessContact.
 */
public interface BusinessContactService {

    /**
     * Save a businessContact.
     *
     * @param businessContact the entity to save
     * @return the persisted entity
     */
    BusinessContact save(BusinessContact businessContact);

    /**
     * Get all the businessContacts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BusinessContact> findAll(Pageable pageable);


    /**
     * Get the "id" businessContact.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BusinessContact> findOne(Long id);

    /**
     * Delete the "id" businessContact.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
