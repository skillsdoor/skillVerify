package com.skillsdoor.skillverify.service.impl;

import com.skillsdoor.skillverify.domain.QcmOption;
import com.skillsdoor.skillverify.repository.QcmOptionRepository;
import com.skillsdoor.skillverify.service.QcmOptionService;
import com.skillsdoor.skillverify.service.dto.QcmOptionDTO;
import com.skillsdoor.skillverify.service.mapper.QcmOptionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.skillsdoor.skillverify.domain.QcmOption}.
 */
@Service
@Transactional
public class QcmOptionServiceImpl implements QcmOptionService {

    private static final Logger LOG = LoggerFactory.getLogger(QcmOptionServiceImpl.class);

    private final QcmOptionRepository qcmOptionRepository;

    private final QcmOptionMapper qcmOptionMapper;

    public QcmOptionServiceImpl(QcmOptionRepository qcmOptionRepository, QcmOptionMapper qcmOptionMapper) {
        this.qcmOptionRepository = qcmOptionRepository;
        this.qcmOptionMapper = qcmOptionMapper;
    }

    @Override
    public QcmOptionDTO save(QcmOptionDTO qcmOptionDTO) {
        LOG.debug("Request to save QcmOption : {}", qcmOptionDTO);
        QcmOption qcmOption = qcmOptionMapper.toEntity(qcmOptionDTO);
        qcmOption = qcmOptionRepository.save(qcmOption);
        return qcmOptionMapper.toDto(qcmOption);
    }

    @Override
    public QcmOptionDTO update(QcmOptionDTO qcmOptionDTO) {
        LOG.debug("Request to update QcmOption : {}", qcmOptionDTO);
        QcmOption qcmOption = qcmOptionMapper.toEntity(qcmOptionDTO);
        qcmOption = qcmOptionRepository.save(qcmOption);
        return qcmOptionMapper.toDto(qcmOption);
    }

    @Override
    public Optional<QcmOptionDTO> partialUpdate(QcmOptionDTO qcmOptionDTO) {
        LOG.debug("Request to partially update QcmOption : {}", qcmOptionDTO);

        return qcmOptionRepository
            .findById(qcmOptionDTO.getId())
            .map(existingQcmOption -> {
                qcmOptionMapper.partialUpdate(existingQcmOption, qcmOptionDTO);

                return existingQcmOption;
            })
            .map(qcmOptionRepository::save)
            .map(qcmOptionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QcmOptionDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all QcmOptions");
        return qcmOptionRepository.findAll(pageable).map(qcmOptionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<QcmOptionDTO> findOne(Long id) {
        LOG.debug("Request to get QcmOption : {}", id);
        return qcmOptionRepository.findById(id).map(qcmOptionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete QcmOption : {}", id);
        qcmOptionRepository.deleteById(id);
    }
}
