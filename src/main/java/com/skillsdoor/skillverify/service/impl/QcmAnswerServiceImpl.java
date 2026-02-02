package com.skillsdoor.skillverify.service.impl;

import com.skillsdoor.skillverify.domain.QcmAnswer;
import com.skillsdoor.skillverify.repository.QcmAnswerRepository;
import com.skillsdoor.skillverify.service.QcmAnswerService;
import com.skillsdoor.skillverify.service.dto.QcmAnswerDTO;
import com.skillsdoor.skillverify.service.mapper.QcmAnswerMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.skillsdoor.skillverify.domain.QcmAnswer}.
 */
@Service
@Transactional
public class QcmAnswerServiceImpl implements QcmAnswerService {

    private static final Logger LOG = LoggerFactory.getLogger(QcmAnswerServiceImpl.class);

    private final QcmAnswerRepository qcmAnswerRepository;

    private final QcmAnswerMapper qcmAnswerMapper;

    public QcmAnswerServiceImpl(QcmAnswerRepository qcmAnswerRepository, QcmAnswerMapper qcmAnswerMapper) {
        this.qcmAnswerRepository = qcmAnswerRepository;
        this.qcmAnswerMapper = qcmAnswerMapper;
    }

    @Override
    public QcmAnswerDTO save(QcmAnswerDTO qcmAnswerDTO) {
        LOG.debug("Request to save QcmAnswer : {}", qcmAnswerDTO);
        QcmAnswer qcmAnswer = qcmAnswerMapper.toEntity(qcmAnswerDTO);
        qcmAnswer = qcmAnswerRepository.save(qcmAnswer);
        return qcmAnswerMapper.toDto(qcmAnswer);
    }

    @Override
    public QcmAnswerDTO update(QcmAnswerDTO qcmAnswerDTO) {
        LOG.debug("Request to update QcmAnswer : {}", qcmAnswerDTO);
        QcmAnswer qcmAnswer = qcmAnswerMapper.toEntity(qcmAnswerDTO);
        qcmAnswer = qcmAnswerRepository.save(qcmAnswer);
        return qcmAnswerMapper.toDto(qcmAnswer);
    }

    @Override
    public Optional<QcmAnswerDTO> partialUpdate(QcmAnswerDTO qcmAnswerDTO) {
        LOG.debug("Request to partially update QcmAnswer : {}", qcmAnswerDTO);

        return qcmAnswerRepository
            .findById(qcmAnswerDTO.getId())
            .map(existingQcmAnswer -> {
                qcmAnswerMapper.partialUpdate(existingQcmAnswer, qcmAnswerDTO);

                return existingQcmAnswer;
            })
            .map(qcmAnswerRepository::save)
            .map(qcmAnswerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QcmAnswerDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all QcmAnswers");
        return qcmAnswerRepository.findAll(pageable).map(qcmAnswerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<QcmAnswerDTO> findOne(Long id) {
        LOG.debug("Request to get QcmAnswer : {}", id);
        return qcmAnswerRepository.findById(id).map(qcmAnswerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete QcmAnswer : {}", id);
        qcmAnswerRepository.deleteById(id);
    }
}
