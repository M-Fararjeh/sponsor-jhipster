package com.mitrol.sponsor.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mitrol.sponsor.service.SponsorService;
import com.mitrol.sponsor.web.rest.errors.BadRequestAlertException;
import com.mitrol.sponsor.web.rest.util.HeaderUtil;
import com.mitrol.sponsor.web.rest.util.PaginationUtil;
import com.mitrol.sponsor.service.dto.SponsorDTO;
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
 * REST controller for managing Sponsor.
 */
@RestController
@RequestMapping("/api")
public class SponsorResource {

    private final Logger log = LoggerFactory.getLogger(SponsorResource.class);

    private static final String ENTITY_NAME = "sponsorSponsor";

    private final SponsorService sponsorService;

    public SponsorResource(SponsorService sponsorService) {
        this.sponsorService = sponsorService;
    }

    /**
     * POST  /sponsors : Create a new sponsor.
     *
     * @param sponsorDTO the sponsorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sponsorDTO, or with status 400 (Bad Request) if the sponsor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sponsors")
    @Timed
    public ResponseEntity<SponsorDTO> createSponsor(@Valid @RequestBody SponsorDTO sponsorDTO) throws URISyntaxException {
        log.debug("REST request to save Sponsor : {}", sponsorDTO);
        if (sponsorDTO.getId() != null) {
            throw new BadRequestAlertException("A new sponsor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SponsorDTO result = sponsorService.save(sponsorDTO);
        return ResponseEntity.created(new URI("/api/sponsors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sponsors : Updates an existing sponsor.
     *
     * @param sponsorDTO the sponsorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sponsorDTO,
     * or with status 400 (Bad Request) if the sponsorDTO is not valid,
     * or with status 500 (Internal Server Error) if the sponsorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sponsors")
    @Timed
    public ResponseEntity<SponsorDTO> updateSponsor(@Valid @RequestBody SponsorDTO sponsorDTO) throws URISyntaxException {
        log.debug("REST request to update Sponsor : {}", sponsorDTO);
        if (sponsorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SponsorDTO result = sponsorService.save(sponsorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sponsorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sponsors : get all the sponsors.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of sponsors in body
     */
    @GetMapping("/sponsors")
    @Timed
    public ResponseEntity<List<SponsorDTO>> getAllSponsors(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Sponsors");
        Page<SponsorDTO> page;
        if (eagerload) {
            page = sponsorService.findAllWithEagerRelationships(pageable);
        } else {
            page = sponsorService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/sponsors?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /sponsors/:id : get the "id" sponsor.
     *
     * @param id the id of the sponsorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sponsorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sponsors/{id}")
    @Timed
    public ResponseEntity<SponsorDTO> getSponsor(@PathVariable Long id) {
        log.debug("REST request to get Sponsor : {}", id);
        Optional<SponsorDTO> sponsorDTO = sponsorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sponsorDTO);
    }

    /**
     * DELETE  /sponsors/:id : delete the "id" sponsor.
     *
     * @param id the id of the sponsorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sponsors/{id}")
    @Timed
    public ResponseEntity<Void> deleteSponsor(@PathVariable Long id) {
        log.debug("REST request to delete Sponsor : {}", id);
        sponsorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/sponsors?query=:query : search for the sponsor corresponding
     * to the query.
     *
     * @param query the query of the sponsor search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/sponsors")
    @Timed
    public ResponseEntity<List<SponsorDTO>> searchSponsors(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Sponsors for query {}", query);
        Page<SponsorDTO> page = sponsorService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/sponsors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
