package dev.drew.restaurantreview.controller;

import dev.drew.restaurantreview.service.RestaurantService;
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
    public ResponseEntity<User> addUser(UserInput userInput) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteUserById(String userId) {
        return null;
    }

    @Override
    public ResponseEntity<UserResponse> getUserById(String userId) {
        return null;
    }

    @Override
    public ResponseEntity<UserResponse> updateUserById(String userId, UserInput userInput) {
        return null;
    }
}
