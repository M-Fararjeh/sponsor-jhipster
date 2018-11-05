package com.mitrol.sponsor.service;

import com.mitrol.sponsor.service.dto.SponsorDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Sponsor.
 */
public interface SponsorService {

    /**
     * Save a sponsor.
     *
     * @param sponsorDTO the entity to save
     * @return the persisted entity
     */
    SponsorDTO save(SponsorDTO sponsorDTO);

    /**
     * Get all the sponsors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SponsorDTO> findAll(Pageable pageable);

    /**
     * Get all the Sponsor with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<SponsorDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" sponsor.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SponsorDTO> findOne(Long id);

    /**
     * Delete the "id" sponsor.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the sponsor corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SponsorDTO> search(String query, Pageable pageable);
}
