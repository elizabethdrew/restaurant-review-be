package dev.drew.restaurantreview.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    // The token field for the authentication response, holding the generated authentication token
    private String token;

}
