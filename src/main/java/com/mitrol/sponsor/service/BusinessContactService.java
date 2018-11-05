package com.mitrol.sponsor.service;

import com.mitrol.sponsor.service.dto.BusinessContactDTO;

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
     * @param businessContactDTO the entity to save
     * @return the persisted entity
     */
    BusinessContactDTO save(BusinessContactDTO businessContactDTO);

    /**
     * Get all the businessContacts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BusinessContactDTO> findAll(Pageable pageable);


    /**
     * Get the "id" businessContact.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BusinessContactDTO> findOne(Long id);

    /**
     * Delete the "id" businessContact.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the businessContact corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BusinessContactDTO> search(String query, Pageable pageable);
}
