package com.mitrol.sponsor.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mitrol.sponsor.domain.BusinessContactProfile;
import com.mitrol.sponsor.service.BusinessContactProfileService;
import com.mitrol.sponsor.web.rest.errors.BadRequestAlertException;
import com.mitrol.sponsor.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing BusinessContactProfile.
 */
@RestController
@RequestMapping("/api")
public class BusinessContactProfileResource {

    private final Logger log = LoggerFactory.getLogger(BusinessContactProfileResource.class);

    private static final String ENTITY_NAME = "sponsorBusinessContactProfile";

    private final BusinessContactProfileService businessContactProfileService;

    public BusinessContactProfileResource(BusinessContactProfileService businessContactProfileService) {
        this.businessContactProfileService = businessContactProfileService;
    }

    /**
     * POST  /business-contact-profiles : Create a new businessContactProfile.
     *
     * @param businessContactProfile the businessContactProfile to create
     * @return the ResponseEntity with status 201 (Created) and with body the new businessContactProfile, or with status 400 (Bad Request) if the businessContactProfile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/business-contact-profiles")
    @Timed
    public ResponseEntity<BusinessContactProfile> createBusinessContactProfile(@RequestBody BusinessContactProfile businessContactProfile) throws URISyntaxException {
        log.debug("REST request to save BusinessContactProfile : {}", businessContactProfile);
        if (businessContactProfile.getId() != null) {
            throw new BadRequestAlertException("A new businessContactProfile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BusinessContactProfile result = businessContactProfileService.save(businessContactProfile);
        return ResponseEntity.created(new URI("/api/business-contact-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /business-contact-profiles : Updates an existing businessContactProfile.
     *
     * @param businessContactProfile the businessContactProfile to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated businessContactProfile,
     * or with status 400 (Bad Request) if the businessContactProfile is not valid,
     * or with status 500 (Internal Server Error) if the businessContactProfile couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/business-contact-profiles")
    @Timed
    public ResponseEntity<BusinessContactProfile> updateBusinessContactProfile(@RequestBody BusinessContactProfile businessContactProfile) throws URISyntaxException {
        log.debug("REST request to update BusinessContactProfile : {}", businessContactProfile);
        if (businessContactProfile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BusinessContactProfile result = businessContactProfileService.save(businessContactProfile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, businessContactProfile.getId().toString()))
            .body(result);
    }

    /**
     * GET  /business-contact-profiles : get all the businessContactProfiles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of businessContactProfiles in body
     */
    @GetMapping("/business-contact-profiles")
    @Timed
    public List<BusinessContactProfile> getAllBusinessContactProfiles() {
        log.debug("REST request to get all BusinessContactProfiles");
        return businessContactProfileService.findAll();
    }

    /**
     * GET  /business-contact-profiles/:id : get the "id" businessContactProfile.
     *
     * @param id the id of the businessContactProfile to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the businessContactProfile, or with status 404 (Not Found)
     */
    @GetMapping("/business-contact-profiles/{id}")
    @Timed
    public ResponseEntity<BusinessContactProfile> getBusinessContactProfile(@PathVariable Long id) {
        log.debug("REST request to get BusinessContactProfile : {}", id);
        Optional<BusinessContactProfile> businessContactProfile = businessContactProfileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(businessContactProfile);
    }

    /**
     * DELETE  /business-contact-profiles/:id : delete the "id" businessContactProfile.
     *
     * @param id the id of the businessContactProfile to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/business-contact-profiles/{id}")
    @Timed
    public ResponseEntity<Void> deleteBusinessContactProfile(@PathVariable Long id) {
        log.debug("REST request to delete BusinessContactProfile : {}", id);
        businessContactProfileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
