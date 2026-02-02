package com.skillsdoor.skillverify.web.rest;

import com.skillsdoor.skillverify.repository.TrainingProviderRepository;
import com.skillsdoor.skillverify.service.TrainingProviderService;
import com.skillsdoor.skillverify.service.dto.TrainingProviderDTO;
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
 * REST controller for managing {@link com.skillsdoor.skillverify.domain.TrainingProvider}.
 */
@RestController
@RequestMapping("/api/training-providers")
public class TrainingProviderResource {

    private static final Logger LOG = LoggerFactory.getLogger(TrainingProviderResource.class);

    private static final String ENTITY_NAME = "trainingProvider";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrainingProviderService trainingProviderService;

    private final TrainingProviderRepository trainingProviderRepository;

    public TrainingProviderResource(
        TrainingProviderService trainingProviderService,
        TrainingProviderRepository trainingProviderRepository
    ) {
        this.trainingProviderService = trainingProviderService;
        this.trainingProviderRepository = trainingProviderRepository;
    }

    /**
     * {@code POST  /training-providers} : Create a new trainingProvider.
     *
     * @param trainingProviderDTO the trainingProviderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trainingProviderDTO, or with status {@code 400 (Bad Request)} if the trainingProvider has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TrainingProviderDTO> createTrainingProvider(@Valid @RequestBody TrainingProviderDTO trainingProviderDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save TrainingProvider : {}", trainingProviderDTO);
        if (trainingProviderDTO.getId() != null) {
            throw new BadRequestAlertException("A new trainingProvider cannot already have an ID", ENTITY_NAME, "idexists");
        }
        trainingProviderDTO = trainingProviderService.save(trainingProviderDTO);
        return ResponseEntity.created(new URI("/api/training-providers/" + trainingProviderDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, trainingProviderDTO.getId().toString()))
            .body(trainingProviderDTO);
    }

    /**
     * {@code PUT  /training-providers/:id} : Updates an existing trainingProvider.
     *
     * @param id the id of the trainingProviderDTO to save.
     * @param trainingProviderDTO the trainingProviderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trainingProviderDTO,
     * or with status {@code 400 (Bad Request)} if the trainingProviderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trainingProviderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TrainingProviderDTO> updateTrainingProvider(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TrainingProviderDTO trainingProviderDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update TrainingProvider : {}, {}", id, trainingProviderDTO);
        if (trainingProviderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trainingProviderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trainingProviderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        trainingProviderDTO = trainingProviderService.update(trainingProviderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trainingProviderDTO.getId().toString()))
            .body(trainingProviderDTO);
    }

    /**
     * {@code PATCH  /training-providers/:id} : Partial updates given fields of an existing trainingProvider, field will ignore if it is null
     *
     * @param id the id of the trainingProviderDTO to save.
     * @param trainingProviderDTO the trainingProviderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trainingProviderDTO,
     * or with status {@code 400 (Bad Request)} if the trainingProviderDTO is not valid,
     * or with status {@code 404 (Not Found)} if the trainingProviderDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the trainingProviderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TrainingProviderDTO> partialUpdateTrainingProvider(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TrainingProviderDTO trainingProviderDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update TrainingProvider partially : {}, {}", id, trainingProviderDTO);
        if (trainingProviderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trainingProviderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trainingProviderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TrainingProviderDTO> result = trainingProviderService.partialUpdate(trainingProviderDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trainingProviderDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /training-providers} : get all the trainingProviders.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trainingProviders in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TrainingProviderDTO>> getAllTrainingProviders(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of TrainingProviders");
        Page<TrainingProviderDTO> page;
        if (eagerload) {
            page = trainingProviderService.findAllWithEagerRelationships(pageable);
        } else {
            page = trainingProviderService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /training-providers/:id} : get the "id" trainingProvider.
     *
     * @param id the id of the trainingProviderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trainingProviderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TrainingProviderDTO> getTrainingProvider(@PathVariable("id") Long id) {
        LOG.debug("REST request to get TrainingProvider : {}", id);
        Optional<TrainingProviderDTO> trainingProviderDTO = trainingProviderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trainingProviderDTO);
    }

    /**
     * {@code DELETE  /training-providers/:id} : delete the "id" trainingProvider.
     *
     * @param id the id of the trainingProviderDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainingProvider(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete TrainingProvider : {}", id);
        trainingProviderService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
