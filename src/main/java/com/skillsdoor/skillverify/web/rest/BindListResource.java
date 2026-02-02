package com.skillsdoor.skillverify.web.rest;

import com.skillsdoor.skillverify.repository.BindListRepository;
import com.skillsdoor.skillverify.service.BindListService;
import com.skillsdoor.skillverify.service.dto.BindListDTO;
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
 * REST controller for managing {@link com.skillsdoor.skillverify.domain.BindList}.
 */
@RestController
@RequestMapping("/api/bind-lists")
public class BindListResource {

    private static final Logger LOG = LoggerFactory.getLogger(BindListResource.class);

    private static final String ENTITY_NAME = "bindList";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BindListService bindListService;

    private final BindListRepository bindListRepository;

    public BindListResource(BindListService bindListService, BindListRepository bindListRepository) {
        this.bindListService = bindListService;
        this.bindListRepository = bindListRepository;
    }

    /**
     * {@code POST  /bind-lists} : Create a new bindList.
     *
     * @param bindListDTO the bindListDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bindListDTO, or with status {@code 400 (Bad Request)} if the bindList has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BindListDTO> createBindList(@Valid @RequestBody BindListDTO bindListDTO) throws URISyntaxException {
        LOG.debug("REST request to save BindList : {}", bindListDTO);
        if (bindListDTO.getId() != null) {
            throw new BadRequestAlertException("A new bindList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bindListDTO = bindListService.save(bindListDTO);
        return ResponseEntity.created(new URI("/api/bind-lists/" + bindListDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, bindListDTO.getId().toString()))
            .body(bindListDTO);
    }

    /**
     * {@code PUT  /bind-lists/:id} : Updates an existing bindList.
     *
     * @param id the id of the bindListDTO to save.
     * @param bindListDTO the bindListDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bindListDTO,
     * or with status {@code 400 (Bad Request)} if the bindListDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bindListDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BindListDTO> updateBindList(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BindListDTO bindListDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update BindList : {}, {}", id, bindListDTO);
        if (bindListDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bindListDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bindListRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bindListDTO = bindListService.update(bindListDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bindListDTO.getId().toString()))
            .body(bindListDTO);
    }

    /**
     * {@code PATCH  /bind-lists/:id} : Partial updates given fields of an existing bindList, field will ignore if it is null
     *
     * @param id the id of the bindListDTO to save.
     * @param bindListDTO the bindListDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bindListDTO,
     * or with status {@code 400 (Bad Request)} if the bindListDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bindListDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bindListDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BindListDTO> partialUpdateBindList(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BindListDTO bindListDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update BindList partially : {}, {}", id, bindListDTO);
        if (bindListDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bindListDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bindListRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BindListDTO> result = bindListService.partialUpdate(bindListDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bindListDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bind-lists} : get all the bindLists.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bindLists in body.
     */
    @GetMapping("")
    public ResponseEntity<List<BindListDTO>> getAllBindLists(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of BindLists");
        Page<BindListDTO> page = bindListService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bind-lists/:id} : get the "id" bindList.
     *
     * @param id the id of the bindListDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bindListDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BindListDTO> getBindList(@PathVariable("id") Long id) {
        LOG.debug("REST request to get BindList : {}", id);
        Optional<BindListDTO> bindListDTO = bindListService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bindListDTO);
    }

    /**
     * {@code DELETE  /bind-lists/:id} : delete the "id" bindList.
     *
     * @param id the id of the bindListDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBindList(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete BindList : {}", id);
        bindListService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
