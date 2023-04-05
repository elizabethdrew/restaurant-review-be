package dev.drew.restaurantreview.service;

import org.openapitools.model.User;
import org.openapitools.model.UserInput;
import org.openapitools.model.UserResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<UserResponse> addNewUser(UserInput userInput);
    ResponseEntity<User> getUserById(Integer userId);
    ResponseEntity<Void> deleteUserById(Integer userId);
    ResponseEntity<UserResponse> updateUserById(Integer userId, UserInput userInput);
}