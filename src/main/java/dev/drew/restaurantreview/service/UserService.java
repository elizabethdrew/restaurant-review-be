package dev.drew.restaurantreview.service;

import org.openapitools.model.User;
import org.openapitools.model.UserInput;
import org.openapitools.model.UserResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    // Add a new user to the database
    User addNewUser(UserInput userInput);

    // Get a user by their ID
    User getUserById(Integer userId);

    // Delete a user by their ID
    ResponseEntity<Void> deleteUserById(Integer userId);

    // Update a user by their ID
    User updateUserById(Integer userId, UserInput userInput);
}