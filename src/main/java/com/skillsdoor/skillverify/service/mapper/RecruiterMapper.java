package com.skillsdoor.skillverify.service.mapper;

import com.skillsdoor.skillverify.domain.Recruiter;
import com.skillsdoor.skillverify.domain.User;
import com.skillsdoor.skillverify.service.dto.RecruiterDTO;
import com.skillsdoor.skillverify.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Recruiter} and its DTO {@link RecruiterDTO}.
 */
@Mapper(componentModel = "spring")
public interface RecruiterMapper extends EntityMapper<RecruiterDTO, Recruiter> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    RecruiterDTO toDto(Recruiter s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
