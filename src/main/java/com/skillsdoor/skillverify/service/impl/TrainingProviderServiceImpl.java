package com.skillsdoor.skillverify.service.impl;

import com.skillsdoor.skillverify.domain.TrainingProvider;
import com.skillsdoor.skillverify.repository.TrainingProviderRepository;
import com.skillsdoor.skillverify.service.TrainingProviderService;
import com.skillsdoor.skillverify.service.dto.TrainingProviderDTO;
import com.skillsdoor.skillverify.service.mapper.TrainingProviderMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.skillsdoor.skillverify.domain.TrainingProvider}.
 */
@Service
@Transactional
public class TrainingProviderServiceImpl implements TrainingProviderService {

    private static final Logger LOG = LoggerFactory.getLogger(TrainingProviderServiceImpl.class);

    private final TrainingProviderRepository trainingProviderRepository;

    private final TrainingProviderMapper trainingProviderMapper;

    public TrainingProviderServiceImpl(
        TrainingProviderRepository trainingProviderRepository,
        TrainingProviderMapper trainingProviderMapper
    ) {
        this.trainingProviderRepository = trainingProviderRepository;
        this.trainingProviderMapper = trainingProviderMapper;
    }

    @Override
    public TrainingProviderDTO save(TrainingProviderDTO trainingProviderDTO) {
        LOG.debug("Request to save TrainingProvider : {}", trainingProviderDTO);
        TrainingProvider trainingProvider = trainingProviderMapper.toEntity(trainingProviderDTO);
        trainingProvider = trainingProviderRepository.save(trainingProvider);
        return trainingProviderMapper.toDto(trainingProvider);
    }

    @Override
    public TrainingProviderDTO update(TrainingProviderDTO trainingProviderDTO) {
        LOG.debug("Request to update TrainingProvider : {}", trainingProviderDTO);
        TrainingProvider trainingProvider = trainingProviderMapper.toEntity(trainingProviderDTO);
        trainingProvider = trainingProviderRepository.save(trainingProvider);
        return trainingProviderMapper.toDto(trainingProvider);
    }

    @Override
    public Optional<TrainingProviderDTO> partialUpdate(TrainingProviderDTO trainingProviderDTO) {
        LOG.debug("Request to partially update TrainingProvider : {}", trainingProviderDTO);

        return trainingProviderRepository
            .findById(trainingProviderDTO.getId())
            .map(existingTrainingProvider -> {
                trainingProviderMapper.partialUpdate(existingTrainingProvider, trainingProviderDTO);

                return existingTrainingProvider;
            })
            .map(trainingProviderRepository::save)
            .map(trainingProviderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TrainingProviderDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all TrainingProviders");
        return trainingProviderRepository.findAll(pageable).map(trainingProviderMapper::toDto);
    }

    public Page<TrainingProviderDTO> findAllWithEagerRelationships(Pageable pageable) {
        return trainingProviderRepository.findAllWithEagerRelationships(pageable).map(trainingProviderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TrainingProviderDTO> findOne(Long id) {
        LOG.debug("Request to get TrainingProvider : {}", id);
        return trainingProviderRepository.findOneWithEagerRelationships(id).map(trainingProviderMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete TrainingProvider : {}", id);
        trainingProviderRepository.deleteById(id);
    }
}
