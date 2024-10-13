package com.remreren.justodoit.domain.user;

import com.remreren.justodoit.domain.user.models.User;
import com.remreren.justodoit.domain.user.models.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    UserEntity toEntity(User user);
    User toModel(UserEntity entity);
}
