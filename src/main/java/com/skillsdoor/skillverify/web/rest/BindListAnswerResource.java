package com.skillsdoor.skillverify.web.rest;

import com.skillsdoor.skillverify.repository.BindListAnswerRepository;
import com.skillsdoor.skillverify.service.BindListAnswerService;
import com.skillsdoor.skillverify.service.dto.BindListAnswerDTO;
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
 * REST controller for managing {@link com.skillsdoor.skillverify.domain.BindListAnswer}.
 */
@RestController
@RequestMapping("/api/bind-list-answers")
public class BindListAnswerResource {

    private static final Logger LOG = LoggerFactory.getLogger(BindListAnswerResource.class);

    private static final String ENTITY_NAME = "bindListAnswer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BindListAnswerService bindListAnswerService;

    private final BindListAnswerRepository bindListAnswerRepository;

    public BindListAnswerResource(BindListAnswerService bindListAnswerService, BindListAnswerRepository bindListAnswerRepository) {
        this.bindListAnswerService = bindListAnswerService;
        this.bindListAnswerRepository = bindListAnswerRepository;
    }

    /**
     * {@code POST  /bind-list-answers} : Create a new bindListAnswer.
     *
     * @param bindListAnswerDTO the bindListAnswerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bindListAnswerDTO, or with status {@code 400 (Bad Request)} if the bindListAnswer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BindListAnswerDTO> createBindListAnswer(@Valid @RequestBody BindListAnswerDTO bindListAnswerDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save BindListAnswer : {}", bindListAnswerDTO);
        if (bindListAnswerDTO.getId() != null) {
            throw new BadRequestAlertException("A new bindListAnswer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bindListAnswerDTO = bindListAnswerService.save(bindListAnswerDTO);
        return ResponseEntity.created(new URI("/api/bind-list-answers/" + bindListAnswerDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, bindListAnswerDTO.getId().toString()))
            .body(bindListAnswerDTO);
    }

    /**
     * {@code PUT  /bind-list-answers/:id} : Updates an existing bindListAnswer.
     *
     * @param id the id of the bindListAnswerDTO to save.
     * @param bindListAnswerDTO the bindListAnswerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bindListAnswerDTO,
     * or with status {@code 400 (Bad Request)} if the bindListAnswerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bindListAnswerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BindListAnswerDTO> updateBindListAnswer(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BindListAnswerDTO bindListAnswerDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update BindListAnswer : {}, {}", id, bindListAnswerDTO);
        if (bindListAnswerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bindListAnswerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bindListAnswerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bindListAnswerDTO = bindListAnswerService.update(bindListAnswerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bindListAnswerDTO.getId().toString()))
            .body(bindListAnswerDTO);
    }

    /**
     * {@code PATCH  /bind-list-answers/:id} : Partial updates given fields of an existing bindListAnswer, field will ignore if it is null
     *
     * @param id the id of the bindListAnswerDTO to save.
     * @param bindListAnswerDTO the bindListAnswerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bindListAnswerDTO,
     * or with status {@code 400 (Bad Request)} if the bindListAnswerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bindListAnswerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bindListAnswerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BindListAnswerDTO> partialUpdateBindListAnswer(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BindListAnswerDTO bindListAnswerDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update BindListAnswer partially : {}, {}", id, bindListAnswerDTO);
        if (bindListAnswerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bindListAnswerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bindListAnswerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BindListAnswerDTO> result = bindListAnswerService.partialUpdate(bindListAnswerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bindListAnswerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bind-list-answers} : get all the bindListAnswers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bindListAnswers in body.
     */
    @GetMapping("")
    public ResponseEntity<List<BindListAnswerDTO>> getAllBindListAnswers(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of BindListAnswers");
        Page<BindListAnswerDTO> page = bindListAnswerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bind-list-answers/:id} : get the "id" bindListAnswer.
     *
     * @param id the id of the bindListAnswerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bindListAnswerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BindListAnswerDTO> getBindListAnswer(@PathVariable("id") Long id) {
        LOG.debug("REST request to get BindListAnswer : {}", id);
        Optional<BindListAnswerDTO> bindListAnswerDTO = bindListAnswerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bindListAnswerDTO);
    }

    /**
     * {@code DELETE  /bind-list-answers/:id} : delete the "id" bindListAnswer.
     *
     * @param id the id of the bindListAnswerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBindListAnswer(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete BindListAnswer : {}", id);
        bindListAnswerService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
