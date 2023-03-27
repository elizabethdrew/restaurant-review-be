package dev.drew.restaurantreview.mapper;

import dev.drew.restaurantreview.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.openapitools.model.UserInput;
import org.openapitools.model.User;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(UserEntity userEntity);
    UserEntity toUserEntity(UserInput userInput);
    UserInput toUserInput(UserEntity userEntity);
}
