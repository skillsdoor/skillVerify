package com.skillsdoor.skillverify.service.impl;

import com.skillsdoor.skillverify.domain.BlankSpace;
import com.skillsdoor.skillverify.repository.BlankSpaceRepository;
import com.skillsdoor.skillverify.service.BlankSpaceService;
import com.skillsdoor.skillverify.service.dto.BlankSpaceDTO;
import com.skillsdoor.skillverify.service.mapper.BlankSpaceMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.skillsdoor.skillverify.domain.BlankSpace}.
 */
@Service
@Transactional
public class BlankSpaceServiceImpl implements BlankSpaceService {

    private static final Logger LOG = LoggerFactory.getLogger(BlankSpaceServiceImpl.class);

    private final BlankSpaceRepository blankSpaceRepository;

    private final BlankSpaceMapper blankSpaceMapper;

    public BlankSpaceServiceImpl(BlankSpaceRepository blankSpaceRepository, BlankSpaceMapper blankSpaceMapper) {
        this.blankSpaceRepository = blankSpaceRepository;
        this.blankSpaceMapper = blankSpaceMapper;
    }

    @Override
    public BlankSpaceDTO save(BlankSpaceDTO blankSpaceDTO) {
        LOG.debug("Request to save BlankSpace : {}", blankSpaceDTO);
        BlankSpace blankSpace = blankSpaceMapper.toEntity(blankSpaceDTO);
        blankSpace = blankSpaceRepository.save(blankSpace);
        return blankSpaceMapper.toDto(blankSpace);
    }

    @Override
    public BlankSpaceDTO update(BlankSpaceDTO blankSpaceDTO) {
        LOG.debug("Request to update BlankSpace : {}", blankSpaceDTO);
        BlankSpace blankSpace = blankSpaceMapper.toEntity(blankSpaceDTO);
        blankSpace = blankSpaceRepository.save(blankSpace);
        return blankSpaceMapper.toDto(blankSpace);
    }

    @Override
    public Optional<BlankSpaceDTO> partialUpdate(BlankSpaceDTO blankSpaceDTO) {
        LOG.debug("Request to partially update BlankSpace : {}", blankSpaceDTO);

        return blankSpaceRepository
            .findById(blankSpaceDTO.getId())
            .map(existingBlankSpace -> {
                blankSpaceMapper.partialUpdate(existingBlankSpace, blankSpaceDTO);

                return existingBlankSpace;
            })
            .map(blankSpaceRepository::save)
            .map(blankSpaceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BlankSpaceDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all BlankSpaces");
        return blankSpaceRepository.findAll(pageable).map(blankSpaceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BlankSpaceDTO> findOne(Long id) {
        LOG.debug("Request to get BlankSpace : {}", id);
        return blankSpaceRepository.findById(id).map(blankSpaceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete BlankSpace : {}", id);
        blankSpaceRepository.deleteById(id);
    }
}
