package com.mitrol.sponsor.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mitrol.sponsor.service.BusinessContactService;
import com.mitrol.sponsor.web.rest.errors.BadRequestAlertException;
import com.mitrol.sponsor.web.rest.util.HeaderUtil;
import com.mitrol.sponsor.web.rest.util.PaginationUtil;
import com.mitrol.sponsor.service.dto.BusinessContactDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

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
     * @param businessContactDTO the businessContactDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new businessContactDTO, or with status 400 (Bad Request) if the businessContact has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/business-contacts")
    @Timed
    public ResponseEntity<BusinessContactDTO> createBusinessContact(@Valid @RequestBody BusinessContactDTO businessContactDTO) throws URISyntaxException {
        log.debug("REST request to save BusinessContact : {}", businessContactDTO);
        if (businessContactDTO.getId() != null) {
            throw new BadRequestAlertException("A new businessContact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BusinessContactDTO result = businessContactService.save(businessContactDTO);
        return ResponseEntity.created(new URI("/api/business-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /business-contacts : Updates an existing businessContact.
     *
     * @param businessContactDTO the businessContactDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated businessContactDTO,
     * or with status 400 (Bad Request) if the businessContactDTO is not valid,
     * or with status 500 (Internal Server Error) if the businessContactDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/business-contacts")
    @Timed
    public ResponseEntity<BusinessContactDTO> updateBusinessContact(@Valid @RequestBody BusinessContactDTO businessContactDTO) throws URISyntaxException {
        log.debug("REST request to update BusinessContact : {}", businessContactDTO);
        if (businessContactDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BusinessContactDTO result = businessContactService.save(businessContactDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, businessContactDTO.getId().toString()))
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
    public ResponseEntity<List<BusinessContactDTO>> getAllBusinessContacts(Pageable pageable) {
        log.debug("REST request to get a page of BusinessContacts");
        Page<BusinessContactDTO> page = businessContactService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/business-contacts");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /business-contacts/:id : get the "id" businessContact.
     *
     * @param id the id of the businessContactDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the businessContactDTO, or with status 404 (Not Found)
     */
    @GetMapping("/business-contacts/{id}")
    @Timed
    public ResponseEntity<BusinessContactDTO> getBusinessContact(@PathVariable Long id) {
        log.debug("REST request to get BusinessContact : {}", id);
        Optional<BusinessContactDTO> businessContactDTO = businessContactService.findOne(id);
        return ResponseUtil.wrapOrNotFound(businessContactDTO);
    }

    /**
     * DELETE  /business-contacts/:id : delete the "id" businessContact.
     *
     * @param id the id of the businessContactDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/business-contacts/{id}")
    @Timed
    public ResponseEntity<Void> deleteBusinessContact(@PathVariable Long id) {
        log.debug("REST request to delete BusinessContact : {}", id);
        businessContactService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/business-contacts?query=:query : search for the businessContact corresponding
     * to the query.
     *
     * @param query the query of the businessContact search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/business-contacts")
    @Timed
    public ResponseEntity<List<BusinessContactDTO>> searchBusinessContacts(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of BusinessContacts for query {}", query);
        Page<BusinessContactDTO> page = businessContactService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/business-contacts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
