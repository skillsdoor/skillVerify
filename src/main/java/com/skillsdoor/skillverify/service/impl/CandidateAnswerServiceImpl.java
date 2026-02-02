package com.skillsdoor.skillverify.service.impl;

import com.skillsdoor.skillverify.domain.CandidateAnswer;
import com.skillsdoor.skillverify.repository.CandidateAnswerRepository;
import com.skillsdoor.skillverify.service.CandidateAnswerService;
import com.skillsdoor.skillverify.service.dto.CandidateAnswerDTO;
import com.skillsdoor.skillverify.service.mapper.CandidateAnswerMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.skillsdoor.skillverify.domain.CandidateAnswer}.
 */
@Service
@Transactional
public class CandidateAnswerServiceImpl implements CandidateAnswerService {

    private static final Logger LOG = LoggerFactory.getLogger(CandidateAnswerServiceImpl.class);

    private final CandidateAnswerRepository candidateAnswerRepository;

    private final CandidateAnswerMapper candidateAnswerMapper;

    public CandidateAnswerServiceImpl(CandidateAnswerRepository candidateAnswerRepository, CandidateAnswerMapper candidateAnswerMapper) {
        this.candidateAnswerRepository = candidateAnswerRepository;
        this.candidateAnswerMapper = candidateAnswerMapper;
    }

    @Override
    public CandidateAnswerDTO save(CandidateAnswerDTO candidateAnswerDTO) {
        LOG.debug("Request to save CandidateAnswer : {}", candidateAnswerDTO);
        CandidateAnswer candidateAnswer = candidateAnswerMapper.toEntity(candidateAnswerDTO);
        candidateAnswer = candidateAnswerRepository.save(candidateAnswer);
        return candidateAnswerMapper.toDto(candidateAnswer);
    }

    @Override
    public CandidateAnswerDTO update(CandidateAnswerDTO candidateAnswerDTO) {
        LOG.debug("Request to update CandidateAnswer : {}", candidateAnswerDTO);
        CandidateAnswer candidateAnswer = candidateAnswerMapper.toEntity(candidateAnswerDTO);
        candidateAnswer = candidateAnswerRepository.save(candidateAnswer);
        return candidateAnswerMapper.toDto(candidateAnswer);
    }

    @Override
    public Optional<CandidateAnswerDTO> partialUpdate(CandidateAnswerDTO candidateAnswerDTO) {
        LOG.debug("Request to partially update CandidateAnswer : {}", candidateAnswerDTO);

        return candidateAnswerRepository
            .findById(candidateAnswerDTO.getId())
            .map(existingCandidateAnswer -> {
                candidateAnswerMapper.partialUpdate(existingCandidateAnswer, candidateAnswerDTO);

                return existingCandidateAnswer;
            })
            .map(candidateAnswerRepository::save)
            .map(candidateAnswerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CandidateAnswerDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all CandidateAnswers");
        return candidateAnswerRepository.findAll(pageable).map(candidateAnswerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CandidateAnswerDTO> findOne(Long id) {
        LOG.debug("Request to get CandidateAnswer : {}", id);
        return candidateAnswerRepository.findById(id).map(candidateAnswerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete CandidateAnswer : {}", id);
        candidateAnswerRepository.deleteById(id);
    }
}
