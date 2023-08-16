package dev.drew.restaurantreview.auth;

import dev.drew.restaurantreview.exception.InvalidCredentialsException;
import dev.drew.restaurantreview.exception.AuthenticationFailedException;
import dev.drew.restaurantreview.exception.UserNotFoundException;
import dev.drew.restaurantreview.model.SecurityUser;
import dev.drew.restaurantreview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            // Authenticate the user with the provided username and password
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("Invalid Credentials");
        } catch (Exception e) {
            throw new AuthenticationFailedException("Authentication Failed");
        }

        // Retrieve the user from the repository
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Create a SecurityUser instance for generating the JWT token
        SecurityUser securityUser = new SecurityUser(user);

        // Generate the JWT token
        var jwtToken = jwtService.generateToken(securityUser);

        // Generate expiration time
        var expirationTime = JwtService.extractExpiration(jwtToken);

        // Build and return the AuthenticationResponse
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userId(securityUser.getId())
                .expirationTime(expirationTime)
                .build();
    }
}
