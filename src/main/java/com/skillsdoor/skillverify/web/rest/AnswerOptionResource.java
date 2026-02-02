package com.skillsdoor.skillverify.web.rest;

import com.skillsdoor.skillverify.repository.AnswerOptionRepository;
import com.skillsdoor.skillverify.service.AnswerOptionService;
import com.skillsdoor.skillverify.service.dto.AnswerOptionDTO;
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
 * REST controller for managing {@link com.skillsdoor.skillverify.domain.AnswerOption}.
 */
@RestController
@RequestMapping("/api/answer-options")
public class AnswerOptionResource {

    private static final Logger LOG = LoggerFactory.getLogger(AnswerOptionResource.class);

    private static final String ENTITY_NAME = "answerOption";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnswerOptionService answerOptionService;

    private final AnswerOptionRepository answerOptionRepository;

    public AnswerOptionResource(AnswerOptionService answerOptionService, AnswerOptionRepository answerOptionRepository) {
        this.answerOptionService = answerOptionService;
        this.answerOptionRepository = answerOptionRepository;
    }

    /**
     * {@code POST  /answer-options} : Create a new answerOption.
     *
     * @param answerOptionDTO the answerOptionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new answerOptionDTO, or with status {@code 400 (Bad Request)} if the answerOption has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AnswerOptionDTO> createAnswerOption(@Valid @RequestBody AnswerOptionDTO answerOptionDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save AnswerOption : {}", answerOptionDTO);
        if (answerOptionDTO.getId() != null) {
            throw new BadRequestAlertException("A new answerOption cannot already have an ID", ENTITY_NAME, "idexists");
        }
        answerOptionDTO = answerOptionService.save(answerOptionDTO);
        return ResponseEntity.created(new URI("/api/answer-options/" + answerOptionDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, answerOptionDTO.getId().toString()))
            .body(answerOptionDTO);
    }

    /**
     * {@code PUT  /answer-options/:id} : Updates an existing answerOption.
     *
     * @param id the id of the answerOptionDTO to save.
     * @param answerOptionDTO the answerOptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated answerOptionDTO,
     * or with status {@code 400 (Bad Request)} if the answerOptionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the answerOptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AnswerOptionDTO> updateAnswerOption(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AnswerOptionDTO answerOptionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update AnswerOption : {}, {}", id, answerOptionDTO);
        if (answerOptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, answerOptionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!answerOptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        answerOptionDTO = answerOptionService.update(answerOptionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, answerOptionDTO.getId().toString()))
            .body(answerOptionDTO);
    }

    /**
     * {@code PATCH  /answer-options/:id} : Partial updates given fields of an existing answerOption, field will ignore if it is null
     *
     * @param id the id of the answerOptionDTO to save.
     * @param answerOptionDTO the answerOptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated answerOptionDTO,
     * or with status {@code 400 (Bad Request)} if the answerOptionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the answerOptionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the answerOptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AnswerOptionDTO> partialUpdateAnswerOption(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AnswerOptionDTO answerOptionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update AnswerOption partially : {}, {}", id, answerOptionDTO);
        if (answerOptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, answerOptionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!answerOptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnswerOptionDTO> result = answerOptionService.partialUpdate(answerOptionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, answerOptionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /answer-options} : get all the answerOptions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of answerOptions in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AnswerOptionDTO>> getAllAnswerOptions(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of AnswerOptions");
        Page<AnswerOptionDTO> page = answerOptionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /answer-options/:id} : get the "id" answerOption.
     *
     * @param id the id of the answerOptionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the answerOptionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AnswerOptionDTO> getAnswerOption(@PathVariable("id") Long id) {
        LOG.debug("REST request to get AnswerOption : {}", id);
        Optional<AnswerOptionDTO> answerOptionDTO = answerOptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(answerOptionDTO);
    }

    /**
     * {@code DELETE  /answer-options/:id} : delete the "id" answerOption.
     *
     * @param id the id of the answerOptionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnswerOption(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete AnswerOption : {}", id);
        answerOptionService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
