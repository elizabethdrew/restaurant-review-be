package dev.drew.restaurantreview.service;

import dev.drew.restaurantreview.entity.UserEntity;
import dev.drew.restaurantreview.mapper.UserMapper;
import dev.drew.restaurantreview.repository.UserRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.openapitools.model.UserResponse;
import org.openapitools.model.User;
import org.openapitools.model.UserInput;
import org.openapitools.model.Error;

import java.time.OffsetDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*
        Add a new user to the database
        Example curl command: curl -X POST http://localhost:8080/user -H "Content-Type: application/json" -d '{"name": "User Name", "city": "City Name", "rating": 4}'
    */
    public ResponseEntity<UserResponse> addNewUser(UserInput userInput) {
        UserMapper mapper = UserMapper.INSTANCE;
        UserEntity user = mapper.toUserEntity(userInput);
        user.setCreatedAt(OffsetDateTime.now());

        UserResponse userResponse = new UserResponse();

        try {
            UserEntity savedUser = userRepository.save(user);
            User savedApiUser = mapper.toUser(savedUser);
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
}
