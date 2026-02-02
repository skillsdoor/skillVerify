package com.skillsdoor.skillverify.service.impl;

import com.skillsdoor.skillverify.domain.Media;
import com.skillsdoor.skillverify.repository.MediaRepository;
import com.skillsdoor.skillverify.service.MediaService;
import com.skillsdoor.skillverify.service.dto.MediaDTO;
import com.skillsdoor.skillverify.service.mapper.MediaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.skillsdoor.skillverify.domain.Media}.
 */
@Service
@Transactional
public class MediaServiceImpl implements MediaService {

    private static final Logger LOG = LoggerFactory.getLogger(MediaServiceImpl.class);

    private final MediaRepository mediaRepository;

    private final MediaMapper mediaMapper;

    public MediaServiceImpl(MediaRepository mediaRepository, MediaMapper mediaMapper) {
        this.mediaRepository = mediaRepository;
        this.mediaMapper = mediaMapper;
    }

    @Override
    public MediaDTO save(MediaDTO mediaDTO) {
        LOG.debug("Request to save Media : {}", mediaDTO);
        Media media = mediaMapper.toEntity(mediaDTO);
        media = mediaRepository.save(media);
        return mediaMapper.toDto(media);
    }

    @Override
    public MediaDTO update(MediaDTO mediaDTO) {
        LOG.debug("Request to update Media : {}", mediaDTO);
        Media media = mediaMapper.toEntity(mediaDTO);
        media = mediaRepository.save(media);
        return mediaMapper.toDto(media);
    }

    @Override
    public Optional<MediaDTO> partialUpdate(MediaDTO mediaDTO) {
        LOG.debug("Request to partially update Media : {}", mediaDTO);

        return mediaRepository
            .findById(mediaDTO.getId())
            .map(existingMedia -> {
                mediaMapper.partialUpdate(existingMedia, mediaDTO);

                return existingMedia;
            })
            .map(mediaRepository::save)
            .map(mediaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MediaDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Media");
        return mediaRepository.findAll(pageable).map(mediaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MediaDTO> findOne(Long id) {
        LOG.debug("Request to get Media : {}", id);
        return mediaRepository.findById(id).map(mediaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Media : {}", id);
        mediaRepository.deleteById(id);
    }
}
