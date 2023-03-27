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

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<UserResponse> addUser(UserInput userInput) {
        return userService.addNewUser(userInput);
    }

    @Override
    public ResponseEntity<Void> deleteUserById(Integer userId) {
        return userService.deleteUserById(userId);
    }

    @Override
    public ResponseEntity<UserResponse> getUserById(Integer userId) {
        return userService.getUserById(userId);
    }

    @Override
    public ResponseEntity<UserResponse> updateUserById(Integer userId, UserInput userInput) {
        return userService.updateUserById(userId, userInput);
    }
}
