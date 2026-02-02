package com.skillsdoor.skillverify.service.mapper;

import com.skillsdoor.skillverify.domain.Candidate;
import com.skillsdoor.skillverify.domain.User;
import com.skillsdoor.skillverify.service.dto.CandidateDTO;
import com.skillsdoor.skillverify.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Candidate} and its DTO {@link CandidateDTO}.
 */
@Mapper(componentModel = "spring")
public interface CandidateMapper extends EntityMapper<CandidateDTO, Candidate> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    CandidateDTO toDto(Candidate s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
