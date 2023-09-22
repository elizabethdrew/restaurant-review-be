package dev.drew.restaurantreview.auth;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Date;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    private final JwtBlacklistRepository jwtBlacklistRepository;

    @PostMapping("api/v1/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = service.authenticate(request);
        return ResponseEntity.ok(response);
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("api/v1/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {

        // Extract the JWT token
        String jwtToken = authHeader.substring(7);

        // Get expiration date of the token
        Date expiration = JwtService.extractExpiration(jwtToken);

        // Save the token in the blacklist
        JwtBlacklist blacklistedToken = new JwtBlacklist();
        blacklistedToken.setToken(jwtToken);
        blacklistedToken.setExpiration(expiration);
        jwtBlacklistRepository.save(blacklistedToken);

        return ResponseEntity.ok("Logged out successfully");
    }

}

