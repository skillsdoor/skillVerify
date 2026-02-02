package com.skillsdoor.skillverify.web.rest;

import com.skillsdoor.skillverify.repository.BindAnswerRepository;
import com.skillsdoor.skillverify.service.BindAnswerService;
import com.skillsdoor.skillverify.service.dto.BindAnswerDTO;
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
 * REST controller for managing {@link com.skillsdoor.skillverify.domain.BindAnswer}.
 */
@RestController
@RequestMapping("/api/bind-answers")
public class BindAnswerResource {

    private static final Logger LOG = LoggerFactory.getLogger(BindAnswerResource.class);

    private static final String ENTITY_NAME = "bindAnswer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BindAnswerService bindAnswerService;

    private final BindAnswerRepository bindAnswerRepository;

    public BindAnswerResource(BindAnswerService bindAnswerService, BindAnswerRepository bindAnswerRepository) {
        this.bindAnswerService = bindAnswerService;
        this.bindAnswerRepository = bindAnswerRepository;
    }

    /**
     * {@code POST  /bind-answers} : Create a new bindAnswer.
     *
     * @param bindAnswerDTO the bindAnswerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bindAnswerDTO, or with status {@code 400 (Bad Request)} if the bindAnswer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BindAnswerDTO> createBindAnswer(@Valid @RequestBody BindAnswerDTO bindAnswerDTO) throws URISyntaxException {
        LOG.debug("REST request to save BindAnswer : {}", bindAnswerDTO);
        if (bindAnswerDTO.getId() != null) {
            throw new BadRequestAlertException("A new bindAnswer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bindAnswerDTO = bindAnswerService.save(bindAnswerDTO);
        return ResponseEntity.created(new URI("/api/bind-answers/" + bindAnswerDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, bindAnswerDTO.getId().toString()))
            .body(bindAnswerDTO);
    }

    /**
     * {@code PUT  /bind-answers/:id} : Updates an existing bindAnswer.
     *
     * @param id the id of the bindAnswerDTO to save.
     * @param bindAnswerDTO the bindAnswerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bindAnswerDTO,
     * or with status {@code 400 (Bad Request)} if the bindAnswerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bindAnswerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BindAnswerDTO> updateBindAnswer(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BindAnswerDTO bindAnswerDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update BindAnswer : {}, {}", id, bindAnswerDTO);
        if (bindAnswerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bindAnswerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bindAnswerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bindAnswerDTO = bindAnswerService.update(bindAnswerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bindAnswerDTO.getId().toString()))
            .body(bindAnswerDTO);
    }

    /**
     * {@code PATCH  /bind-answers/:id} : Partial updates given fields of an existing bindAnswer, field will ignore if it is null
     *
     * @param id the id of the bindAnswerDTO to save.
     * @param bindAnswerDTO the bindAnswerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bindAnswerDTO,
     * or with status {@code 400 (Bad Request)} if the bindAnswerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bindAnswerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bindAnswerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BindAnswerDTO> partialUpdateBindAnswer(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BindAnswerDTO bindAnswerDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update BindAnswer partially : {}, {}", id, bindAnswerDTO);
        if (bindAnswerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bindAnswerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bindAnswerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BindAnswerDTO> result = bindAnswerService.partialUpdate(bindAnswerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bindAnswerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bind-answers} : get all the bindAnswers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bindAnswers in body.
     */
    @GetMapping("")
    public ResponseEntity<List<BindAnswerDTO>> getAllBindAnswers(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of BindAnswers");
        Page<BindAnswerDTO> page = bindAnswerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bind-answers/:id} : get the "id" bindAnswer.
     *
     * @param id the id of the bindAnswerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bindAnswerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BindAnswerDTO> getBindAnswer(@PathVariable("id") Long id) {
        LOG.debug("REST request to get BindAnswer : {}", id);
        Optional<BindAnswerDTO> bindAnswerDTO = bindAnswerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bindAnswerDTO);
    }

    /**
     * {@code DELETE  /bind-answers/:id} : delete the "id" bindAnswer.
     *
     * @param id the id of the bindAnswerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBindAnswer(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete BindAnswer : {}", id);
        bindAnswerService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
