package com.skillsdoor.skillverify.web.rest;

import com.skillsdoor.skillverify.repository.BlankSpaceRepository;
import com.skillsdoor.skillverify.service.BlankSpaceService;
import com.skillsdoor.skillverify.service.dto.BlankSpaceDTO;
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
 * REST controller for managing {@link com.skillsdoor.skillverify.domain.BlankSpace}.
 */
@RestController
@RequestMapping("/api/blank-spaces")
public class BlankSpaceResource {

    private static final Logger LOG = LoggerFactory.getLogger(BlankSpaceResource.class);

    private static final String ENTITY_NAME = "blankSpace";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BlankSpaceService blankSpaceService;

    private final BlankSpaceRepository blankSpaceRepository;

    public BlankSpaceResource(BlankSpaceService blankSpaceService, BlankSpaceRepository blankSpaceRepository) {
        this.blankSpaceService = blankSpaceService;
        this.blankSpaceRepository = blankSpaceRepository;
    }

    /**
     * {@code POST  /blank-spaces} : Create a new blankSpace.
     *
     * @param blankSpaceDTO the blankSpaceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new blankSpaceDTO, or with status {@code 400 (Bad Request)} if the blankSpace has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BlankSpaceDTO> createBlankSpace(@Valid @RequestBody BlankSpaceDTO blankSpaceDTO) throws URISyntaxException {
        LOG.debug("REST request to save BlankSpace : {}", blankSpaceDTO);
        if (blankSpaceDTO.getId() != null) {
            throw new BadRequestAlertException("A new blankSpace cannot already have an ID", ENTITY_NAME, "idexists");
        }
        blankSpaceDTO = blankSpaceService.save(blankSpaceDTO);
        return ResponseEntity.created(new URI("/api/blank-spaces/" + blankSpaceDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, blankSpaceDTO.getId().toString()))
            .body(blankSpaceDTO);
    }

    /**
     * {@code PUT  /blank-spaces/:id} : Updates an existing blankSpace.
     *
     * @param id the id of the blankSpaceDTO to save.
     * @param blankSpaceDTO the blankSpaceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated blankSpaceDTO,
     * or with status {@code 400 (Bad Request)} if the blankSpaceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the blankSpaceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BlankSpaceDTO> updateBlankSpace(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BlankSpaceDTO blankSpaceDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update BlankSpace : {}, {}", id, blankSpaceDTO);
        if (blankSpaceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, blankSpaceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!blankSpaceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        blankSpaceDTO = blankSpaceService.update(blankSpaceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, blankSpaceDTO.getId().toString()))
            .body(blankSpaceDTO);
    }

    /**
     * {@code PATCH  /blank-spaces/:id} : Partial updates given fields of an existing blankSpace, field will ignore if it is null
     *
     * @param id the id of the blankSpaceDTO to save.
     * @param blankSpaceDTO the blankSpaceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated blankSpaceDTO,
     * or with status {@code 400 (Bad Request)} if the blankSpaceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the blankSpaceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the blankSpaceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BlankSpaceDTO> partialUpdateBlankSpace(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BlankSpaceDTO blankSpaceDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update BlankSpace partially : {}, {}", id, blankSpaceDTO);
        if (blankSpaceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, blankSpaceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!blankSpaceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BlankSpaceDTO> result = blankSpaceService.partialUpdate(blankSpaceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, blankSpaceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /blank-spaces} : get all the blankSpaces.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of blankSpaces in body.
     */
    @GetMapping("")
    public ResponseEntity<List<BlankSpaceDTO>> getAllBlankSpaces(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of BlankSpaces");
        Page<BlankSpaceDTO> page = blankSpaceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /blank-spaces/:id} : get the "id" blankSpace.
     *
     * @param id the id of the blankSpaceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the blankSpaceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BlankSpaceDTO> getBlankSpace(@PathVariable("id") Long id) {
        LOG.debug("REST request to get BlankSpace : {}", id);
        Optional<BlankSpaceDTO> blankSpaceDTO = blankSpaceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(blankSpaceDTO);
    }

    /**
     * {@code DELETE  /blank-spaces/:id} : delete the "id" blankSpace.
     *
     * @param id the id of the blankSpaceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlankSpace(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete BlankSpace : {}", id);
        blankSpaceService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
