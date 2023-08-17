package dev.drew.restaurantreview.auth.unit;

import dev.drew.restaurantreview.auth.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JwtServiceTest {

    @Autowired
    private JwtService jwtService;
    private final String SECRET_KEY = "472B4B6250645367566B5970337336763979244226452948404D635166546A57";

    @BeforeEach
    public void setUp() {
        jwtService = new JwtService();
    }

    @Test
    public void testGenerateToken() {
        UserDetails mockUserDetails = mock(UserDetails.class);
        when(mockUserDetails.getUsername()).thenReturn("testUser");

        String token = jwtService.generateToken(mockUserDetails);

        assertNotNull(token);
        assertTrue(token.split("\\.").length == 3);  // A valid JWT has 3 parts separated by '.'
    }

    @Test
    public void testExtractUsername() {
        UserDetails mockUserDetails = mock(UserDetails.class);
        when(mockUserDetails.getUsername()).thenReturn("testUser");

        String token = jwtService.generateToken(mockUserDetails);
        String extractedUsername = jwtService.extractUsername(token);

        assertEquals("testUser", extractedUsername);
    }

    @Test
    public void testIsTokenValid() {
        UserDetails mockUserDetails = mock(UserDetails.class);
        when(mockUserDetails.getUsername()).thenReturn("testUser");

        String token = jwtService.generateToken(mockUserDetails);

        assertTrue(jwtService.isTokenValid(token, mockUserDetails));
    }
}