package com.skillsdoor.skillverify.service.impl;

import com.skillsdoor.skillverify.domain.ListItem;
import com.skillsdoor.skillverify.repository.ListItemRepository;
import com.skillsdoor.skillverify.service.ListItemService;
import com.skillsdoor.skillverify.service.dto.ListItemDTO;
import com.skillsdoor.skillverify.service.mapper.ListItemMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.skillsdoor.skillverify.domain.ListItem}.
 */
@Service
@Transactional
public class ListItemServiceImpl implements ListItemService {

    private static final Logger LOG = LoggerFactory.getLogger(ListItemServiceImpl.class);

    private final ListItemRepository listItemRepository;

    private final ListItemMapper listItemMapper;

    public ListItemServiceImpl(ListItemRepository listItemRepository, ListItemMapper listItemMapper) {
        this.listItemRepository = listItemRepository;
        this.listItemMapper = listItemMapper;
    }

    @Override
    public ListItemDTO save(ListItemDTO listItemDTO) {
        LOG.debug("Request to save ListItem : {}", listItemDTO);
        ListItem listItem = listItemMapper.toEntity(listItemDTO);
        listItem = listItemRepository.save(listItem);
        return listItemMapper.toDto(listItem);
    }

    @Override
    public ListItemDTO update(ListItemDTO listItemDTO) {
        LOG.debug("Request to update ListItem : {}", listItemDTO);
        ListItem listItem = listItemMapper.toEntity(listItemDTO);
        listItem = listItemRepository.save(listItem);
        return listItemMapper.toDto(listItem);
    }

    @Override
    public Optional<ListItemDTO> partialUpdate(ListItemDTO listItemDTO) {
        LOG.debug("Request to partially update ListItem : {}", listItemDTO);

        return listItemRepository
            .findById(listItemDTO.getId())
            .map(existingListItem -> {
                listItemMapper.partialUpdate(existingListItem, listItemDTO);

                return existingListItem;
            })
            .map(listItemRepository::save)
            .map(listItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ListItemDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all ListItems");
        return listItemRepository.findAll(pageable).map(listItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ListItemDTO> findOne(Long id) {
        LOG.debug("Request to get ListItem : {}", id);
        return listItemRepository.findById(id).map(listItemMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete ListItem : {}", id);
        listItemRepository.deleteById(id);
    }
}
