package dev.drew.restaurantreview.controller;

import dev.drew.restaurantreview.exception.InsufficientPermissionException;
import dev.drew.restaurantreview.exception.UserNotFoundException;
import dev.drew.restaurantreview.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Min;
import org.openapitools.api.UserApi;
import org.openapitools.model.User;
import org.openapitools.model.UserInput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

//import javax.validation.Valid;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@PreAuthorize("isAuthenticated()")
public class UserController implements UserApi {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<User> addUser( @Valid @RequestBody UserInput userInput ) {
        User user = userService.addNewUser(userInput);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @Override
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(
            @Min(1) @PathVariable Integer userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @Override
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(
            @Min(1) @PathVariable Integer userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Override
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUserById(
            @Min(1) @PathVariable Integer userId,
            @RequestBody @Valid UserInput userInput)
            throws UserNotFoundException, InsufficientPermissionException {
        User updatedUser = userService.updateUserById(userId, userInput);
        return ResponseEntity.ok(updatedUser);
    }
}