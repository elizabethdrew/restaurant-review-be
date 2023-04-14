package dev.drew.restaurantreview.auth;

import dev.drew.restaurantreview.entity.SecurityUser;
import dev.drew.restaurantreview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

// Annotate the class as a service
@Service
// Lombok annotation for generating a constructor for required dependencies
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtService jwtService;
    // Use Spring's dependency injection for the AuthenticationManager
    @Autowired
    private final AuthenticationManager authenticationManager;

    // Authenticate the user with the provided credentials and generate a JWT token upon successful authentication
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Authenticate the user with the provided username and password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        // Retrieve the user from the repository
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();
        // Create a SecurityUser instance for generating the JWT token
        SecurityUser securityUser = new SecurityUser(user);
        // Generate the JWT token
        var jwtToken = jwtService.generateToken(securityUser);
        // Build and return the AuthenticationResponse
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
