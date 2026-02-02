package com.skillsdoor.skillverify.service.impl;

import com.skillsdoor.skillverify.domain.BlankAnswer;
import com.skillsdoor.skillverify.repository.BlankAnswerRepository;
import com.skillsdoor.skillverify.service.BlankAnswerService;
import com.skillsdoor.skillverify.service.dto.BlankAnswerDTO;
import com.skillsdoor.skillverify.service.mapper.BlankAnswerMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.skillsdoor.skillverify.domain.BlankAnswer}.
 */
@Service
@Transactional
public class BlankAnswerServiceImpl implements BlankAnswerService {

    private static final Logger LOG = LoggerFactory.getLogger(BlankAnswerServiceImpl.class);

    private final BlankAnswerRepository blankAnswerRepository;

    private final BlankAnswerMapper blankAnswerMapper;

    public BlankAnswerServiceImpl(BlankAnswerRepository blankAnswerRepository, BlankAnswerMapper blankAnswerMapper) {
        this.blankAnswerRepository = blankAnswerRepository;
        this.blankAnswerMapper = blankAnswerMapper;
    }

    @Override
    public BlankAnswerDTO save(BlankAnswerDTO blankAnswerDTO) {
        LOG.debug("Request to save BlankAnswer : {}", blankAnswerDTO);
        BlankAnswer blankAnswer = blankAnswerMapper.toEntity(blankAnswerDTO);
        blankAnswer = blankAnswerRepository.save(blankAnswer);
        return blankAnswerMapper.toDto(blankAnswer);
    }

    @Override
    public BlankAnswerDTO update(BlankAnswerDTO blankAnswerDTO) {
        LOG.debug("Request to update BlankAnswer : {}", blankAnswerDTO);
        BlankAnswer blankAnswer = blankAnswerMapper.toEntity(blankAnswerDTO);
        blankAnswer = blankAnswerRepository.save(blankAnswer);
        return blankAnswerMapper.toDto(blankAnswer);
    }

    @Override
    public Optional<BlankAnswerDTO> partialUpdate(BlankAnswerDTO blankAnswerDTO) {
        LOG.debug("Request to partially update BlankAnswer : {}", blankAnswerDTO);

        return blankAnswerRepository
            .findById(blankAnswerDTO.getId())
            .map(existingBlankAnswer -> {
                blankAnswerMapper.partialUpdate(existingBlankAnswer, blankAnswerDTO);

                return existingBlankAnswer;
            })
            .map(blankAnswerRepository::save)
            .map(blankAnswerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BlankAnswerDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all BlankAnswers");
        return blankAnswerRepository.findAll(pageable).map(blankAnswerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BlankAnswerDTO> findOne(Long id) {
        LOG.debug("Request to get BlankAnswer : {}", id);
        return blankAnswerRepository.findById(id).map(blankAnswerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete BlankAnswer : {}", id);
        blankAnswerRepository.deleteById(id);
    }
}
