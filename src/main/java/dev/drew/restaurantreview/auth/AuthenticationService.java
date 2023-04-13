package dev.drew.restaurantreview.auth;

import dev.drew.restaurantreview.entity.SecurityUser;
import dev.drew.restaurantreview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
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
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();
        SecurityUser securityUser = new SecurityUser(user);
        var jwtToken = jwtService.generateToken(securityUser);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
