package dev.drew.restaurantreview.controller;

import dev.drew.restaurantreview.exception.InsufficientPermissionException;
import dev.drew.restaurantreview.exception.UserNotFoundException;
import dev.drew.restaurantreview.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.User;
import org.openapitools.model.UserInput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user;
    private UserInput userInput;

    @BeforeEach
    void setUp() {
        userInput = new UserInput();
        userInput.setEmail("test@test.com");
        userInput.setUsername("testUser");
        userInput.setPassword("password");
        userInput.setName("Test User");

        user = new User();
        user.setEmail("test@test.com");
        user.setUsername("testUser");
        user.setName("Test User");
        user.setCreatedAt(OffsetDateTime.now());
        user.setRole(User.RoleEnum.ADMIN);
    }

    @Test
    void shouldAddUser() {
        when(userService.addNewUser(any(UserInput.class))).thenReturn(user);

        ResponseEntity<User> response = userController.addUser(userInput);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void shouldDeleteUserById() {
        doNothing().when(userService).deleteUserById(any(Integer.class));

        ResponseEntity<Void> response = userController.deleteUserById(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void shouldGetUserById() throws UserNotFoundException {
        when(userService.getUserById(any(Integer.class))).thenReturn(user);

        ResponseEntity<User> response = userController.getUserById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void shouldUpdateUserById() throws UserNotFoundException, InsufficientPermissionException {
        when(userService.updateUserById(any(Integer.class), any(UserInput.class))).thenReturn(user);

        ResponseEntity<User> response = userController.updateUserById(1, userInput);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }
}