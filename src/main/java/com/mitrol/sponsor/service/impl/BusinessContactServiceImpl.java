package com.mitrol.sponsor.service.impl;

import com.mitrol.sponsor.service.BusinessContactService;
import com.mitrol.sponsor.domain.BusinessContact;
import com.mitrol.sponsor.repository.BusinessContactRepository;
import com.mitrol.sponsor.repository.search.BusinessContactSearchRepository;
import com.mitrol.sponsor.service.dto.BusinessContactDTO;
import com.mitrol.sponsor.service.mapper.BusinessContactMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing BusinessContact.
 */
@Service
@Transactional
public class BusinessContactServiceImpl implements BusinessContactService {

    private final Logger log = LoggerFactory.getLogger(BusinessContactServiceImpl.class);

    private final BusinessContactRepository businessContactRepository;

    private final BusinessContactMapper businessContactMapper;

    private final BusinessContactSearchRepository businessContactSearchRepository;

    public BusinessContactServiceImpl(BusinessContactRepository businessContactRepository, BusinessContactMapper businessContactMapper, BusinessContactSearchRepository businessContactSearchRepository) {
        this.businessContactRepository = businessContactRepository;
        this.businessContactMapper = businessContactMapper;
        this.businessContactSearchRepository = businessContactSearchRepository;
    }

    /**
     * Save a businessContact.
     *
     * @param businessContactDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BusinessContactDTO save(BusinessContactDTO businessContactDTO) {
        log.debug("Request to save BusinessContact : {}", businessContactDTO);

        BusinessContact businessContact = businessContactMapper.toEntity(businessContactDTO);
        businessContact = businessContactRepository.save(businessContact);
        BusinessContactDTO result = businessContactMapper.toDto(businessContact);
        businessContactSearchRepository.save(businessContact);
        return result;
    }

    /**
     * Get all the businessContacts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BusinessContactDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BusinessContacts");
        return businessContactRepository.findAll(pageable)
            .map(businessContactMapper::toDto);
    }


    /**
     * Get one businessContact by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BusinessContactDTO> findOne(Long id) {
        log.debug("Request to get BusinessContact : {}", id);
        return businessContactRepository.findById(id)
            .map(businessContactMapper::toDto);
    }

    /**
     * Delete the businessContact by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BusinessContact : {}", id);
        businessContactRepository.deleteById(id);
        businessContactSearchRepository.deleteById(id);
    }

    /**
     * Search for the businessContact corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BusinessContactDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of BusinessContacts for query {}", query);
        return businessContactSearchRepository.search(queryStringQuery(query), pageable)
            .map(businessContactMapper::toDto);
    }
}
