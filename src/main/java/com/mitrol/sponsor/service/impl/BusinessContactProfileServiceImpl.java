package com.mitrol.sponsor.service.impl;

import com.mitrol.sponsor.service.BusinessContactProfileService;
import com.mitrol.sponsor.domain.BusinessContactProfile;
import com.mitrol.sponsor.repository.BusinessContactProfileRepository;
import com.mitrol.sponsor.repository.search.BusinessContactProfileSearchRepository;
import com.mitrol.sponsor.service.dto.BusinessContactProfileDTO;
import com.mitrol.sponsor.service.mapper.BusinessContactProfileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing BusinessContactProfile.
 */
@Service
@Transactional
public class BusinessContactProfileServiceImpl implements BusinessContactProfileService {

    private final Logger log = LoggerFactory.getLogger(BusinessContactProfileServiceImpl.class);

    private final BusinessContactProfileRepository businessContactProfileRepository;

    private final BusinessContactProfileMapper businessContactProfileMapper;

    private final BusinessContactProfileSearchRepository businessContactProfileSearchRepository;

    public BusinessContactProfileServiceImpl(BusinessContactProfileRepository businessContactProfileRepository, BusinessContactProfileMapper businessContactProfileMapper, BusinessContactProfileSearchRepository businessContactProfileSearchRepository) {
        this.businessContactProfileRepository = businessContactProfileRepository;
        this.businessContactProfileMapper = businessContactProfileMapper;
        this.businessContactProfileSearchRepository = businessContactProfileSearchRepository;
    }

    /**
     * Save a businessContactProfile.
     *
     * @param businessContactProfileDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BusinessContactProfileDTO save(BusinessContactProfileDTO businessContactProfileDTO) {
        log.debug("Request to save BusinessContactProfile : {}", businessContactProfileDTO);

        BusinessContactProfile businessContactProfile = businessContactProfileMapper.toEntity(businessContactProfileDTO);
        businessContactProfile = businessContactProfileRepository.save(businessContactProfile);
        BusinessContactProfileDTO result = businessContactProfileMapper.toDto(businessContactProfile);
        businessContactProfileSearchRepository.save(businessContactProfile);
        return result;
    }

    /**
     * Get all the businessContactProfiles.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BusinessContactProfileDTO> findAll() {
        log.debug("Request to get all BusinessContactProfiles");
        return businessContactProfileRepository.findAll().stream()
            .map(businessContactProfileMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one businessContactProfile by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BusinessContactProfileDTO> findOne(Long id) {
        log.debug("Request to get BusinessContactProfile : {}", id);
        return businessContactProfileRepository.findById(id)
            .map(businessContactProfileMapper::toDto);
    }

    /**
     * Delete the businessContactProfile by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BusinessContactProfile : {}", id);
        businessContactProfileRepository.deleteById(id);
        businessContactProfileSearchRepository.deleteById(id);
    }

    /**
     * Search for the businessContactProfile corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BusinessContactProfileDTO> search(String query) {
        log.debug("Request to search BusinessContactProfiles for query {}", query);
        return StreamSupport
            .stream(businessContactProfileSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(businessContactProfileMapper::toDto)
            .collect(Collectors.toList());
    }
}
