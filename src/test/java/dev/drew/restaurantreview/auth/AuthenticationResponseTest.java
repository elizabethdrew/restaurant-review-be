package dev.drew.restaurantreview.auth;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationResponseTest {

    @Test
    public void testAuthenticationResponseGettersAndSetters() {
        AuthenticationResponse response = AuthenticationResponse.builder()
                .token("testtoken")
                .build();

        assertEquals("testtoken", response.getToken());

        response.setToken("newtoken");

        assertEquals("newtoken", response.getToken());
    }

    @Test
    public void testAuthenticationResponseBuilder() {
        AuthenticationResponse response = AuthenticationResponse.builder()
                .token("testtoken")
                .build();

        assertNotNull(response);
        assertEquals("testtoken", response.getToken());
    }

    @Test
    public void testAuthenticationResponseEqualsAndHashCode() {
        AuthenticationResponse response1 = AuthenticationResponse.builder()
                .token("testtoken")
                .build();

        AuthenticationResponse response2 = AuthenticationResponse.builder()
                .token("testtoken")
                .build();

        AuthenticationResponse response3 = AuthenticationResponse.builder()
                .token("differenttoken")
                .build();

        assertEquals(response1, response2);
        assertNotEquals(response1, response3);
        assertEquals(response1.hashCode(), response2.hashCode());
        assertNotEquals(response1.hashCode(), response3.hashCode());
    }
}