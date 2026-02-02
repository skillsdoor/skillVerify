package com.skillsdoor.skillverify.web.rest;

import com.skillsdoor.skillverify.repository.BlankAnswerRepository;
import com.skillsdoor.skillverify.service.BlankAnswerService;
import com.skillsdoor.skillverify.service.dto.BlankAnswerDTO;
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
 * REST controller for managing {@link com.skillsdoor.skillverify.domain.BlankAnswer}.
 */
@RestController
@RequestMapping("/api/blank-answers")
public class BlankAnswerResource {

    private static final Logger LOG = LoggerFactory.getLogger(BlankAnswerResource.class);

    private static final String ENTITY_NAME = "blankAnswer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BlankAnswerService blankAnswerService;

    private final BlankAnswerRepository blankAnswerRepository;

    public BlankAnswerResource(BlankAnswerService blankAnswerService, BlankAnswerRepository blankAnswerRepository) {
        this.blankAnswerService = blankAnswerService;
        this.blankAnswerRepository = blankAnswerRepository;
    }

    /**
     * {@code POST  /blank-answers} : Create a new blankAnswer.
     *
     * @param blankAnswerDTO the blankAnswerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new blankAnswerDTO, or with status {@code 400 (Bad Request)} if the blankAnswer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BlankAnswerDTO> createBlankAnswer(@Valid @RequestBody BlankAnswerDTO blankAnswerDTO) throws URISyntaxException {
        LOG.debug("REST request to save BlankAnswer : {}", blankAnswerDTO);
        if (blankAnswerDTO.getId() != null) {
            throw new BadRequestAlertException("A new blankAnswer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        blankAnswerDTO = blankAnswerService.save(blankAnswerDTO);
        return ResponseEntity.created(new URI("/api/blank-answers/" + blankAnswerDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, blankAnswerDTO.getId().toString()))
            .body(blankAnswerDTO);
    }

    /**
     * {@code PUT  /blank-answers/:id} : Updates an existing blankAnswer.
     *
     * @param id the id of the blankAnswerDTO to save.
     * @param blankAnswerDTO the blankAnswerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated blankAnswerDTO,
     * or with status {@code 400 (Bad Request)} if the blankAnswerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the blankAnswerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BlankAnswerDTO> updateBlankAnswer(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BlankAnswerDTO blankAnswerDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update BlankAnswer : {}, {}", id, blankAnswerDTO);
        if (blankAnswerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, blankAnswerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!blankAnswerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        blankAnswerDTO = blankAnswerService.update(blankAnswerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, blankAnswerDTO.getId().toString()))
            .body(blankAnswerDTO);
    }

    /**
     * {@code PATCH  /blank-answers/:id} : Partial updates given fields of an existing blankAnswer, field will ignore if it is null
     *
     * @param id the id of the blankAnswerDTO to save.
     * @param blankAnswerDTO the blankAnswerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated blankAnswerDTO,
     * or with status {@code 400 (Bad Request)} if the blankAnswerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the blankAnswerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the blankAnswerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BlankAnswerDTO> partialUpdateBlankAnswer(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BlankAnswerDTO blankAnswerDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update BlankAnswer partially : {}, {}", id, blankAnswerDTO);
        if (blankAnswerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, blankAnswerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!blankAnswerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BlankAnswerDTO> result = blankAnswerService.partialUpdate(blankAnswerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, blankAnswerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /blank-answers} : get all the blankAnswers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of blankAnswers in body.
     */
    @GetMapping("")
    public ResponseEntity<List<BlankAnswerDTO>> getAllBlankAnswers(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of BlankAnswers");
        Page<BlankAnswerDTO> page = blankAnswerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /blank-answers/:id} : get the "id" blankAnswer.
     *
     * @param id the id of the blankAnswerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the blankAnswerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BlankAnswerDTO> getBlankAnswer(@PathVariable("id") Long id) {
        LOG.debug("REST request to get BlankAnswer : {}", id);
        Optional<BlankAnswerDTO> blankAnswerDTO = blankAnswerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(blankAnswerDTO);
    }

    /**
     * {@code DELETE  /blank-answers/:id} : delete the "id" blankAnswer.
     *
     * @param id the id of the blankAnswerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlankAnswer(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete BlankAnswer : {}", id);
        blankAnswerService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
