package dev.drew.restaurantreview.mapper;

import dev.drew.restaurantreview.entity.UserEntity;
import javax.annotation.processing.Generated;
import org.openapitools.model.User;
import org.openapitools.model.UserInput;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-27T12:40:35+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.6 (Amazon.com Inc.)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        User user = new User();

        user.setId( userEntity.getId() );
        user.setName( userEntity.getName() );
        user.setEmail( userEntity.getEmail() );
        user.setPassword( userEntity.getPassword() );
        user.setCreatedAt( userEntity.getCreatedAt() );

        return user;
    }

    @Override
    public UserEntity toUserEntity(UserInput userInput) {
        if ( userInput == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setName( userInput.getName() );
        userEntity.setEmail( userInput.getEmail() );
        userEntity.setPassword( userInput.getPassword() );

        return userEntity;
    }

    @Override
    public UserInput toUserInput(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        UserInput userInput = new UserInput();

        userInput.setName( userEntity.getName() );
        userInput.setEmail( userEntity.getEmail() );
        userInput.setPassword( userEntity.getPassword() );

        return userInput;
    }
}
