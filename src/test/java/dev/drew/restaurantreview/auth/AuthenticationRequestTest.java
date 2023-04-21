package dev.drew.restaurantreview.auth;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationRequestTest {

    @Test
    public void testAuthenticationRequestGettersAndSetters() {
        AuthenticationRequest request = AuthenticationRequest.builder()
                .username("testuser")
                .password("testpassword")
                .build();

        assertEquals("testuser", request.getUsername());
        assertEquals("testpassword", request.getPassword());

        request.setUsername("newuser");
        request.setPassword("newpassword");

        assertEquals("newuser", request.getUsername());
        assertEquals("newpassword", request.getPassword());
    }

    @Test
    public void testAuthenticationRequestBuilder() {
        AuthenticationRequest request = AuthenticationRequest.builder()
                .username("testuser")
                .password("testpassword")
                .build();

        assertNotNull(request);
        assertEquals("testuser", request.getUsername());
        assertEquals("testpassword", request.getPassword());
    }

    @Test
    public void testAuthenticationRequestEqualsAndHashCode() {
        AuthenticationRequest request1 = AuthenticationRequest.builder()
                .username("testuser")
                .password("testpassword")
                .build();

        AuthenticationRequest request2 = AuthenticationRequest.builder()
                .username("testuser")
                .password("testpassword")
                .build();

        AuthenticationRequest request3 = AuthenticationRequest.builder()
                .username("differentuser")
                .password("differentpassword")
                .build();

        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
        assertEquals(request1.hashCode(), request2.hashCode());
        assertNotEquals(request1.hashCode(), request3.hashCode());
    }
}