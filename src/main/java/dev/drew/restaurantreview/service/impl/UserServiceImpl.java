package dev.drew.restaurantreview.service.impl;

import dev.drew.restaurantreview.entity.RestaurantEntity;
import dev.drew.restaurantreview.exception.InsufficientPermissionException;
import dev.drew.restaurantreview.exception.RestaurantNotFoundException;
import dev.drew.restaurantreview.exception.UserNotFoundException;
import dev.drew.restaurantreview.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import dev.drew.restaurantreview.entity.UserEntity;
import dev.drew.restaurantreview.mapper.UserMapper;
import dev.drew.restaurantreview.repository.UserRepository;
import org.openapitools.model.*;
import org.openapitools.model.Error;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import dev.drew.restaurantreview.util.interfaces.EntityUserIdProvider;

import java.time.OffsetDateTime;
import java.util.Optional;

import static dev.drew.restaurantreview.util.SecurityUtils.isAdminOrOwner;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    // Implement EntityUserIdProvider for UserEntity
    private final EntityUserIdProvider<UserEntity> userEntityUserIdProvider = UserEntity::getId;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }




    public ResponseEntity<UserResponse> addNewUser(UserInput userInput) {
        UserEntity user = userMapper.toUserEntity(userInput);
        user.setCreatedAt(OffsetDateTime.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        UserResponse userResponse = new UserResponse();

        try {
            UserEntity savedUser = userRepository.save(user);
            User savedApiUser = userMapper.toUser(savedUser);
            userResponse.setUser(savedApiUser);
            userResponse.setSuccess(true);
            return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            // Handle database constraint violations, such as unique constraints
            userResponse.setSuccess(false);
            userResponse.setError(new Error().message("Invalid input: " + e.getMessage()));
            return new ResponseEntity<>(userResponse, HttpStatus.BAD_REQUEST);
        } catch (DataAccessException e) {
            // Handle other database-related exceptions
            userResponse.setSuccess(false);
            userResponse.setError(new Error().message("Database error: " + e.getMessage()));
            return new ResponseEntity<>(userResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            // Handle general exceptions
            userResponse.setSuccess(false);
            userResponse.setError(new Error().message("An unexpected error occurred: " + e.getMessage()));
            return new ResponseEntity<>(userResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /*
        Get a user by ID
        Example curl command: curl -X GET http://localhost:8080/user/{userId}
    */

    public User getUserById(Integer userId) {

        UserEntity userEntity = userRepository.findById(userId.longValue())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

            if (!isAdminOrOwner(userEntity, userEntityUserIdProvider)) {
                throw new InsufficientPermissionException("User does not have permission to update this restaurant");
            }

            return userMapper.toUser(userEntity);
    }


    /*
        Delete a user by ID
        Example curl command: curl -X DELETE http://localhost:8080/user/{userId}
        */
    public ResponseEntity<Void> deleteUserById(Integer userId) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId.longValue());

        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();

            if (!isAdminOrOwner(userEntity, userEntityUserIdProvider)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            userRepository.deleteById(userId.longValue());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<UserResponse> updateUserById(Integer userId, UserInput userInput) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId.longValue());

        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();

            if (!isAdminOrOwner(userEntity, UserEntity::getId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            UserEntity updatedEntity = userMapper.toUserEntity(userInput);
            updatedEntity.setId(userEntity.getId());
            updatedEntity.setCreatedAt(userEntity.getCreatedAt());
            updatedEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

            try {
                UserEntity savedUser = userRepository.save(updatedEntity);
                User savedApiUser = userMapper.toUser(savedUser);

                UserResponse userResponse = new UserResponse();
                userResponse.setUser(savedApiUser);

                return ResponseEntity.ok(userResponse);
            } catch (DataIntegrityViolationException e) {
                // Handle database constraint violations, such as unique constraints
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (DataAccessException e) {
                // Handle other database-related exceptions
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            } catch (Exception e) {
                // Handle general exceptions
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
