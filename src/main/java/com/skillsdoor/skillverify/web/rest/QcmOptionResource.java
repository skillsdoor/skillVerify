package com.skillsdoor.skillverify.web.rest;

import com.skillsdoor.skillverify.repository.QcmOptionRepository;
import com.skillsdoor.skillverify.service.QcmOptionService;
import com.skillsdoor.skillverify.service.dto.QcmOptionDTO;
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
 * REST controller for managing {@link com.skillsdoor.skillverify.domain.QcmOption}.
 */
@RestController
@RequestMapping("/api/qcm-options")
public class QcmOptionResource {

    private static final Logger LOG = LoggerFactory.getLogger(QcmOptionResource.class);

    private static final String ENTITY_NAME = "qcmOption";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QcmOptionService qcmOptionService;

    private final QcmOptionRepository qcmOptionRepository;

    public QcmOptionResource(QcmOptionService qcmOptionService, QcmOptionRepository qcmOptionRepository) {
        this.qcmOptionService = qcmOptionService;
        this.qcmOptionRepository = qcmOptionRepository;
    }

    /**
     * {@code POST  /qcm-options} : Create a new qcmOption.
     *
     * @param qcmOptionDTO the qcmOptionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new qcmOptionDTO, or with status {@code 400 (Bad Request)} if the qcmOption has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<QcmOptionDTO> createQcmOption(@Valid @RequestBody QcmOptionDTO qcmOptionDTO) throws URISyntaxException {
        LOG.debug("REST request to save QcmOption : {}", qcmOptionDTO);
        if (qcmOptionDTO.getId() != null) {
            throw new BadRequestAlertException("A new qcmOption cannot already have an ID", ENTITY_NAME, "idexists");
        }
        qcmOptionDTO = qcmOptionService.save(qcmOptionDTO);
        return ResponseEntity.created(new URI("/api/qcm-options/" + qcmOptionDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, qcmOptionDTO.getId().toString()))
            .body(qcmOptionDTO);
    }

    /**
     * {@code PUT  /qcm-options/:id} : Updates an existing qcmOption.
     *
     * @param id the id of the qcmOptionDTO to save.
     * @param qcmOptionDTO the qcmOptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qcmOptionDTO,
     * or with status {@code 400 (Bad Request)} if the qcmOptionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the qcmOptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<QcmOptionDTO> updateQcmOption(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody QcmOptionDTO qcmOptionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update QcmOption : {}, {}", id, qcmOptionDTO);
        if (qcmOptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qcmOptionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qcmOptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        qcmOptionDTO = qcmOptionService.update(qcmOptionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qcmOptionDTO.getId().toString()))
            .body(qcmOptionDTO);
    }

    /**
     * {@code PATCH  /qcm-options/:id} : Partial updates given fields of an existing qcmOption, field will ignore if it is null
     *
     * @param id the id of the qcmOptionDTO to save.
     * @param qcmOptionDTO the qcmOptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qcmOptionDTO,
     * or with status {@code 400 (Bad Request)} if the qcmOptionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the qcmOptionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the qcmOptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<QcmOptionDTO> partialUpdateQcmOption(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody QcmOptionDTO qcmOptionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update QcmOption partially : {}, {}", id, qcmOptionDTO);
        if (qcmOptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qcmOptionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qcmOptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<QcmOptionDTO> result = qcmOptionService.partialUpdate(qcmOptionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qcmOptionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /qcm-options} : get all the qcmOptions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of qcmOptions in body.
     */
    @GetMapping("")
    public ResponseEntity<List<QcmOptionDTO>> getAllQcmOptions(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of QcmOptions");
        Page<QcmOptionDTO> page = qcmOptionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /qcm-options/:id} : get the "id" qcmOption.
     *
     * @param id the id of the qcmOptionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the qcmOptionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<QcmOptionDTO> getQcmOption(@PathVariable("id") Long id) {
        LOG.debug("REST request to get QcmOption : {}", id);
        Optional<QcmOptionDTO> qcmOptionDTO = qcmOptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(qcmOptionDTO);
    }

    /**
     * {@code DELETE  /qcm-options/:id} : delete the "id" qcmOption.
     *
     * @param id the id of the qcmOptionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQcmOption(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete QcmOption : {}", id);
        qcmOptionService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
