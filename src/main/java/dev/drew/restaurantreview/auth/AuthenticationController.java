package dev.drew.restaurantreview.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// REST controller for handling authentication requests
@RestController
@CrossOrigin(origins ="http://localhost:3000")
@RequiredArgsConstructor
public class AuthenticationController {

    // AuthenticationService instance for handling the authentication process
    private final AuthenticationService service;

    // Map the POST /api/v1/auth/authenticate endpoint to the authenticate method
    @PostMapping("api/v1/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        AuthenticationResponse response = service.authenticate(request);
        System.out.println("Generated Authentication Response: " + response);
        return ResponseEntity.ok(response);

        // Delegate the authentication process to the AuthenticationService and return the response
        //return ResponseEntity.ok(service.authenticate(request));
    }

}

