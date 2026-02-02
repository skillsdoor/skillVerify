package com.skillsdoor.skillverify.service.impl;

import com.skillsdoor.skillverify.domain.BindListAnswer;
import com.skillsdoor.skillverify.repository.BindListAnswerRepository;
import com.skillsdoor.skillverify.service.BindListAnswerService;
import com.skillsdoor.skillverify.service.dto.BindListAnswerDTO;
import com.skillsdoor.skillverify.service.mapper.BindListAnswerMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.skillsdoor.skillverify.domain.BindListAnswer}.
 */
@Service
@Transactional
public class BindListAnswerServiceImpl implements BindListAnswerService {

    private static final Logger LOG = LoggerFactory.getLogger(BindListAnswerServiceImpl.class);

    private final BindListAnswerRepository bindListAnswerRepository;

    private final BindListAnswerMapper bindListAnswerMapper;

    public BindListAnswerServiceImpl(BindListAnswerRepository bindListAnswerRepository, BindListAnswerMapper bindListAnswerMapper) {
        this.bindListAnswerRepository = bindListAnswerRepository;
        this.bindListAnswerMapper = bindListAnswerMapper;
    }

    @Override
    public BindListAnswerDTO save(BindListAnswerDTO bindListAnswerDTO) {
        LOG.debug("Request to save BindListAnswer : {}", bindListAnswerDTO);
        BindListAnswer bindListAnswer = bindListAnswerMapper.toEntity(bindListAnswerDTO);
        bindListAnswer = bindListAnswerRepository.save(bindListAnswer);
        return bindListAnswerMapper.toDto(bindListAnswer);
    }

    @Override
    public BindListAnswerDTO update(BindListAnswerDTO bindListAnswerDTO) {
        LOG.debug("Request to update BindListAnswer : {}", bindListAnswerDTO);
        BindListAnswer bindListAnswer = bindListAnswerMapper.toEntity(bindListAnswerDTO);
        bindListAnswer = bindListAnswerRepository.save(bindListAnswer);
        return bindListAnswerMapper.toDto(bindListAnswer);
    }

    @Override
    public Optional<BindListAnswerDTO> partialUpdate(BindListAnswerDTO bindListAnswerDTO) {
        LOG.debug("Request to partially update BindListAnswer : {}", bindListAnswerDTO);

        return bindListAnswerRepository
            .findById(bindListAnswerDTO.getId())
            .map(existingBindListAnswer -> {
                bindListAnswerMapper.partialUpdate(existingBindListAnswer, bindListAnswerDTO);

                return existingBindListAnswer;
            })
            .map(bindListAnswerRepository::save)
            .map(bindListAnswerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BindListAnswerDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all BindListAnswers");
        return bindListAnswerRepository.findAll(pageable).map(bindListAnswerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BindListAnswerDTO> findOne(Long id) {
        LOG.debug("Request to get BindListAnswer : {}", id);
        return bindListAnswerRepository.findById(id).map(bindListAnswerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete BindListAnswer : {}", id);
        bindListAnswerRepository.deleteById(id);
    }
}
