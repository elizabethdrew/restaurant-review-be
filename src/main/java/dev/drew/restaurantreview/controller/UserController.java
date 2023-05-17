package dev.drew.restaurantreview.controller;

import dev.drew.restaurantreview.exception.InsufficientPermissionException;
import dev.drew.restaurantreview.exception.RestaurantNotFoundException;
import dev.drew.restaurantreview.exception.UserNotFoundException;
import dev.drew.restaurantreview.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.openapitools.api.UserApi;
import org.openapitools.model.Restaurant;
import org.openapitools.model.User;
import org.openapitools.model.UserInput;
import org.openapitools.model.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @PostMapping("/signup")
    @PreAuthorize("permitAll()")
    public ResponseEntity<User> addUser( @Valid @RequestBody UserInput userInput ) {
        User user = userService.addNewUser(userInput);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
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
    public ResponseEntity<User> getUserById(@PathVariable Integer userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
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
    public ResponseEntity<User> updateUserById(
            @PathVariable Integer userId,
            @RequestBody @Valid UserInput userInput)
            throws UserNotFoundException, InsufficientPermissionException {
        User updatedUser = userService.updateUserById(userId, userInput);
        return ResponseEntity.ok(updatedUser);
    }
}