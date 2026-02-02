package com.skillsdoor.skillverify.service.impl;

import com.skillsdoor.skillverify.domain.Exam;
import com.skillsdoor.skillverify.domain.Exercise;
import com.skillsdoor.skillverify.repository.ExamRepository;
import com.skillsdoor.skillverify.repository.ExerciseRepository;
import com.skillsdoor.skillverify.service.ExamService;
import com.skillsdoor.skillverify.service.dto.ExamDTO;
import com.skillsdoor.skillverify.service.dto.ExerciseDTO;
import com.skillsdoor.skillverify.service.mapper.ExamMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.skillsdoor.skillverify.domain.Exam}.
 */
@Service
@Transactional
public class ExamServiceImpl implements ExamService {

    private static final Logger LOG = LoggerFactory.getLogger(ExamServiceImpl.class);

    private final ExamRepository examRepository;

    private final ExamMapper examMapper;

    private final ExerciseRepository exerciseRepository;

    public ExamServiceImpl(ExamRepository examRepository, ExamMapper examMapper, ExerciseRepository exerciseRepository) {
        this.examRepository = examRepository;
        this.examMapper = examMapper;
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public ExamDTO save(ExamDTO examDTO) {
        LOG.debug("Request to save Exam : {}", examDTO);
        Exam exam = examMapper.toEntity(examDTO);
        exam = examRepository.save(exam);
        return examMapper.toDto(exam);
    }

    @Override
    public ExamDTO update(ExamDTO examDTO) {
        LOG.debug("Request to update Exam : {}", examDTO);
        Exam exam = examMapper.toEntity(examDTO);

        // Update exercise relationships
        if (examDTO.getExercises() != null) {
            for (ExerciseDTO exDto : examDTO.getExercises()) {
                Exercise exercise = exerciseRepository.findById(exDto.getId()).orElse(null);
                if (exercise != null) {
                    exercise.setExam(exam);
                    exerciseRepository.save(exercise);
                }
            }
        }

        exam = examRepository.save(exam);

        // Reload with exercises
        return examRepository
            .findByIdWithExercises(exam.getId())
            .map(savedExam -> {
                ExamDTO dto = examMapper.toDto(savedExam);
                if (!savedExam.getExercises().isEmpty()) {
                    dto.setExercises(
                        savedExam
                            .getExercises()
                            .stream()
                            .map(exercise -> {
                                ExerciseDTO exDto = new ExerciseDTO();
                                exDto.setId(exercise.getId());
                                exDto.setTitle(exercise.getTitle());
                                exDto.setType(exercise.getType());
                                exDto.setPoints(exercise.getPoints());
                                exDto.setOrderIndex(exercise.getOrderIndex());
                                return exDto;
                            })
                            .toList()
                    );
                }
                return dto;
            })
            .orElse(examMapper.toDto(exam));
    }

    @Override
    public Optional<ExamDTO> partialUpdate(ExamDTO examDTO) {
        LOG.debug("Request to partially update Exam : {}", examDTO);

        return examRepository
            .findById(examDTO.getId())
            .map(existingExam -> {
                examMapper.partialUpdate(existingExam, examDTO);

                return existingExam;
            })
            .map(examRepository::save)
            .map(examMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExamDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Exams");
        return examRepository.findAll(pageable).map(examMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ExamDTO> findOne(Long id) {
        LOG.debug("Request to get Exam : {}", id);
        return examRepository
            .findByIdWithExercises(id)
            .map(exam -> {
                ExamDTO dto = examMapper.toDto(exam);
                if (!exam.getExercises().isEmpty()) {
                    dto.setExercises(
                        exam
                            .getExercises()
                            .stream()
                            .map(exercise -> {
                                ExerciseDTO exDto = new ExerciseDTO();
                                exDto.setId(exercise.getId());
                                exDto.setTitle(exercise.getTitle());
                                exDto.setType(exercise.getType());
                                exDto.setPoints(exercise.getPoints());
                                exDto.setOrderIndex(exercise.getOrderIndex());
                                return exDto;
                            })
                            .toList()
                    );
                }
                return dto;
            });
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Exam : {}", id);
        examRepository.deleteById(id);
    }
}
