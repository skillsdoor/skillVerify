package com.skillsdoor.skillverify.service.impl;

import com.skillsdoor.skillverify.domain.Candidate;
import com.skillsdoor.skillverify.repository.CandidateRepository;
import com.skillsdoor.skillverify.service.CandidateService;
import com.skillsdoor.skillverify.service.dto.CandidateDTO;
import com.skillsdoor.skillverify.service.mapper.CandidateMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.skillsdoor.skillverify.domain.Candidate}.
 */
@Service
@Transactional
public class CandidateServiceImpl implements CandidateService {

    private static final Logger LOG = LoggerFactory.getLogger(CandidateServiceImpl.class);

    private final CandidateRepository candidateRepository;

    private final CandidateMapper candidateMapper;

    public CandidateServiceImpl(CandidateRepository candidateRepository, CandidateMapper candidateMapper) {
        this.candidateRepository = candidateRepository;
        this.candidateMapper = candidateMapper;
    }

    @Override
    public CandidateDTO save(CandidateDTO candidateDTO) {
        LOG.debug("Request to save Candidate : {}", candidateDTO);
        Candidate candidate = candidateMapper.toEntity(candidateDTO);
        candidate = candidateRepository.save(candidate);
        return candidateMapper.toDto(candidate);
    }

    @Override
    public CandidateDTO update(CandidateDTO candidateDTO) {
        LOG.debug("Request to update Candidate : {}", candidateDTO);
        Candidate candidate = candidateMapper.toEntity(candidateDTO);
        candidate = candidateRepository.save(candidate);
        return candidateMapper.toDto(candidate);
    }

    @Override
    public Optional<CandidateDTO> partialUpdate(CandidateDTO candidateDTO) {
        LOG.debug("Request to partially update Candidate : {}", candidateDTO);

        return candidateRepository
            .findById(candidateDTO.getId())
            .map(existingCandidate -> {
                candidateMapper.partialUpdate(existingCandidate, candidateDTO);

                return existingCandidate;
            })
            .map(candidateRepository::save)
            .map(candidateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CandidateDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Candidates");
        return candidateRepository.findAll(pageable).map(candidateMapper::toDto);
    }

    public Page<CandidateDTO> findAllWithEagerRelationships(Pageable pageable) {
        return candidateRepository.findAllWithEagerRelationships(pageable).map(candidateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CandidateDTO> findOne(Long id) {
        LOG.debug("Request to get Candidate : {}", id);
        return candidateRepository.findOneWithEagerRelationships(id).map(candidateMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Candidate : {}", id);
        candidateRepository.deleteById(id);
    }
}
