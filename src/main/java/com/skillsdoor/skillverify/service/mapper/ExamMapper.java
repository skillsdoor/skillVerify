package com.skillsdoor.skillverify.service.mapper;

import com.skillsdoor.skillverify.domain.Exam;
import com.skillsdoor.skillverify.domain.Recruiter;
import com.skillsdoor.skillverify.domain.TrainingProvider;
import com.skillsdoor.skillverify.service.dto.ExamDTO;
import com.skillsdoor.skillverify.service.dto.RecruiterDTO;
import com.skillsdoor.skillverify.service.dto.TrainingProviderDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Exam} and its DTO {@link ExamDTO}.
 */
@Mapper(componentModel = "spring")
public interface ExamMapper extends EntityMapper<ExamDTO, Exam> {
    @Mapping(target = "recruiter", source = "recruiter", qualifiedByName = "recruiterId")
    @Mapping(target = "trainingProvider", source = "trainingProvider", qualifiedByName = "trainingProviderId")
    ExamDTO toDto(Exam s);

    @Named("recruiterId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RecruiterDTO toDtoRecruiterId(Recruiter recruiter);

    @Named("trainingProviderId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TrainingProviderDTO toDtoTrainingProviderId(TrainingProvider trainingProvider);
}
