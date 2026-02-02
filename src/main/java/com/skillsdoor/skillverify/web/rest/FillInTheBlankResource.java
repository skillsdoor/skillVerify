package com.skillsdoor.skillverify.web.rest;

import com.skillsdoor.skillverify.repository.FillInTheBlankRepository;
import com.skillsdoor.skillverify.service.FillInTheBlankService;
import com.skillsdoor.skillverify.service.dto.FillInTheBlankDTO;
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
 * REST controller for managing {@link com.skillsdoor.skillverify.domain.FillInTheBlank}.
 */
@RestController
@RequestMapping("/api/fill-in-the-blanks")
public class FillInTheBlankResource {

    private static final Logger LOG = LoggerFactory.getLogger(FillInTheBlankResource.class);

    private static final String ENTITY_NAME = "fillInTheBlank";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FillInTheBlankService fillInTheBlankService;

    private final FillInTheBlankRepository fillInTheBlankRepository;

    public FillInTheBlankResource(FillInTheBlankService fillInTheBlankService, FillInTheBlankRepository fillInTheBlankRepository) {
        this.fillInTheBlankService = fillInTheBlankService;
        this.fillInTheBlankRepository = fillInTheBlankRepository;
    }

    /**
     * {@code POST  /fill-in-the-blanks} : Create a new fillInTheBlank.
     *
     * @param fillInTheBlankDTO the fillInTheBlankDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fillInTheBlankDTO, or with status {@code 400 (Bad Request)} if the fillInTheBlank has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FillInTheBlankDTO> createFillInTheBlank(@Valid @RequestBody FillInTheBlankDTO fillInTheBlankDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save FillInTheBlank : {}", fillInTheBlankDTO);
        if (fillInTheBlankDTO.getId() != null) {
            throw new BadRequestAlertException("A new fillInTheBlank cannot already have an ID", ENTITY_NAME, "idexists");
        }
        fillInTheBlankDTO = fillInTheBlankService.save(fillInTheBlankDTO);
        return ResponseEntity.created(new URI("/api/fill-in-the-blanks/" + fillInTheBlankDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, fillInTheBlankDTO.getId().toString()))
            .body(fillInTheBlankDTO);
    }

    /**
     * {@code PUT  /fill-in-the-blanks/:id} : Updates an existing fillInTheBlank.
     *
     * @param id the id of the fillInTheBlankDTO to save.
     * @param fillInTheBlankDTO the fillInTheBlankDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fillInTheBlankDTO,
     * or with status {@code 400 (Bad Request)} if the fillInTheBlankDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fillInTheBlankDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FillInTheBlankDTO> updateFillInTheBlank(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FillInTheBlankDTO fillInTheBlankDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update FillInTheBlank : {}, {}", id, fillInTheBlankDTO);
        if (fillInTheBlankDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fillInTheBlankDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fillInTheBlankRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        fillInTheBlankDTO = fillInTheBlankService.update(fillInTheBlankDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fillInTheBlankDTO.getId().toString()))
            .body(fillInTheBlankDTO);
    }

    /**
     * {@code PATCH  /fill-in-the-blanks/:id} : Partial updates given fields of an existing fillInTheBlank, field will ignore if it is null
     *
     * @param id the id of the fillInTheBlankDTO to save.
     * @param fillInTheBlankDTO the fillInTheBlankDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fillInTheBlankDTO,
     * or with status {@code 400 (Bad Request)} if the fillInTheBlankDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fillInTheBlankDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fillInTheBlankDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FillInTheBlankDTO> partialUpdateFillInTheBlank(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FillInTheBlankDTO fillInTheBlankDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update FillInTheBlank partially : {}, {}", id, fillInTheBlankDTO);
        if (fillInTheBlankDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fillInTheBlankDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fillInTheBlankRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FillInTheBlankDTO> result = fillInTheBlankService.partialUpdate(fillInTheBlankDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fillInTheBlankDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /fill-in-the-blanks} : get all the fillInTheBlanks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fillInTheBlanks in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FillInTheBlankDTO>> getAllFillInTheBlanks(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of FillInTheBlanks");
        Page<FillInTheBlankDTO> page = fillInTheBlankService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fill-in-the-blanks/:id} : get the "id" fillInTheBlank.
     *
     * @param id the id of the fillInTheBlankDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fillInTheBlankDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FillInTheBlankDTO> getFillInTheBlank(@PathVariable("id") Long id) {
        LOG.debug("REST request to get FillInTheBlank : {}", id);
        Optional<FillInTheBlankDTO> fillInTheBlankDTO = fillInTheBlankService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fillInTheBlankDTO);
    }

    /**
     * {@code DELETE  /fill-in-the-blanks/:id} : delete the "id" fillInTheBlank.
     *
     * @param id the id of the fillInTheBlankDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFillInTheBlank(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete FillInTheBlank : {}", id);
        fillInTheBlankService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
