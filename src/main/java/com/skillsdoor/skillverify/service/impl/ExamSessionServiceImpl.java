package com.skillsdoor.skillverify.service.impl;

import com.skillsdoor.skillverify.domain.ExamSession;
import com.skillsdoor.skillverify.repository.ExamSessionRepository;
import com.skillsdoor.skillverify.service.ExamSessionService;
import com.skillsdoor.skillverify.service.dto.ExamSessionDTO;
import com.skillsdoor.skillverify.service.mapper.ExamSessionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.skillsdoor.skillverify.domain.ExamSession}.
 */
@Service
@Transactional
public class ExamSessionServiceImpl implements ExamSessionService {

    private static final Logger LOG = LoggerFactory.getLogger(ExamSessionServiceImpl.class);

    private final ExamSessionRepository examSessionRepository;

    private final ExamSessionMapper examSessionMapper;

    public ExamSessionServiceImpl(ExamSessionRepository examSessionRepository, ExamSessionMapper examSessionMapper) {
        this.examSessionRepository = examSessionRepository;
        this.examSessionMapper = examSessionMapper;
    }

    @Override
    public ExamSessionDTO save(ExamSessionDTO examSessionDTO) {
        LOG.debug("Request to save ExamSession : {}", examSessionDTO);
        ExamSession examSession = examSessionMapper.toEntity(examSessionDTO);
        examSession = examSessionRepository.save(examSession);
        return examSessionMapper.toDto(examSession);
    }

    @Override
    public ExamSessionDTO update(ExamSessionDTO examSessionDTO) {
        LOG.debug("Request to update ExamSession : {}", examSessionDTO);
        ExamSession examSession = examSessionMapper.toEntity(examSessionDTO);
        examSession = examSessionRepository.save(examSession);
        return examSessionMapper.toDto(examSession);
    }

    @Override
    public Optional<ExamSessionDTO> partialUpdate(ExamSessionDTO examSessionDTO) {
        LOG.debug("Request to partially update ExamSession : {}", examSessionDTO);

        return examSessionRepository
            .findById(examSessionDTO.getId())
            .map(existingExamSession -> {
                examSessionMapper.partialUpdate(existingExamSession, examSessionDTO);

                return existingExamSession;
            })
            .map(examSessionRepository::save)
            .map(examSessionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExamSessionDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all ExamSessions");
        return examSessionRepository.findAll(pageable).map(examSessionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ExamSessionDTO> findOne(Long id) {
        LOG.debug("Request to get ExamSession : {}", id);
        return examSessionRepository.findById(id).map(examSessionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete ExamSession : {}", id);
        examSessionRepository.deleteById(id);
    }
}
