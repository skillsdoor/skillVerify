package com.skillsdoor.skillverify.service.mapper;

import com.skillsdoor.skillverify.domain.TrainingProvider;
import com.skillsdoor.skillverify.domain.User;
import com.skillsdoor.skillverify.service.dto.TrainingProviderDTO;
import com.skillsdoor.skillverify.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TrainingProvider} and its DTO {@link TrainingProviderDTO}.
 */
@Mapper(componentModel = "spring")
public interface TrainingProviderMapper extends EntityMapper<TrainingProviderDTO, TrainingProvider> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    TrainingProviderDTO toDto(TrainingProvider s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
