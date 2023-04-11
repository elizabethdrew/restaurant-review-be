package dev.drew.restaurantreview.mapper;

import dev.drew.restaurantreview.entity.UserEntity;
import org.mapstruct.Mapper;
import org.openapitools.model.UserInput;
import org.openapitools.model.User;

// The UserMapper interface defines methods for mapping between UserEntity, User, and UserInput classes.
@Mapper(componentModel = "spring")
public interface UserMapper {

    // Method to convert a UserEntity object to a User object
    User toUser(UserEntity userEntity);

    // Method to convert a UserInput object to a UserEntity object
    UserEntity toUserEntity(UserInput userInput);

    // Method to convert a UserEntity object to a UserInput object
    UserInput toUserInput(UserEntity userEntity);
}