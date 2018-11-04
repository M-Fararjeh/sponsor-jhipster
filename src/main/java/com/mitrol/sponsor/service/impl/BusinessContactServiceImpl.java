package com.mitrol.sponsor.service.impl;

import com.mitrol.sponsor.service.BusinessContactService;
import com.mitrol.sponsor.domain.BusinessContact;
import com.mitrol.sponsor.repository.BusinessContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing BusinessContact.
 */
@Service
@Transactional
public class BusinessContactServiceImpl implements BusinessContactService {

    private final Logger log = LoggerFactory.getLogger(BusinessContactServiceImpl.class);

    private final BusinessContactRepository businessContactRepository;

    public BusinessContactServiceImpl(BusinessContactRepository businessContactRepository) {
        this.businessContactRepository = businessContactRepository;
    }

    /**
     * Save a businessContact.
     *
     * @param businessContact the entity to save
     * @return the persisted entity
     */
    @Override
    public BusinessContact save(BusinessContact businessContact) {
        log.debug("Request to save BusinessContact : {}", businessContact);
        return businessContactRepository.save(businessContact);
    }

    /**
     * Get all the businessContacts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BusinessContact> findAll(Pageable pageable) {
        log.debug("Request to get all BusinessContacts");
        return businessContactRepository.findAll(pageable);
    }


    /**
     * Get one businessContact by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BusinessContact> findOne(Long id) {
        log.debug("Request to get BusinessContact : {}", id);
        return businessContactRepository.findById(id);
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
    }
}
