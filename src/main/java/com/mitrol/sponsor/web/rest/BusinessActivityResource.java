package com.mitrol.sponsor.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mitrol.sponsor.service.BusinessActivityService;
import com.mitrol.sponsor.web.rest.errors.BadRequestAlertException;
import com.mitrol.sponsor.web.rest.util.HeaderUtil;
import com.mitrol.sponsor.web.rest.util.PaginationUtil;
import com.mitrol.sponsor.service.dto.BusinessActivityDTO;
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
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

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
     * @param businessActivityDTO the businessActivityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new businessActivityDTO, or with status 400 (Bad Request) if the businessActivity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/business-activities")
    @Timed
    public ResponseEntity<BusinessActivityDTO> createBusinessActivity(@RequestBody BusinessActivityDTO businessActivityDTO) throws URISyntaxException {
        log.debug("REST request to save BusinessActivity : {}", businessActivityDTO);
        if (businessActivityDTO.getId() != null) {
            throw new BadRequestAlertException("A new businessActivity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BusinessActivityDTO result = businessActivityService.save(businessActivityDTO);
        return ResponseEntity.created(new URI("/api/business-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /business-activities : Updates an existing businessActivity.
     *
     * @param businessActivityDTO the businessActivityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated businessActivityDTO,
     * or with status 400 (Bad Request) if the businessActivityDTO is not valid,
     * or with status 500 (Internal Server Error) if the businessActivityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/business-activities")
    @Timed
    public ResponseEntity<BusinessActivityDTO> updateBusinessActivity(@RequestBody BusinessActivityDTO businessActivityDTO) throws URISyntaxException {
        log.debug("REST request to update BusinessActivity : {}", businessActivityDTO);
        if (businessActivityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BusinessActivityDTO result = businessActivityService.save(businessActivityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, businessActivityDTO.getId().toString()))
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
    public ResponseEntity<List<BusinessActivityDTO>> getAllBusinessActivities(Pageable pageable) {
        log.debug("REST request to get a page of BusinessActivities");
        Page<BusinessActivityDTO> page = businessActivityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/business-activities");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /business-activities/:id : get the "id" businessActivity.
     *
     * @param id the id of the businessActivityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the businessActivityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/business-activities/{id}")
    @Timed
    public ResponseEntity<BusinessActivityDTO> getBusinessActivity(@PathVariable Long id) {
        log.debug("REST request to get BusinessActivity : {}", id);
        Optional<BusinessActivityDTO> businessActivityDTO = businessActivityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(businessActivityDTO);
    }

    /**
     * DELETE  /business-activities/:id : delete the "id" businessActivity.
     *
     * @param id the id of the businessActivityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/business-activities/{id}")
    @Timed
    public ResponseEntity<Void> deleteBusinessActivity(@PathVariable Long id) {
        log.debug("REST request to delete BusinessActivity : {}", id);
        businessActivityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/business-activities?query=:query : search for the businessActivity corresponding
     * to the query.
     *
     * @param query the query of the businessActivity search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/business-activities")
    @Timed
    public ResponseEntity<List<BusinessActivityDTO>> searchBusinessActivities(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of BusinessActivities for query {}", query);
        Page<BusinessActivityDTO> page = businessActivityService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/business-activities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
