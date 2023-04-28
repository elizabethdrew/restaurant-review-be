package dev.drew.restaurantreview.controller;

import dev.drew.restaurantreview.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.openapitools.api.UserApi;
import org.openapitools.model.User;
import org.openapitools.model.UserInput;
import org.openapitools.model.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins ="http://localhost:3000")
@PreAuthorize("isAuthenticated()")
public class UserController implements UserApi {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Add a new user to the database.
     *
     * @param userInput input data for the new user
     * @return response entity containing the new user data
     */
    @Override
    @PostMapping("/user/add")
    @PreAuthorize("permitAll()")
    public ResponseEntity<UserResponse> addUser(UserInput userInput) {
        return userService.addNewUser(userInput);
    }

    /**
     * Delete a user from the database by their ID.
     *
     * @param userId the ID of the user to delete
     * @return response entity indicating success or failure of the operation
     */
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @Override
    @DeleteMapping("/user/{userId}/delete")
    public ResponseEntity<Void> deleteUserById(Integer userId) {
        return userService.deleteUserById(userId);
    }

    /**
     * Get a specific user by their ID.
     *
     * @param userId the ID of the user to retrieve
     * @return response entity containing the user data
     */
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @Override
    @GetMapping("/user/{userId}")
    public ResponseEntity<User> getUserById(Integer userId) {
        return userService.getUserById(userId);
    }

    /**
     * Update a user's data by their ID.
     *
     * @param userId the ID of the user to update
     * @param userInput the updated user data
     * @return response entity containing the updated user data
     */
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @Override
    @PutMapping("/user/{userId}/edit")
    public ResponseEntity<UserResponse> updateUserById(Integer userId, UserInput userInput) {
        return userService.updateUserById(userId, userInput);
    }
}