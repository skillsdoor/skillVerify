package com.skillsdoor.skillverify.service.mapper;

import com.skillsdoor.skillverify.domain.CandidateAnswer;
import com.skillsdoor.skillverify.domain.ExamSession;
import com.skillsdoor.skillverify.domain.Exercise;
import com.skillsdoor.skillverify.domain.Question;
import com.skillsdoor.skillverify.service.dto.CandidateAnswerDTO;
import com.skillsdoor.skillverify.service.dto.ExamSessionDTO;
import com.skillsdoor.skillverify.service.dto.ExerciseDTO;
import com.skillsdoor.skillverify.service.dto.QuestionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CandidateAnswer} and its DTO {@link CandidateAnswerDTO}.
 */
@Mapper(componentModel = "spring")
public interface CandidateAnswerMapper extends EntityMapper<CandidateAnswerDTO, CandidateAnswer> {
    @Mapping(target = "examSession", source = "examSession", qualifiedByName = "examSessionId")
    @Mapping(target = "exercise", source = "exercise", qualifiedByName = "exerciseId")
    @Mapping(target = "question", source = "question", qualifiedByName = "questionId")
    CandidateAnswerDTO toDto(CandidateAnswer s);

    @Named("examSessionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ExamSessionDTO toDtoExamSessionId(ExamSession examSession);

    @Named("exerciseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ExerciseDTO toDtoExerciseId(Exercise exercise);

    @Named("questionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    QuestionDTO toDtoQuestionId(Question question);
}
