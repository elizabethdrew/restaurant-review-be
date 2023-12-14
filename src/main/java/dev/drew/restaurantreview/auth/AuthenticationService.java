package dev.drew.restaurantreview.auth;

import dev.drew.restaurantreview.exception.InvalidCredentialsException;
import dev.drew.restaurantreview.exception.AuthenticationFailedException;
import dev.drew.restaurantreview.exception.UserNotFoundException;
import dev.drew.restaurantreview.model.SecurityUser;
import dev.drew.restaurantreview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        log.info("Starting Authentication...");
        try {
            // Authenticate the user with the provided username and password
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            log.info("Invalid Credentials");
            throw new InvalidCredentialsException("Invalid Credentials");
        } catch (Exception e) {
            log.info("Authentication Failed");
            throw new AuthenticationFailedException("Authentication Failed");
        }

        // Retrieve the user from the repository
        log.info("Searching For User");
        var user = userRepository.findByUsernameAndIsDeletedFalse(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        log.info("Generating JWT");

        // Create a SecurityUser instance for generating the JWT token
        SecurityUser securityUser = new SecurityUser(user);

        // Generate the JWT token
        var jwtToken = jwtService.generateToken(securityUser);

        // Generate expiration time
        var expirationTime = JwtService.extractExpiration(jwtToken);

        // Build and return the AuthenticationResponse
        log.info("Returning JWT, Expiration Time and User Id");
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userId(securityUser.getId())
                .expirationTime(expirationTime)
                .build();
    }
}
