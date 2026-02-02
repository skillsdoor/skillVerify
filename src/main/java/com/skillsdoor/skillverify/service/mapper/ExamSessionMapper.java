package com.skillsdoor.skillverify.service.mapper;

import com.skillsdoor.skillverify.domain.Candidate;
import com.skillsdoor.skillverify.domain.Exam;
import com.skillsdoor.skillverify.domain.ExamSession;
import com.skillsdoor.skillverify.service.dto.CandidateDTO;
import com.skillsdoor.skillverify.service.dto.ExamDTO;
import com.skillsdoor.skillverify.service.dto.ExamSessionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ExamSession} and its DTO {@link ExamSessionDTO}.
 */
@Mapper(componentModel = "spring")
public interface ExamSessionMapper extends EntityMapper<ExamSessionDTO, ExamSession> {
    @Mapping(target = "candidate", source = "candidate", qualifiedByName = "candidateId")
    @Mapping(target = "exam", source = "exam", qualifiedByName = "examId")
    ExamSessionDTO toDto(ExamSession s);

    @Named("candidateId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CandidateDTO toDtoCandidateId(Candidate candidate);

    @Named("examId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ExamDTO toDtoExamId(Exam exam);
}
