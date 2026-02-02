package com.skillsdoor.skillverify.service.impl;

import com.skillsdoor.skillverify.domain.BlankSpace;
import com.skillsdoor.skillverify.domain.Exercise;
import com.skillsdoor.skillverify.domain.ListItem;
import com.skillsdoor.skillverify.domain.QcmOption;
import com.skillsdoor.skillverify.repository.ExerciseRepository;
import com.skillsdoor.skillverify.service.ExerciseService;
import com.skillsdoor.skillverify.service.dto.BlankSpaceDTO;
import com.skillsdoor.skillverify.service.dto.ExerciseDTO;
import com.skillsdoor.skillverify.service.dto.ListItemDTO;
import com.skillsdoor.skillverify.service.dto.QcmOptionDTO;
import com.skillsdoor.skillverify.service.mapper.ExerciseMapper;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.skillsdoor.skillverify.domain.Exercise}.
 */
@Service
@Transactional
public class ExerciseServiceImpl implements ExerciseService {

    private static final Logger LOG = LoggerFactory.getLogger(ExerciseServiceImpl.class);

    private final ExerciseRepository exerciseRepository;

    private final ExerciseMapper exerciseMapper;

    public ExerciseServiceImpl(ExerciseRepository exerciseRepository, ExerciseMapper exerciseMapper) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseMapper = exerciseMapper;
    }

    @Override
    public ExerciseDTO save(ExerciseDTO exerciseDTO) {
        LOG.debug("Request to save Exercise : {}", exerciseDTO);
        Exercise exercise = exerciseMapper.toEntity(exerciseDTO);

        // Handle QCM options
        if (exerciseDTO.getQcmOptions() != null && !exerciseDTO.getQcmOptions().isEmpty()) {
            Set<QcmOption> qcmOptions = new HashSet<>();
            for (QcmOptionDTO optionDTO : exerciseDTO.getQcmOptions()) {
                QcmOption option = new QcmOption();
                option.setContent(optionDTO.getContent());
                option.setIsCorrect(optionDTO.getIsCorrect());
                option.setOrderIndex(optionDTO.getOrderIndex());
                option.setExercise(exercise);
                qcmOptions.add(option);
            }
            exercise.setQcmOptions(qcmOptions);
        }

        // Handle blank spaces
        if (exerciseDTO.getBlankSpaces() != null && !exerciseDTO.getBlankSpaces().isEmpty()) {
            Set<BlankSpace> blankSpaces = new HashSet<>();
            for (BlankSpaceDTO blankDTO : exerciseDTO.getBlankSpaces()) {
                BlankSpace blank = new BlankSpace();
                blank.setPosition(blankDTO.getPosition());
                blank.setCorrectAnswer(blankDTO.getCorrectAnswer());
                blank.setCaseSensitive(blankDTO.getCaseSensitive());
                blank.setOrderIndex(blankDTO.getOrderIndex());
                blank.setExercise(exercise);
                blankSpaces.add(blank);
            }
            exercise.setBlankSpaces(blankSpaces);
        }

        // Handle list items
        if (exerciseDTO.getListItems() != null && !exerciseDTO.getListItems().isEmpty()) {
            Set<ListItem> listItems = new HashSet<>();
            for (ListItemDTO itemDTO : exerciseDTO.getListItems()) {
                ListItem item = new ListItem();
                item.setContent(itemDTO.getContent());
                item.setMatchingContent(itemDTO.getMatchingContent());
                item.setIsLeftSide(itemDTO.getIsLeftSide());
                item.setOrderIndex(itemDTO.getOrderIndex());
                item.setExercise(exercise);
                listItems.add(item);
            }
            exercise.setListItems(listItems);
        }

        exercise = exerciseRepository.save(exercise);
        return exerciseMapper.toDto(exercise);
    }

    @Override
    public ExerciseDTO update(ExerciseDTO exerciseDTO) {
        LOG.debug("Request to update Exercise : {}", exerciseDTO);
        Exercise exercise = exerciseRepository.findById(exerciseDTO.getId()).orElseThrow(() -> new RuntimeException("Exercise not found"));

        // Update basic fields
        exercise.setType(exerciseDTO.getType());
        exercise.setTitle(exerciseDTO.getTitle());
        exercise.setInstruction(exerciseDTO.getInstruction());
        exercise.setContent(exerciseDTO.getContent());
        exercise.setExplanation(exerciseDTO.getExplanation());
        exercise.setPoints(exerciseDTO.getPoints());
        exercise.setOrderIndex(exerciseDTO.getOrderIndex());

        // Clear existing child entities
        exercise.getQcmOptions().clear();
        exercise.getBlankSpaces().clear();
        exercise.getListItems().clear();

        // Handle QCM options
        if (exerciseDTO.getQcmOptions() != null && !exerciseDTO.getQcmOptions().isEmpty()) {
            for (QcmOptionDTO optionDTO : exerciseDTO.getQcmOptions()) {
                QcmOption option = new QcmOption();
                if (optionDTO.getId() != null) {
                    option.setId(optionDTO.getId());
                }
                option.setContent(optionDTO.getContent());
                option.setIsCorrect(optionDTO.getIsCorrect());
                option.setOrderIndex(optionDTO.getOrderIndex());
                option.setExercise(exercise);
                exercise.getQcmOptions().add(option);
            }
        }

        // Handle blank spaces
        if (exerciseDTO.getBlankSpaces() != null && !exerciseDTO.getBlankSpaces().isEmpty()) {
            for (BlankSpaceDTO blankDTO : exerciseDTO.getBlankSpaces()) {
                BlankSpace blank = new BlankSpace();
                if (blankDTO.getId() != null) {
                    blank.setId(blankDTO.getId());
                }
                blank.setPosition(blankDTO.getPosition());
                blank.setCorrectAnswer(blankDTO.getCorrectAnswer());
                blank.setCaseSensitive(blankDTO.getCaseSensitive());
                blank.setOrderIndex(blankDTO.getOrderIndex());
                blank.setExercise(exercise);
                exercise.getBlankSpaces().add(blank);
            }
        }

        // Handle list items
        if (exerciseDTO.getListItems() != null && !exerciseDTO.getListItems().isEmpty()) {
            for (ListItemDTO itemDTO : exerciseDTO.getListItems()) {
                ListItem item = new ListItem();
                if (itemDTO.getId() != null) {
                    item.setId(itemDTO.getId());
                }
                item.setContent(itemDTO.getContent());
                item.setMatchingContent(itemDTO.getMatchingContent());
                item.setIsLeftSide(itemDTO.getIsLeftSide());
                item.setOrderIndex(itemDTO.getOrderIndex());
                item.setExercise(exercise);
                exercise.getListItems().add(item);
            }
        }

        exercise = exerciseRepository.save(exercise);
        return exerciseMapper.toDto(exercise);
    }

    @Override
    public Optional<ExerciseDTO> partialUpdate(ExerciseDTO exerciseDTO) {
        LOG.debug("Request to partially update Exercise : {}", exerciseDTO);

        return exerciseRepository
            .findById(exerciseDTO.getId())
            .map(existingExercise -> {
                exerciseMapper.partialUpdate(existingExercise, exerciseDTO);

                return existingExercise;
            })
            .map(exerciseRepository::save)
            .map(exerciseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExerciseDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Exercises");
        return exerciseRepository.findAll(pageable).map(exerciseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ExerciseDTO> findOne(Long id) {
        LOG.debug("Request to get Exercise : {}", id);
        return exerciseRepository
            .findById(id)
            .map(exercise -> {
                ExerciseDTO dto = exerciseMapper.toDto(exercise);

                // Map QCM options
                if (!exercise.getQcmOptions().isEmpty()) {
                    dto.setQcmOptions(
                        exercise
                            .getQcmOptions()
                            .stream()
                            .map(option -> {
                                QcmOptionDTO optionDTO = new QcmOptionDTO();
                                optionDTO.setId(option.getId());
                                optionDTO.setContent(option.getContent());
                                optionDTO.setIsCorrect(option.getIsCorrect());
                                optionDTO.setOrderIndex(option.getOrderIndex());
                                return optionDTO;
                            })
                            .toList()
                    );
                }

                // Map blank spaces
                if (!exercise.getBlankSpaces().isEmpty()) {
                    dto.setBlankSpaces(
                        exercise
                            .getBlankSpaces()
                            .stream()
                            .map(blank -> {
                                BlankSpaceDTO blankDTO = new BlankSpaceDTO();
                                blankDTO.setId(blank.getId());
                                blankDTO.setPosition(blank.getPosition());
                                blankDTO.setCorrectAnswer(blank.getCorrectAnswer());
                                blankDTO.setCaseSensitive(blank.getCaseSensitive());
                                blankDTO.setOrderIndex(blank.getOrderIndex());
                                return blankDTO;
                            })
                            .toList()
                    );
                }

                // Map list items
                if (!exercise.getListItems().isEmpty()) {
                    dto.setListItems(
                        exercise
                            .getListItems()
                            .stream()
                            .map(item -> {
                                ListItemDTO itemDTO = new ListItemDTO();
                                itemDTO.setId(item.getId());
                                itemDTO.setContent(item.getContent());
                                itemDTO.setMatchingContent(item.getMatchingContent());
                                itemDTO.setIsLeftSide(item.getIsLeftSide());
                                itemDTO.setOrderIndex(item.getOrderIndex());
                                return itemDTO;
                            })
                            .toList()
                    );
                }

                return dto;
            });
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Exercise : {}", id);
        exerciseRepository.deleteById(id);
    }
}
