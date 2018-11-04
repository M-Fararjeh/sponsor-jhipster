package com.mitrol.sponsor.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mitrol.sponsor.domain.BusinessActivity;
import com.mitrol.sponsor.service.BusinessActivityService;
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
 * REST controller for managing BusinessActivity.
 */
@RestController
@RequestMapping("/api")
public class BusinessActivityResource {

    private final Logger log = LoggerFactory.getLogger(BusinessActivityResource.class);

    private static final String ENTITY_NAME = "sponsorBusinessActivity";

    private final BusinessActivityService businessActivityService;

    public BusinessActivityResource(BusinessActivityService businessActivityService) {
        this.businessActivityService = businessActivityService;
    }

    /**
     * POST  /business-activities : Create a new businessActivity.
     *
     * @param businessActivity the businessActivity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new businessActivity, or with status 400 (Bad Request) if the businessActivity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/business-activities")
    @Timed
    public ResponseEntity<BusinessActivity> createBusinessActivity(@RequestBody BusinessActivity businessActivity) throws URISyntaxException {
        log.debug("REST request to save BusinessActivity : {}", businessActivity);
        if (businessActivity.getId() != null) {
            throw new BadRequestAlertException("A new businessActivity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BusinessActivity result = businessActivityService.save(businessActivity);
        return ResponseEntity.created(new URI("/api/business-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /business-activities : Updates an existing businessActivity.
     *
     * @param businessActivity the businessActivity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated businessActivity,
     * or with status 400 (Bad Request) if the businessActivity is not valid,
     * or with status 500 (Internal Server Error) if the businessActivity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/business-activities")
    @Timed
    public ResponseEntity<BusinessActivity> updateBusinessActivity(@RequestBody BusinessActivity businessActivity) throws URISyntaxException {
        log.debug("REST request to update BusinessActivity : {}", businessActivity);
        if (businessActivity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BusinessActivity result = businessActivityService.save(businessActivity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, businessActivity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /business-activities : get all the businessActivities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of businessActivities in body
     */
    @GetMapping("/business-activities")
    @Timed
    public ResponseEntity<List<BusinessActivity>> getAllBusinessActivities(Pageable pageable) {
        log.debug("REST request to get a page of BusinessActivities");
        Page<BusinessActivity> page = businessActivityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/business-activities");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /business-activities/:id : get the "id" businessActivity.
     *
     * @param id the id of the businessActivity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the businessActivity, or with status 404 (Not Found)
     */
    @GetMapping("/business-activities/{id}")
    @Timed
    public ResponseEntity<BusinessActivity> getBusinessActivity(@PathVariable Long id) {
        log.debug("REST request to get BusinessActivity : {}", id);
        Optional<BusinessActivity> businessActivity = businessActivityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(businessActivity);
    }

    /**
     * DELETE  /business-activities/:id : delete the "id" businessActivity.
     *
     * @param id the id of the businessActivity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/business-activities/{id}")
    @Timed
    public ResponseEntity<Void> deleteBusinessActivity(@PathVariable Long id) {
        log.debug("REST request to delete BusinessActivity : {}", id);
        businessActivityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
