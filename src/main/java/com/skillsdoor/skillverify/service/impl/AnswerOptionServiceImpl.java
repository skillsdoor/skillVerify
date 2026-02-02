package com.skillsdoor.skillverify.service.impl;

import com.skillsdoor.skillverify.domain.AnswerOption;
import com.skillsdoor.skillverify.repository.AnswerOptionRepository;
import com.skillsdoor.skillverify.service.AnswerOptionService;
import com.skillsdoor.skillverify.service.dto.AnswerOptionDTO;
import com.skillsdoor.skillverify.service.mapper.AnswerOptionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.skillsdoor.skillverify.domain.AnswerOption}.
 */
@Service
@Transactional
public class AnswerOptionServiceImpl implements AnswerOptionService {

    private static final Logger LOG = LoggerFactory.getLogger(AnswerOptionServiceImpl.class);

    private final AnswerOptionRepository answerOptionRepository;

    private final AnswerOptionMapper answerOptionMapper;

    public AnswerOptionServiceImpl(AnswerOptionRepository answerOptionRepository, AnswerOptionMapper answerOptionMapper) {
        this.answerOptionRepository = answerOptionRepository;
        this.answerOptionMapper = answerOptionMapper;
    }

    @Override
    public AnswerOptionDTO save(AnswerOptionDTO answerOptionDTO) {
        LOG.debug("Request to save AnswerOption : {}", answerOptionDTO);
        AnswerOption answerOption = answerOptionMapper.toEntity(answerOptionDTO);
        answerOption = answerOptionRepository.save(answerOption);
        return answerOptionMapper.toDto(answerOption);
    }

    @Override
    public AnswerOptionDTO update(AnswerOptionDTO answerOptionDTO) {
        LOG.debug("Request to update AnswerOption : {}", answerOptionDTO);
        AnswerOption answerOption = answerOptionMapper.toEntity(answerOptionDTO);
        answerOption = answerOptionRepository.save(answerOption);
        return answerOptionMapper.toDto(answerOption);
    }

    @Override
    public Optional<AnswerOptionDTO> partialUpdate(AnswerOptionDTO answerOptionDTO) {
        LOG.debug("Request to partially update AnswerOption : {}", answerOptionDTO);

        return answerOptionRepository
            .findById(answerOptionDTO.getId())
            .map(existingAnswerOption -> {
                answerOptionMapper.partialUpdate(existingAnswerOption, answerOptionDTO);

                return existingAnswerOption;
            })
            .map(answerOptionRepository::save)
            .map(answerOptionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnswerOptionDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all AnswerOptions");
        return answerOptionRepository.findAll(pageable).map(answerOptionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AnswerOptionDTO> findOne(Long id) {
        LOG.debug("Request to get AnswerOption : {}", id);
        return answerOptionRepository.findById(id).map(answerOptionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete AnswerOption : {}", id);
        answerOptionRepository.deleteById(id);
    }
}
