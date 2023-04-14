package dev.drew.restaurantreview.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Lombok annotations for generating getters, setters, constructors, and a builder
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    // The username field for the authentication request
    private String username;

    // The password field for the authentication request
    private String password;
}

