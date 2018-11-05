package com.mitrol.sponsor.service.impl;

import com.mitrol.sponsor.service.SponsorService;
import com.mitrol.sponsor.domain.Sponsor;
import com.mitrol.sponsor.repository.SponsorRepository;
import com.mitrol.sponsor.repository.search.SponsorSearchRepository;
import com.mitrol.sponsor.service.dto.SponsorDTO;
import com.mitrol.sponsor.service.mapper.SponsorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Sponsor.
 */
@Service
@Transactional
public class SponsorServiceImpl implements SponsorService {

    private final Logger log = LoggerFactory.getLogger(SponsorServiceImpl.class);

    private final SponsorRepository sponsorRepository;

    private final SponsorMapper sponsorMapper;

    private final SponsorSearchRepository sponsorSearchRepository;

    public SponsorServiceImpl(SponsorRepository sponsorRepository, SponsorMapper sponsorMapper, SponsorSearchRepository sponsorSearchRepository) {
        this.sponsorRepository = sponsorRepository;
        this.sponsorMapper = sponsorMapper;
        this.sponsorSearchRepository = sponsorSearchRepository;
    }

    /**
     * Save a sponsor.
     *
     * @param sponsorDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SponsorDTO save(SponsorDTO sponsorDTO) {
        log.debug("Request to save Sponsor : {}", sponsorDTO);

        Sponsor sponsor = sponsorMapper.toEntity(sponsorDTO);
        sponsor = sponsorRepository.save(sponsor);
        SponsorDTO result = sponsorMapper.toDto(sponsor);
        sponsorSearchRepository.save(sponsor);
        return result;
    }

    /**
     * Get all the sponsors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SponsorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sponsors");
        return sponsorRepository.findAll(pageable)
            .map(sponsorMapper::toDto);
    }

    /**
     * Get all the Sponsor with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<SponsorDTO> findAllWithEagerRelationships(Pageable pageable) {
        return sponsorRepository.findAllWithEagerRelationships(pageable).map(sponsorMapper::toDto);
    }
    

    /**
     * Get one sponsor by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SponsorDTO> findOne(Long id) {
        log.debug("Request to get Sponsor : {}", id);
        return sponsorRepository.findOneWithEagerRelationships(id)
            .map(sponsorMapper::toDto);
    }

    /**
     * Delete the sponsor by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Sponsor : {}", id);
        sponsorRepository.deleteById(id);
        sponsorSearchRepository.deleteById(id);
    }

    /**
     * Search for the sponsor corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SponsorDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Sponsors for query {}", query);
        return sponsorSearchRepository.search(queryStringQuery(query), pageable)
            .map(sponsorMapper::toDto);
    }
}
