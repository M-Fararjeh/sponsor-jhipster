package com.mitrol.sponsor.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mitrol.sponsor.service.BusinessContactProfileService;
import com.mitrol.sponsor.web.rest.errors.BadRequestAlertException;
import com.mitrol.sponsor.web.rest.util.HeaderUtil;
import com.mitrol.sponsor.service.dto.BusinessContactProfileDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

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
     * @param businessContactProfileDTO the businessContactProfileDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new businessContactProfileDTO, or with status 400 (Bad Request) if the businessContactProfile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/business-contact-profiles")
    @Timed
    public ResponseEntity<BusinessContactProfileDTO> createBusinessContactProfile(@RequestBody BusinessContactProfileDTO businessContactProfileDTO) throws URISyntaxException {
        log.debug("REST request to save BusinessContactProfile : {}", businessContactProfileDTO);
        if (businessContactProfileDTO.getId() != null) {
            throw new BadRequestAlertException("A new businessContactProfile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BusinessContactProfileDTO result = businessContactProfileService.save(businessContactProfileDTO);
        return ResponseEntity.created(new URI("/api/business-contact-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /business-contact-profiles : Updates an existing businessContactProfile.
     *
     * @param businessContactProfileDTO the businessContactProfileDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated businessContactProfileDTO,
     * or with status 400 (Bad Request) if the businessContactProfileDTO is not valid,
     * or with status 500 (Internal Server Error) if the businessContactProfileDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/business-contact-profiles")
    @Timed
    public ResponseEntity<BusinessContactProfileDTO> updateBusinessContactProfile(@RequestBody BusinessContactProfileDTO businessContactProfileDTO) throws URISyntaxException {
        log.debug("REST request to update BusinessContactProfile : {}", businessContactProfileDTO);
        if (businessContactProfileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BusinessContactProfileDTO result = businessContactProfileService.save(businessContactProfileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, businessContactProfileDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /business-contact-profiles : get all the businessContactProfiles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of businessContactProfiles in body
     */
    @GetMapping("/business-contact-profiles")
    @Timed
    public List<BusinessContactProfileDTO> getAllBusinessContactProfiles() {
        log.debug("REST request to get all BusinessContactProfiles");
        return businessContactProfileService.findAll();
    }

    /**
     * GET  /business-contact-profiles/:id : get the "id" businessContactProfile.
     *
     * @param id the id of the businessContactProfileDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the businessContactProfileDTO, or with status 404 (Not Found)
     */
    @GetMapping("/business-contact-profiles/{id}")
    @Timed
    public ResponseEntity<BusinessContactProfileDTO> getBusinessContactProfile(@PathVariable Long id) {
        log.debug("REST request to get BusinessContactProfile : {}", id);
        Optional<BusinessContactProfileDTO> businessContactProfileDTO = businessContactProfileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(businessContactProfileDTO);
    }

    /**
     * DELETE  /business-contact-profiles/:id : delete the "id" businessContactProfile.
     *
     * @param id the id of the businessContactProfileDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/business-contact-profiles/{id}")
    @Timed
    public ResponseEntity<Void> deleteBusinessContactProfile(@PathVariable Long id) {
        log.debug("REST request to delete BusinessContactProfile : {}", id);
        businessContactProfileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/business-contact-profiles?query=:query : search for the businessContactProfile corresponding
     * to the query.
     *
     * @param query the query of the businessContactProfile search
     * @return the result of the search
     */
    @GetMapping("/_search/business-contact-profiles")
    @Timed
    public List<BusinessContactProfileDTO> searchBusinessContactProfiles(@RequestParam String query) {
        log.debug("REST request to search BusinessContactProfiles for query {}", query);
        return businessContactProfileService.search(query);
    }

}
