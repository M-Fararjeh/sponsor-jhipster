package com.mitrol.sponsor.service.impl;

import com.mitrol.sponsor.service.BusinessContactProfileService;
import com.mitrol.sponsor.domain.BusinessContactProfile;
import com.mitrol.sponsor.repository.BusinessContactProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing BusinessContactProfile.
 */
@Service
@Transactional
public class BusinessContactProfileServiceImpl implements BusinessContactProfileService {

    private final Logger log = LoggerFactory.getLogger(BusinessContactProfileServiceImpl.class);

    private final BusinessContactProfileRepository businessContactProfileRepository;

    public BusinessContactProfileServiceImpl(BusinessContactProfileRepository businessContactProfileRepository) {
        this.businessContactProfileRepository = businessContactProfileRepository;
    }

    /**
     * Save a businessContactProfile.
     *
     * @param businessContactProfile the entity to save
     * @return the persisted entity
     */
    @Override
    public BusinessContactProfile save(BusinessContactProfile businessContactProfile) {
        log.debug("Request to save BusinessContactProfile : {}", businessContactProfile);
        return businessContactProfileRepository.save(businessContactProfile);
    }

    /**
     * Get all the businessContactProfiles.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BusinessContactProfile> findAll() {
        log.debug("Request to get all BusinessContactProfiles");
        return businessContactProfileRepository.findAll();
    }


    /**
     * Get one businessContactProfile by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BusinessContactProfile> findOne(Long id) {
        log.debug("Request to get BusinessContactProfile : {}", id);
        return businessContactProfileRepository.findById(id);
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
    }
}
