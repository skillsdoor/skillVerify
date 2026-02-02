package com.skillsdoor.skillverify.service.impl;

import com.skillsdoor.skillverify.domain.FillInTheBlank;
import com.skillsdoor.skillverify.repository.FillInTheBlankRepository;
import com.skillsdoor.skillverify.service.FillInTheBlankService;
import com.skillsdoor.skillverify.service.dto.FillInTheBlankDTO;
import com.skillsdoor.skillverify.service.mapper.FillInTheBlankMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.skillsdoor.skillverify.domain.FillInTheBlank}.
 */
@Service
@Transactional
public class FillInTheBlankServiceImpl implements FillInTheBlankService {

    private static final Logger LOG = LoggerFactory.getLogger(FillInTheBlankServiceImpl.class);

    private final FillInTheBlankRepository fillInTheBlankRepository;

    private final FillInTheBlankMapper fillInTheBlankMapper;

    public FillInTheBlankServiceImpl(FillInTheBlankRepository fillInTheBlankRepository, FillInTheBlankMapper fillInTheBlankMapper) {
        this.fillInTheBlankRepository = fillInTheBlankRepository;
        this.fillInTheBlankMapper = fillInTheBlankMapper;
    }

    @Override
    public FillInTheBlankDTO save(FillInTheBlankDTO fillInTheBlankDTO) {
        LOG.debug("Request to save FillInTheBlank : {}", fillInTheBlankDTO);
        FillInTheBlank fillInTheBlank = fillInTheBlankMapper.toEntity(fillInTheBlankDTO);
        fillInTheBlank = fillInTheBlankRepository.save(fillInTheBlank);
        return fillInTheBlankMapper.toDto(fillInTheBlank);
    }

    @Override
    public FillInTheBlankDTO update(FillInTheBlankDTO fillInTheBlankDTO) {
        LOG.debug("Request to update FillInTheBlank : {}", fillInTheBlankDTO);
        FillInTheBlank fillInTheBlank = fillInTheBlankMapper.toEntity(fillInTheBlankDTO);
        fillInTheBlank = fillInTheBlankRepository.save(fillInTheBlank);
        return fillInTheBlankMapper.toDto(fillInTheBlank);
    }

    @Override
    public Optional<FillInTheBlankDTO> partialUpdate(FillInTheBlankDTO fillInTheBlankDTO) {
        LOG.debug("Request to partially update FillInTheBlank : {}", fillInTheBlankDTO);

        return fillInTheBlankRepository
            .findById(fillInTheBlankDTO.getId())
            .map(existingFillInTheBlank -> {
                fillInTheBlankMapper.partialUpdate(existingFillInTheBlank, fillInTheBlankDTO);

                return existingFillInTheBlank;
            })
            .map(fillInTheBlankRepository::save)
            .map(fillInTheBlankMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FillInTheBlankDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all FillInTheBlanks");
        return fillInTheBlankRepository.findAll(pageable).map(fillInTheBlankMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FillInTheBlankDTO> findOne(Long id) {
        LOG.debug("Request to get FillInTheBlank : {}", id);
        return fillInTheBlankRepository.findById(id).map(fillInTheBlankMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete FillInTheBlank : {}", id);
        fillInTheBlankRepository.deleteById(id);
    }
}
