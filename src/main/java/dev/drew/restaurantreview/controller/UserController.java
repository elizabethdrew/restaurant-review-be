package dev.drew.restaurantreview.controller;

import dev.drew.restaurantreview.service.UserService;
import org.openapitools.api.UserApi;
import org.openapitools.model.User;
import org.openapitools.model.UserInput;
import org.openapitools.model.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@PreAuthorize("isAuthenticated()")
public class UserController implements UserApi {

    private final UserService userService;

    // Constructor for dependency injection of UserService
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint to add a new user, accessible to all users
    @Override
    @PreAuthorize("permitAll()")
    public ResponseEntity<UserResponse> addUser(UserInput userInput) {
        return userService.addNewUser(userInput);
    }

    // Endpoint to delete a user by their ID, accessible to authenticated users
    @Override
    public ResponseEntity<Void> deleteUserById(Integer userId) {
        return userService.deleteUserById(userId);
    }

    // Endpoint to get a single user by their ID, accessible to authenticated users
    @Override
    public ResponseEntity<User> getUserById(Integer userId) {
        return userService.getUserById(userId);
    }

    // Endpoint to update a user by their ID, accessible to authenticated users
    @Override
    public ResponseEntity<UserResponse> updateUserById(Integer userId, UserInput userInput) {
        return userService.updateUserById(userId, userInput);
    }
}
