package com.skillsdoor.skillverify.web.rest;

import com.skillsdoor.skillverify.repository.CandidateAnswerRepository;
import com.skillsdoor.skillverify.service.CandidateAnswerService;
import com.skillsdoor.skillverify.service.dto.CandidateAnswerDTO;
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
 * REST controller for managing {@link com.skillsdoor.skillverify.domain.CandidateAnswer}.
 */
@RestController
@RequestMapping("/api/candidate-answers")
public class CandidateAnswerResource {

    private static final Logger LOG = LoggerFactory.getLogger(CandidateAnswerResource.class);

    private static final String ENTITY_NAME = "candidateAnswer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CandidateAnswerService candidateAnswerService;

    private final CandidateAnswerRepository candidateAnswerRepository;

    public CandidateAnswerResource(CandidateAnswerService candidateAnswerService, CandidateAnswerRepository candidateAnswerRepository) {
        this.candidateAnswerService = candidateAnswerService;
        this.candidateAnswerRepository = candidateAnswerRepository;
    }

    /**
     * {@code POST  /candidate-answers} : Create a new candidateAnswer.
     *
     * @param candidateAnswerDTO the candidateAnswerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new candidateAnswerDTO, or with status {@code 400 (Bad Request)} if the candidateAnswer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CandidateAnswerDTO> createCandidateAnswer(@Valid @RequestBody CandidateAnswerDTO candidateAnswerDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save CandidateAnswer : {}", candidateAnswerDTO);
        if (candidateAnswerDTO.getId() != null) {
            throw new BadRequestAlertException("A new candidateAnswer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        candidateAnswerDTO = candidateAnswerService.save(candidateAnswerDTO);
        return ResponseEntity.created(new URI("/api/candidate-answers/" + candidateAnswerDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, candidateAnswerDTO.getId().toString()))
            .body(candidateAnswerDTO);
    }

    /**
     * {@code PUT  /candidate-answers/:id} : Updates an existing candidateAnswer.
     *
     * @param id the id of the candidateAnswerDTO to save.
     * @param candidateAnswerDTO the candidateAnswerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated candidateAnswerDTO,
     * or with status {@code 400 (Bad Request)} if the candidateAnswerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the candidateAnswerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CandidateAnswerDTO> updateCandidateAnswer(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CandidateAnswerDTO candidateAnswerDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update CandidateAnswer : {}, {}", id, candidateAnswerDTO);
        if (candidateAnswerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, candidateAnswerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!candidateAnswerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        candidateAnswerDTO = candidateAnswerService.update(candidateAnswerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, candidateAnswerDTO.getId().toString()))
            .body(candidateAnswerDTO);
    }

    /**
     * {@code PATCH  /candidate-answers/:id} : Partial updates given fields of an existing candidateAnswer, field will ignore if it is null
     *
     * @param id the id of the candidateAnswerDTO to save.
     * @param candidateAnswerDTO the candidateAnswerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated candidateAnswerDTO,
     * or with status {@code 400 (Bad Request)} if the candidateAnswerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the candidateAnswerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the candidateAnswerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CandidateAnswerDTO> partialUpdateCandidateAnswer(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CandidateAnswerDTO candidateAnswerDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update CandidateAnswer partially : {}, {}", id, candidateAnswerDTO);
        if (candidateAnswerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, candidateAnswerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!candidateAnswerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CandidateAnswerDTO> result = candidateAnswerService.partialUpdate(candidateAnswerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, candidateAnswerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /candidate-answers} : get all the candidateAnswers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of candidateAnswers in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CandidateAnswerDTO>> getAllCandidateAnswers(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of CandidateAnswers");
        Page<CandidateAnswerDTO> page = candidateAnswerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /candidate-answers/:id} : get the "id" candidateAnswer.
     *
     * @param id the id of the candidateAnswerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the candidateAnswerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CandidateAnswerDTO> getCandidateAnswer(@PathVariable("id") Long id) {
        LOG.debug("REST request to get CandidateAnswer : {}", id);
        Optional<CandidateAnswerDTO> candidateAnswerDTO = candidateAnswerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(candidateAnswerDTO);
    }

    /**
     * {@code DELETE  /candidate-answers/:id} : delete the "id" candidateAnswer.
     *
     * @param id the id of the candidateAnswerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidateAnswer(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete CandidateAnswer : {}", id);
        candidateAnswerService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
