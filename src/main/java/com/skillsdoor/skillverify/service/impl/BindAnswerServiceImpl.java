package com.skillsdoor.skillverify.service.impl;

import com.skillsdoor.skillverify.domain.BindAnswer;
import com.skillsdoor.skillverify.repository.BindAnswerRepository;
import com.skillsdoor.skillverify.service.BindAnswerService;
import com.skillsdoor.skillverify.service.dto.BindAnswerDTO;
import com.skillsdoor.skillverify.service.mapper.BindAnswerMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.skillsdoor.skillverify.domain.BindAnswer}.
 */
@Service
@Transactional
public class BindAnswerServiceImpl implements BindAnswerService {

    private static final Logger LOG = LoggerFactory.getLogger(BindAnswerServiceImpl.class);

    private final BindAnswerRepository bindAnswerRepository;

    private final BindAnswerMapper bindAnswerMapper;

    public BindAnswerServiceImpl(BindAnswerRepository bindAnswerRepository, BindAnswerMapper bindAnswerMapper) {
        this.bindAnswerRepository = bindAnswerRepository;
        this.bindAnswerMapper = bindAnswerMapper;
    }

    @Override
    public BindAnswerDTO save(BindAnswerDTO bindAnswerDTO) {
        LOG.debug("Request to save BindAnswer : {}", bindAnswerDTO);
        BindAnswer bindAnswer = bindAnswerMapper.toEntity(bindAnswerDTO);
        bindAnswer = bindAnswerRepository.save(bindAnswer);
        return bindAnswerMapper.toDto(bindAnswer);
    }

    @Override
    public BindAnswerDTO update(BindAnswerDTO bindAnswerDTO) {
        LOG.debug("Request to update BindAnswer : {}", bindAnswerDTO);
        BindAnswer bindAnswer = bindAnswerMapper.toEntity(bindAnswerDTO);
        bindAnswer = bindAnswerRepository.save(bindAnswer);
        return bindAnswerMapper.toDto(bindAnswer);
    }

    @Override
    public Optional<BindAnswerDTO> partialUpdate(BindAnswerDTO bindAnswerDTO) {
        LOG.debug("Request to partially update BindAnswer : {}", bindAnswerDTO);

        return bindAnswerRepository
            .findById(bindAnswerDTO.getId())
            .map(existingBindAnswer -> {
                bindAnswerMapper.partialUpdate(existingBindAnswer, bindAnswerDTO);

                return existingBindAnswer;
            })
            .map(bindAnswerRepository::save)
            .map(bindAnswerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BindAnswerDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all BindAnswers");
        return bindAnswerRepository.findAll(pageable).map(bindAnswerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BindAnswerDTO> findOne(Long id) {
        LOG.debug("Request to get BindAnswer : {}", id);
        return bindAnswerRepository.findById(id).map(bindAnswerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete BindAnswer : {}", id);
        bindAnswerRepository.deleteById(id);
    }
}
