package com.skillsdoor.skillverify.service.impl;

import com.skillsdoor.skillverify.domain.BindList;
import com.skillsdoor.skillverify.repository.BindListRepository;
import com.skillsdoor.skillverify.service.BindListService;
import com.skillsdoor.skillverify.service.dto.BindListDTO;
import com.skillsdoor.skillverify.service.mapper.BindListMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.skillsdoor.skillverify.domain.BindList}.
 */
@Service
@Transactional
public class BindListServiceImpl implements BindListService {

    private static final Logger LOG = LoggerFactory.getLogger(BindListServiceImpl.class);

    private final BindListRepository bindListRepository;

    private final BindListMapper bindListMapper;

    public BindListServiceImpl(BindListRepository bindListRepository, BindListMapper bindListMapper) {
        this.bindListRepository = bindListRepository;
        this.bindListMapper = bindListMapper;
    }

    @Override
    public BindListDTO save(BindListDTO bindListDTO) {
        LOG.debug("Request to save BindList : {}", bindListDTO);
        BindList bindList = bindListMapper.toEntity(bindListDTO);
        bindList = bindListRepository.save(bindList);
        return bindListMapper.toDto(bindList);
    }

    @Override
    public BindListDTO update(BindListDTO bindListDTO) {
        LOG.debug("Request to update BindList : {}", bindListDTO);
        BindList bindList = bindListMapper.toEntity(bindListDTO);
        bindList = bindListRepository.save(bindList);
        return bindListMapper.toDto(bindList);
    }

    @Override
    public Optional<BindListDTO> partialUpdate(BindListDTO bindListDTO) {
        LOG.debug("Request to partially update BindList : {}", bindListDTO);

        return bindListRepository
            .findById(bindListDTO.getId())
            .map(existingBindList -> {
                bindListMapper.partialUpdate(existingBindList, bindListDTO);

                return existingBindList;
            })
            .map(bindListRepository::save)
            .map(bindListMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BindListDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all BindLists");
        return bindListRepository.findAll(pageable).map(bindListMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BindListDTO> findOne(Long id) {
        LOG.debug("Request to get BindList : {}", id);
        return bindListRepository.findById(id).map(bindListMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete BindList : {}", id);
        bindListRepository.deleteById(id);
    }
}
