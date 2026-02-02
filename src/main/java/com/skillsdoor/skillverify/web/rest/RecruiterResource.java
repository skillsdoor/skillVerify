package com.skillsdoor.skillverify.web.rest;

import com.skillsdoor.skillverify.repository.RecruiterRepository;
import com.skillsdoor.skillverify.service.RecruiterService;
import com.skillsdoor.skillverify.service.dto.RecruiterDTO;
import com.skillsdoor.skillverify.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.skillsdoor.skillverify.domain.Recruiter}.
 */
@RestController
@RequestMapping("/api/recruiters")
public class RecruiterResource {

    private static final Logger LOG = LoggerFactory.getLogger(RecruiterResource.class);

    private static final String ENTITY_NAME = "recruiter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecruiterService recruiterService;

    private final RecruiterRepository recruiterRepository;

    public RecruiterResource(RecruiterService recruiterService, RecruiterRepository recruiterRepository) {
        this.recruiterService = recruiterService;
        this.recruiterRepository = recruiterRepository;
    }

    /**
     * {@code POST  /recruiters} : Create a new recruiter.
     *
     * @param recruiterDTO the recruiterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recruiterDTO, or with status {@code 400 (Bad Request)} if the recruiter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RecruiterDTO> createRecruiter(@Valid @RequestBody RecruiterDTO recruiterDTO) throws URISyntaxException {
        LOG.debug("REST request to save Recruiter : {}", recruiterDTO);
        if (recruiterDTO.getId() != null) {
            throw new BadRequestAlertException("A new recruiter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        recruiterDTO = recruiterService.save(recruiterDTO);
        return ResponseEntity.created(new URI("/api/recruiters/" + recruiterDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, recruiterDTO.getId().toString()))
            .body(recruiterDTO);
    }

    /**
     * {@code PUT  /recruiters/:id} : Updates an existing recruiter.
     *
     * @param id the id of the recruiterDTO to save.
     * @param recruiterDTO the recruiterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recruiterDTO,
     * or with status {@code 400 (Bad Request)} if the recruiterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recruiterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RecruiterDTO> updateRecruiter(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RecruiterDTO recruiterDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Recruiter : {}, {}", id, recruiterDTO);
        if (recruiterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recruiterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recruiterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        recruiterDTO = recruiterService.update(recruiterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recruiterDTO.getId().toString()))
            .body(recruiterDTO);
    }

    /**
     * {@code PATCH  /recruiters/:id} : Partial updates given fields of an existing recruiter, field will ignore if it is null
     *
     * @param id the id of the recruiterDTO to save.
     * @param recruiterDTO the recruiterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recruiterDTO,
     * or with status {@code 400 (Bad Request)} if the recruiterDTO is not valid,
     * or with status {@code 404 (Not Found)} if the recruiterDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the recruiterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RecruiterDTO> partialUpdateRecruiter(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RecruiterDTO recruiterDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Recruiter partially : {}, {}", id, recruiterDTO);
        if (recruiterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recruiterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recruiterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RecruiterDTO> result = recruiterService.partialUpdate(recruiterDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recruiterDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /recruiters} : get all the recruiters.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recruiters in body.
     */
    @GetMapping("")
    public ResponseEntity<List<RecruiterDTO>> getAllRecruiters(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of Recruiters");
        Page<RecruiterDTO> page;
        if (eagerload) {
            page = recruiterService.findAllWithEagerRelationships(pageable);
        } else {
            page = recruiterService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /recruiters/:id} : get the "id" recruiter.
     *
     * @param id the id of the recruiterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recruiterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RecruiterDTO> getRecruiter(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Recruiter : {}", id);
        Optional<RecruiterDTO> recruiterDTO = recruiterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recruiterDTO);
    }

    /**
     * {@code DELETE  /recruiters/:id} : delete the "id" recruiter.
     *
     * @param id the id of the recruiterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecruiter(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Recruiter : {}", id);
        recruiterService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
