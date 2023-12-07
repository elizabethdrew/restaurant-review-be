package dev.drew.restaurantreview.service;

import org.openapitools.model.User;
import org.openapitools.model.UserInput;
import org.openapitools.model.UserUpdateInput;

public interface UserService {
    // Add a new user to the database
    User addNewUser(UserInput userInput);

    // Get a user by their ID
    User getUserById(Integer userId);

    // Delete a user by their ID
    void deleteUserById(Integer userId);

    // Update a user by their ID
    User updateUserById(Integer userId, UserUpdateInput userInput);
}