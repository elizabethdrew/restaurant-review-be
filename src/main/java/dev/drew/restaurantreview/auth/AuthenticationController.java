package dev.drew.restaurantreview.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// REST controller for handling authentication requests
@RestController
//@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    // AuthenticationService instance for handling the authentication process
    private final AuthenticationService service;

    // Map the POST /api/v1/auth/authenticate endpoint to the authenticate method
    //@PostMapping("/authenticate")
    @PostMapping("api/v1/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        // Delegate the authentication process to the AuthenticationService and return the response
        return ResponseEntity.ok(service.authenticate(request));
    }

}

