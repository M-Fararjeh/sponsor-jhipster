package com.mitrol.sponsor.service.impl;

import com.mitrol.sponsor.service.BusinessActivityService;
import com.mitrol.sponsor.domain.BusinessActivity;
import com.mitrol.sponsor.repository.BusinessActivityRepository;
import com.mitrol.sponsor.repository.search.BusinessActivitySearchRepository;
import com.mitrol.sponsor.service.dto.BusinessActivityDTO;
import com.mitrol.sponsor.service.mapper.BusinessActivityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing BusinessActivity.
 */
@Service
@Transactional
public class BusinessActivityServiceImpl implements BusinessActivityService {

    private final Logger log = LoggerFactory.getLogger(BusinessActivityServiceImpl.class);

    private final BusinessActivityRepository businessActivityRepository;

    private final BusinessActivityMapper businessActivityMapper;

    private final BusinessActivitySearchRepository businessActivitySearchRepository;

    public BusinessActivityServiceImpl(BusinessActivityRepository businessActivityRepository, BusinessActivityMapper businessActivityMapper, BusinessActivitySearchRepository businessActivitySearchRepository) {
        this.businessActivityRepository = businessActivityRepository;
        this.businessActivityMapper = businessActivityMapper;
        this.businessActivitySearchRepository = businessActivitySearchRepository;
    }

    /**
     * Save a businessActivity.
     *
     * @param businessActivityDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BusinessActivityDTO save(BusinessActivityDTO businessActivityDTO) {
        log.debug("Request to save BusinessActivity : {}", businessActivityDTO);

        BusinessActivity businessActivity = businessActivityMapper.toEntity(businessActivityDTO);
        businessActivity = businessActivityRepository.save(businessActivity);
        BusinessActivityDTO result = businessActivityMapper.toDto(businessActivity);
        businessActivitySearchRepository.save(businessActivity);
        return result;
    }

    /**
     * Get all the businessActivities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BusinessActivityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BusinessActivities");
        return businessActivityRepository.findAll(pageable)
            .map(businessActivityMapper::toDto);
    }


    /**
     * Get one businessActivity by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BusinessActivityDTO> findOne(Long id) {
        log.debug("Request to get BusinessActivity : {}", id);
        return businessActivityRepository.findById(id)
            .map(businessActivityMapper::toDto);
    }

    /**
     * Delete the businessActivity by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BusinessActivity : {}", id);
        businessActivityRepository.deleteById(id);
        businessActivitySearchRepository.deleteById(id);
    }

    /**
     * Search for the businessActivity corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BusinessActivityDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of BusinessActivities for query {}", query);
        return businessActivitySearchRepository.search(queryStringQuery(query), pageable)
            .map(businessActivityMapper::toDto);
    }
}
