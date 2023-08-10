package dev.drew.restaurantreview.service;

import dev.drew.restaurantreview.entity.UserEntity;
import dev.drew.restaurantreview.exception.InsufficientPermissionException;
import dev.drew.restaurantreview.exception.UserNotFoundException;
import dev.drew.restaurantreview.mapper.UserMapper;
import dev.drew.restaurantreview.model.SecurityUser;
import dev.drew.restaurantreview.repository.UserRepository;
import dev.drew.restaurantreview.service.impl.UserServiceImpl;
import dev.drew.restaurantreview.util.SecurityUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.User;
import org.openapitools.model.UserInput;
import org.openapitools.model.User.RoleEnum;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.openapitools.model.User.RoleEnum.ADMIN;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        SecurityUser securityUser = createSecurityUserWithRole(ADMIN);
        SecurityUtils.setCurrentUser(securityUser);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    private SecurityUser createSecurityUserWithRole(RoleEnum role) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("testUser");
        userEntity.setPassword("password");
        userEntity.setRole(role);

        return new SecurityUser(userEntity);
    }

    @Test
    void testAddNewUser() {
        // Prepare input data and expected response
        UserInput userInput = new UserInput().username("newUser").password("password");
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userInput.getUsername());
        userEntity.setPassword(userInput.getPassword());
        userEntity.setCreatedAt(OffsetDateTime.now());

        User savedUserResponse = new User().username(userInput.getUsername());

        // Mock the mapper and repository calls
        when(userMapper.toUserEntity(any(UserInput.class))).thenReturn(userEntity);
        when(passwordEncoder.encode(anyString())).thenReturn(userInput.getPassword());
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(userMapper.toUser(any(UserEntity.class))).thenReturn(savedUserResponse);

        // Call the service method
        User savedUser = userServiceImpl.addNewUser(userInput);

        // Verify the response status
        assertEquals(userInput.getUsername(), savedUser.getUsername());
    }

//    @Test
//    void testGetUserById() {
//        // Prepare input data
//        Integer userId = 1;
//        UserEntity userEntity = new UserEntity();
//        userEntity.setId(userId.longValue());
//        userEntity.setUsername("testUser");
//
//        // Mock the repository call
//        when(userRepository.findById(userId.longValue())).thenReturn(Optional.of(userEntity));
//
//        // Call the service method
//        User user = userServiceImpl.getUserById(userId);
//
//        // Verify the response status
//        assertNotNull(user);
//        assertEquals(userEntity.getUsername(), user.getUsername());
//    }
//
//    @Test
//    void testGetUserByIdNotFound() {
//        // Prepare input data
//        Integer userId = 1;
//
//        // Mock the repository call
//        when(userRepository.findById(userId.longValue())).thenReturn(Optional.empty());
//
//        // Verify the response status
//        assertThrows(UserNotFoundException.class, () -> userServiceImpl.getUserById(userId));
//    }
//
//    @Test
//    void testDeleteUserById() {
//        // Prepare input data
//        Integer userId = 1;
//        UserEntity userEntity = new UserEntity();
//        userEntity.setId(userId.longValue());
//        userEntity.setUsername("testUser");
//
//        // Mock the repository call
//        when(userRepository.findById(userId.longValue())).thenReturn(Optional.of(userEntity));
//
//        // Call the service method
//        userServiceImpl.deleteUserById(userId);
//
//        // Verify the repository call
//        verify(userRepository, times(1)).deleteById(userId.longValue());
//    }
//
//    @Test
//    void testDeleteUserByIdNotFound() {
//        // Prepare input data
//        Integer userId = 1;
//
//        // Mock the repository call
//        when(userRepository.findById(userId.longValue())).thenReturn(Optional.empty());
//
//        // Verify the response status
//        assertThrows(UserNotFoundException.class, () -> userServiceImpl.deleteUserById(userId));
//
//        // Verify the repository call
//        verify(userRepository, never()).deleteById(anyLong());
//    }
//
//    @Test
//    void testUpdateUserById() {
//        // Prepare input data
//        Integer userId = 1;
//        UserInput userInput = new UserInput().username("newUsername");
//
//        // Prepare expected data
//        UserEntity userEntity = new UserEntity();
//        userEntity.setId(userId.longValue());
//        userEntity.setUsername("testUser");
//        userEntity.setCreatedAt(OffsetDateTime.now());
//
//        UserEntity updatedEntity = new UserEntity();
//        updatedEntity.setId(userId.longValue());
//        updatedEntity.setUsername(userInput.getUsername());
//        updatedEntity.setCreatedAt(userEntity.getCreatedAt());
//        updatedEntity.setPassword(userEntity.getPassword());
//
//        // Mock the repository and mapper calls
//        when(userRepository.findById(userId.longValue())).thenReturn(Optional.of(userEntity));
//        when(userMapper.toUserEntity(any(UserInput.class))).thenReturn(updatedEntity);
//        when(userRepository.save(any(UserEntity.class))).thenReturn(updatedEntity);
//
//        User updatedUserResponse = new User().username(userInput.getUsername());
//        when(userMapper.toUser(any(UserEntity.class))).thenReturn(updatedUserResponse);
//
//        // Call the service method
//        User updatedUser = userServiceImpl.updateUserById(userId, userInput);
//
//        // Verify the response
//        assertEquals(userInput.getUsername(), updatedUser.getUsername());
//    }
}
