package dev.drew.restaurantreview.mapper;

import dev.drew.restaurantreview.entity.UserEntity;
import javax.annotation.processing.Generated;
import org.openapitools.model.User;
import org.openapitools.model.UserInput;
import org.openapitools.model.UserInput.RoleEnum;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-27T15:55:17+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.6 (Amazon.com Inc.)"
)
@Component
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
        user.setRole( userEntity.getRole() );
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
        userEntity.setRole( roleEnumToRoleEnum( userInput.getRole() ) );

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
        userInput.setRole( roleEnumToRoleEnum1( userEntity.getRole() ) );

        return userInput;
    }

    protected org.openapitools.model.User.RoleEnum roleEnumToRoleEnum(RoleEnum roleEnum) {
        if ( roleEnum == null ) {
            return null;
        }

        org.openapitools.model.User.RoleEnum roleEnum1;

        switch ( roleEnum ) {
            case ADMIN: roleEnum1 = org.openapitools.model.User.RoleEnum.ADMIN;
            break;
            case REVIEWER: roleEnum1 = org.openapitools.model.User.RoleEnum.REVIEWER;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + roleEnum );
        }

        return roleEnum1;
    }

    protected RoleEnum roleEnumToRoleEnum1(org.openapitools.model.User.RoleEnum roleEnum) {
        if ( roleEnum == null ) {
            return null;
        }

        RoleEnum roleEnum1;

        switch ( roleEnum ) {
            case ADMIN: roleEnum1 = RoleEnum.ADMIN;
            break;
            case REVIEWER: roleEnum1 = RoleEnum.REVIEWER;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + roleEnum );
        }

        return roleEnum1;
    }
}
