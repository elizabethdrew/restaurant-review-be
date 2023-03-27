package dev.drew.restaurantreview.mapper;

import dev.drew.restaurantreview.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.openapitools.model.UserInput;
import org.openapitools.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserEntity userEntity);
    UserEntity toUserEntity(UserInput userInput);
    UserInput toUserInput(UserEntity userEntity);
}
