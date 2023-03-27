package dev.drew.restaurantreview.service;

import dev.drew.restaurantreview.entity.RestaurantEntity;
import dev.drew.restaurantreview.entity.UserEntity;
import dev.drew.restaurantreview.mapper.RestaurantMapper;
import dev.drew.restaurantreview.mapper.UserMapper;
import dev.drew.restaurantreview.repository.UserRepository;
import org.openapitools.model.*;
import org.openapitools.model.Error;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    /*
        Add a new user to the database
        Example curl command: curl -X POST http://localhost:8080/user -H "Content-Type: application/json" -d '{"name": "User Name", "city": "City Name", "rating": 4}'
    */
    public ResponseEntity<UserResponse> addNewUser(UserInput userInput) {
        //UserMapper mapper = UserMapper.INSTANCE;
        UserEntity user = userMapper.toUserEntity(userInput);
        user.setCreatedAt(OffsetDateTime.now());

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

    public ResponseEntity<UserResponse> getUserById(Integer userId) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId.longValue());

        if (userEntityOptional.isPresent()) {
            User user = userMapper.toUser(userEntityOptional.get());
            UserResponse userResponse = new UserResponse();
            userResponse.setUser(user);
            return ResponseEntity.ok(userResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    /*
        Delete a user by ID
        Example curl command: curl -X DELETE http://localhost:8080/user/{userId}
        */
    public ResponseEntity<Void> deleteUserById(Integer userId) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId.longValue());
        if (userEntityOptional.isPresent()) {
            userRepository.deleteById(userId.longValue());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<UserResponse> updateUserById(Integer userId, UserInput userInput) {
        //UserMapper mapper = UserMapper.INSTANCE;
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId.longValue());

        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            UserEntity updatedEntity = userMapper.toUserEntity(userInput);
            updatedEntity.setId(userEntity.getId());
            updatedEntity.setCreatedAt(userEntity.getCreatedAt());

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