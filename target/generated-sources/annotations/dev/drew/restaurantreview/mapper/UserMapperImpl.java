package dev.drew.restaurantreview.mapper;

import dev.drew.restaurantreview.entity.UserEntity;
import javax.annotation.processing.Generated;
import org.openapitools.model.User;
import org.openapitools.model.UserInput;
import org.openapitools.model.UserInput.UserRoleEnum;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-27T14:02:50+0100",
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
        user.setUsername( userEntity.getUsername() );
        user.setPassword( userEntity.getPassword() );
        user.setUserRole( userEntity.getUserRole() );
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
        userEntity.setUsername( userInput.getUsername() );
        userEntity.setPassword( userInput.getPassword() );
        userEntity.setUserRole( userRoleEnumToUserRoleEnum( userInput.getUserRole() ) );

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
        userInput.setUsername( userEntity.getUsername() );
        userInput.setPassword( userEntity.getPassword() );
        userInput.setUserRole( userRoleEnumToUserRoleEnum1( userEntity.getUserRole() ) );

        return userInput;
    }

    protected org.openapitools.model.User.UserRoleEnum userRoleEnumToUserRoleEnum(UserRoleEnum userRoleEnum) {
        if ( userRoleEnum == null ) {
            return null;
        }

        org.openapitools.model.User.UserRoleEnum userRoleEnum1;

        switch ( userRoleEnum ) {
            case ADMIN: userRoleEnum1 = org.openapitools.model.User.UserRoleEnum.ADMIN;
            break;
            case REVIEWER: userRoleEnum1 = org.openapitools.model.User.UserRoleEnum.REVIEWER;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + userRoleEnum );
        }

        return userRoleEnum1;
    }

    protected UserRoleEnum userRoleEnumToUserRoleEnum1(org.openapitools.model.User.UserRoleEnum userRoleEnum) {
        if ( userRoleEnum == null ) {
            return null;
        }

        UserRoleEnum userRoleEnum1;

        switch ( userRoleEnum ) {
            case ADMIN: userRoleEnum1 = UserRoleEnum.ADMIN;
            break;
            case REVIEWER: userRoleEnum1 = UserRoleEnum.REVIEWER;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + userRoleEnum );
        }

        return userRoleEnum1;
    }
}
