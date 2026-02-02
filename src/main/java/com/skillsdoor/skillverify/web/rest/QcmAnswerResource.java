package com.skillsdoor.skillverify.web.rest;

import com.skillsdoor.skillverify.repository.QcmAnswerRepository;
import com.skillsdoor.skillverify.service.QcmAnswerService;
import com.skillsdoor.skillverify.service.dto.QcmAnswerDTO;
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
 * REST controller for managing {@link com.skillsdoor.skillverify.domain.QcmAnswer}.
 */
@RestController
@RequestMapping("/api/qcm-answers")
public class QcmAnswerResource {

    private static final Logger LOG = LoggerFactory.getLogger(QcmAnswerResource.class);

    private static final String ENTITY_NAME = "qcmAnswer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QcmAnswerService qcmAnswerService;

    private final QcmAnswerRepository qcmAnswerRepository;

    public QcmAnswerResource(QcmAnswerService qcmAnswerService, QcmAnswerRepository qcmAnswerRepository) {
        this.qcmAnswerService = qcmAnswerService;
        this.qcmAnswerRepository = qcmAnswerRepository;
    }

    /**
     * {@code POST  /qcm-answers} : Create a new qcmAnswer.
     *
     * @param qcmAnswerDTO the qcmAnswerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new qcmAnswerDTO, or with status {@code 400 (Bad Request)} if the qcmAnswer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<QcmAnswerDTO> createQcmAnswer(@Valid @RequestBody QcmAnswerDTO qcmAnswerDTO) throws URISyntaxException {
        LOG.debug("REST request to save QcmAnswer : {}", qcmAnswerDTO);
        if (qcmAnswerDTO.getId() != null) {
            throw new BadRequestAlertException("A new qcmAnswer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        qcmAnswerDTO = qcmAnswerService.save(qcmAnswerDTO);
        return ResponseEntity.created(new URI("/api/qcm-answers/" + qcmAnswerDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, qcmAnswerDTO.getId().toString()))
            .body(qcmAnswerDTO);
    }

    /**
     * {@code PUT  /qcm-answers/:id} : Updates an existing qcmAnswer.
     *
     * @param id the id of the qcmAnswerDTO to save.
     * @param qcmAnswerDTO the qcmAnswerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qcmAnswerDTO,
     * or with status {@code 400 (Bad Request)} if the qcmAnswerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the qcmAnswerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<QcmAnswerDTO> updateQcmAnswer(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody QcmAnswerDTO qcmAnswerDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update QcmAnswer : {}, {}", id, qcmAnswerDTO);
        if (qcmAnswerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qcmAnswerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qcmAnswerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        qcmAnswerDTO = qcmAnswerService.update(qcmAnswerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qcmAnswerDTO.getId().toString()))
            .body(qcmAnswerDTO);
    }

    /**
     * {@code PATCH  /qcm-answers/:id} : Partial updates given fields of an existing qcmAnswer, field will ignore if it is null
     *
     * @param id the id of the qcmAnswerDTO to save.
     * @param qcmAnswerDTO the qcmAnswerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qcmAnswerDTO,
     * or with status {@code 400 (Bad Request)} if the qcmAnswerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the qcmAnswerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the qcmAnswerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<QcmAnswerDTO> partialUpdateQcmAnswer(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody QcmAnswerDTO qcmAnswerDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update QcmAnswer partially : {}, {}", id, qcmAnswerDTO);
        if (qcmAnswerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qcmAnswerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qcmAnswerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<QcmAnswerDTO> result = qcmAnswerService.partialUpdate(qcmAnswerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qcmAnswerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /qcm-answers} : get all the qcmAnswers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of qcmAnswers in body.
     */
    @GetMapping("")
    public ResponseEntity<List<QcmAnswerDTO>> getAllQcmAnswers(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of QcmAnswers");
        Page<QcmAnswerDTO> page = qcmAnswerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /qcm-answers/:id} : get the "id" qcmAnswer.
     *
     * @param id the id of the qcmAnswerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the qcmAnswerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<QcmAnswerDTO> getQcmAnswer(@PathVariable("id") Long id) {
        LOG.debug("REST request to get QcmAnswer : {}", id);
        Optional<QcmAnswerDTO> qcmAnswerDTO = qcmAnswerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(qcmAnswerDTO);
    }

    /**
     * {@code DELETE  /qcm-answers/:id} : delete the "id" qcmAnswer.
     *
     * @param id the id of the qcmAnswerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQcmAnswer(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete QcmAnswer : {}", id);
        qcmAnswerService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
