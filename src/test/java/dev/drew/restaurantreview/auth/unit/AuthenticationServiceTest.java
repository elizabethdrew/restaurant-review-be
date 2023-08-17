package dev.drew.restaurantreview.auth;

import dev.drew.restaurantreview.entity.UserEntity;
import dev.drew.restaurantreview.exception.AuthenticationFailedException;
import dev.drew.restaurantreview.exception.InvalidCredentialsException;
import dev.drew.restaurantreview.exception.UserNotFoundException;
import dev.drew.restaurantreview.model.SecurityUser;
import dev.drew.restaurantreview.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    BCryptPasswordEncoder passwordEncoder;

    @Mock
    JwtService jwtService;

    @Mock
    AuthenticationManager authenticationManager;

    @InjectMocks
    AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void authenticate_jwtExpired_throwsException() {
        String username = "testUser";
        String password = "testPassword";
        String expiredToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY5MjE4Mzc0MywiZXhwIjoxNjkyMjcwMTQzfQ.jkTdFddw1I-hgbT8Rbl7dY_vsdn4tgujT999J7I7kac";

        UserEntity user = new UserEntity();
        user.setUsername(username);

        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername(username);
        request.setPassword(password);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any(SecurityUser.class))).thenReturn(expiredToken);

        ExpiredJwtException thrownException = assertThrows(ExpiredJwtException.class, () -> {
            authenticationService.authenticate(request);
        });
    }

    @Test
    void authenticate_invalidCredentials() {
        String username = "testUser";
        String password = "testPassword";

        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername(username);
        request.setPassword(password);

        when(authenticationManager.authenticate(any())).thenThrow(BadCredentialsException.class);

        assertThrows(InvalidCredentialsException.class, () -> authenticationService.authenticate(request));
    }

    @Test
    void authenticate_authenticationFailure() {
        String username = "testUser";
        String password = "testPassword";

        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername(username);
        request.setPassword(password);

        when(authenticationManager.authenticate(any())).thenThrow(RuntimeException.class);

        assertThrows(AuthenticationFailedException.class, () -> authenticationService.authenticate(request));
    }

    @Test
    void authenticate_userNotFound() {
        String username = "testUser";
        String password = "testPassword";

        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername(username);
        request.setPassword(password);

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> authenticationService.authenticate(request));
    }
}