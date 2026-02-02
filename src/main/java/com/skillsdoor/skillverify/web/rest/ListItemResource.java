package com.skillsdoor.skillverify.web.rest;

import com.skillsdoor.skillverify.repository.ListItemRepository;
import com.skillsdoor.skillverify.service.ListItemService;
import com.skillsdoor.skillverify.service.dto.ListItemDTO;
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
 * REST controller for managing {@link com.skillsdoor.skillverify.domain.ListItem}.
 */
@RestController
@RequestMapping("/api/list-items")
public class ListItemResource {

    private static final Logger LOG = LoggerFactory.getLogger(ListItemResource.class);

    private static final String ENTITY_NAME = "listItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ListItemService listItemService;

    private final ListItemRepository listItemRepository;

    public ListItemResource(ListItemService listItemService, ListItemRepository listItemRepository) {
        this.listItemService = listItemService;
        this.listItemRepository = listItemRepository;
    }

    /**
     * {@code POST  /list-items} : Create a new listItem.
     *
     * @param listItemDTO the listItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new listItemDTO, or with status {@code 400 (Bad Request)} if the listItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ListItemDTO> createListItem(@Valid @RequestBody ListItemDTO listItemDTO) throws URISyntaxException {
        LOG.debug("REST request to save ListItem : {}", listItemDTO);
        if (listItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new listItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        listItemDTO = listItemService.save(listItemDTO);
        return ResponseEntity.created(new URI("/api/list-items/" + listItemDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, listItemDTO.getId().toString()))
            .body(listItemDTO);
    }

    /**
     * {@code PUT  /list-items/:id} : Updates an existing listItem.
     *
     * @param id the id of the listItemDTO to save.
     * @param listItemDTO the listItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listItemDTO,
     * or with status {@code 400 (Bad Request)} if the listItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the listItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ListItemDTO> updateListItem(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ListItemDTO listItemDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update ListItem : {}, {}", id, listItemDTO);
        if (listItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, listItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!listItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        listItemDTO = listItemService.update(listItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listItemDTO.getId().toString()))
            .body(listItemDTO);
    }

    /**
     * {@code PATCH  /list-items/:id} : Partial updates given fields of an existing listItem, field will ignore if it is null
     *
     * @param id the id of the listItemDTO to save.
     * @param listItemDTO the listItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listItemDTO,
     * or with status {@code 400 (Bad Request)} if the listItemDTO is not valid,
     * or with status {@code 404 (Not Found)} if the listItemDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the listItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ListItemDTO> partialUpdateListItem(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ListItemDTO listItemDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ListItem partially : {}, {}", id, listItemDTO);
        if (listItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, listItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!listItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ListItemDTO> result = listItemService.partialUpdate(listItemDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listItemDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /list-items} : get all the listItems.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of listItems in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ListItemDTO>> getAllListItems(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of ListItems");
        Page<ListItemDTO> page = listItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /list-items/:id} : get the "id" listItem.
     *
     * @param id the id of the listItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ListItemDTO> getListItem(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ListItem : {}", id);
        Optional<ListItemDTO> listItemDTO = listItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(listItemDTO);
    }

    /**
     * {@code DELETE  /list-items/:id} : delete the "id" listItem.
     *
     * @param id the id of the listItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListItem(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete ListItem : {}", id);
        listItemService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
