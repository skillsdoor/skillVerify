package com.skillsdoor.skillverify.service.impl;

import com.skillsdoor.skillverify.domain.Recruiter;
import com.skillsdoor.skillverify.repository.RecruiterRepository;
import com.skillsdoor.skillverify.service.RecruiterService;
import com.skillsdoor.skillverify.service.dto.RecruiterDTO;
import com.skillsdoor.skillverify.service.mapper.RecruiterMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.skillsdoor.skillverify.domain.Recruiter}.
 */
@Service
@Transactional
public class RecruiterServiceImpl implements RecruiterService {

    private static final Logger LOG = LoggerFactory.getLogger(RecruiterServiceImpl.class);

    private final RecruiterRepository recruiterRepository;

    private final RecruiterMapper recruiterMapper;

    public RecruiterServiceImpl(RecruiterRepository recruiterRepository, RecruiterMapper recruiterMapper) {
        this.recruiterRepository = recruiterRepository;
        this.recruiterMapper = recruiterMapper;
    }

    @Override
    public RecruiterDTO save(RecruiterDTO recruiterDTO) {
        LOG.debug("Request to save Recruiter : {}", recruiterDTO);
        Recruiter recruiter = recruiterMapper.toEntity(recruiterDTO);
        recruiter = recruiterRepository.save(recruiter);
        return recruiterMapper.toDto(recruiter);
    }

    @Override
    public RecruiterDTO update(RecruiterDTO recruiterDTO) {
        LOG.debug("Request to update Recruiter : {}", recruiterDTO);
        Recruiter recruiter = recruiterMapper.toEntity(recruiterDTO);
        recruiter = recruiterRepository.save(recruiter);
        return recruiterMapper.toDto(recruiter);
    }

    @Override
    public Optional<RecruiterDTO> partialUpdate(RecruiterDTO recruiterDTO) {
        LOG.debug("Request to partially update Recruiter : {}", recruiterDTO);

        return recruiterRepository
            .findById(recruiterDTO.getId())
            .map(existingRecruiter -> {
                recruiterMapper.partialUpdate(existingRecruiter, recruiterDTO);

                return existingRecruiter;
            })
            .map(recruiterRepository::save)
            .map(recruiterMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RecruiterDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Recruiters");
        return recruiterRepository.findAll(pageable).map(recruiterMapper::toDto);
    }

    public Page<RecruiterDTO> findAllWithEagerRelationships(Pageable pageable) {
        return recruiterRepository.findAllWithEagerRelationships(pageable).map(recruiterMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RecruiterDTO> findOne(Long id) {
        LOG.debug("Request to get Recruiter : {}", id);
        return recruiterRepository.findOneWithEagerRelationships(id).map(recruiterMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Recruiter : {}", id);
        recruiterRepository.deleteById(id);
    }
}
