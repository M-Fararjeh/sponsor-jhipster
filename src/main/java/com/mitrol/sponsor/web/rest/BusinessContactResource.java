package com.mitrol.sponsor.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mitrol.sponsor.domain.BusinessContact;
import com.mitrol.sponsor.service.BusinessContactService;
import com.mitrol.sponsor.web.rest.errors.BadRequestAlertException;
import com.mitrol.sponsor.web.rest.util.HeaderUtil;
import com.mitrol.sponsor.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing BusinessContact.
 */
@RestController
@RequestMapping("/api")
public class BusinessContactResource {

    private final Logger log = LoggerFactory.getLogger(BusinessContactResource.class);

    private static final String ENTITY_NAME = "sponsorBusinessContact";

    private final BusinessContactService businessContactService;

    public BusinessContactResource(BusinessContactService businessContactService) {
        this.businessContactService = businessContactService;
    }

    /**
     * POST  /business-contacts : Create a new businessContact.
     *
     * @param businessContact the businessContact to create
     * @return the ResponseEntity with status 201 (Created) and with body the new businessContact, or with status 400 (Bad Request) if the businessContact has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/business-contacts")
    @Timed
    public ResponseEntity<BusinessContact> createBusinessContact(@RequestBody BusinessContact businessContact) throws URISyntaxException {
        log.debug("REST request to save BusinessContact : {}", businessContact);
        if (businessContact.getId() != null) {
            throw new BadRequestAlertException("A new businessContact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BusinessContact result = businessContactService.save(businessContact);
        return ResponseEntity.created(new URI("/api/business-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /business-contacts : Updates an existing businessContact.
     *
     * @param businessContact the businessContact to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated businessContact,
     * or with status 400 (Bad Request) if the businessContact is not valid,
     * or with status 500 (Internal Server Error) if the businessContact couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/business-contacts")
    @Timed
    public ResponseEntity<BusinessContact> updateBusinessContact(@RequestBody BusinessContact businessContact) throws URISyntaxException {
        log.debug("REST request to update BusinessContact : {}", businessContact);
        if (businessContact.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BusinessContact result = businessContactService.save(businessContact);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, businessContact.getId().toString()))
            .body(result);
    }

    /**
     * GET  /business-contacts : get all the businessContacts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of businessContacts in body
     */
    @GetMapping("/business-contacts")
    @Timed
    public ResponseEntity<List<BusinessContact>> getAllBusinessContacts(Pageable pageable) {
        log.debug("REST request to get a page of BusinessContacts");
        Page<BusinessContact> page = businessContactService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/business-contacts");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /business-contacts/:id : get the "id" businessContact.
     *
     * @param id the id of the businessContact to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the businessContact, or with status 404 (Not Found)
     */
    @GetMapping("/business-contacts/{id}")
    @Timed
    public ResponseEntity<BusinessContact> getBusinessContact(@PathVariable Long id) {
        log.debug("REST request to get BusinessContact : {}", id);
        Optional<BusinessContact> businessContact = businessContactService.findOne(id);
        return ResponseUtil.wrapOrNotFound(businessContact);
    }

    /**
     * DELETE  /business-contacts/:id : delete the "id" businessContact.
     *
     * @param id the id of the businessContact to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/business-contacts/{id}")
    @Timed
    public ResponseEntity<Void> deleteBusinessContact(@PathVariable Long id) {
        log.debug("REST request to delete BusinessContact : {}", id);
        businessContactService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
