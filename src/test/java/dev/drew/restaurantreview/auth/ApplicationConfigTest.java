package dev.drew.restaurantreview.auth;

import dev.drew.restaurantreview.service.JpaUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

// Use MockitoExtension to enable Mockito annotations in the test class
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ApplicationConfigTest {

    // Inject the mocked dependencies into the ApplicationConfig instance
    @InjectMocks
    private ApplicationConfig applicationConfig;

    // Mock the JpaUserDetailsService dependency
    @Mock
    private JpaUserDetailsService jpaUserDetailsService;

    // Mock the AuthenticationConfiguration dependency
    @Mock
    private AuthenticationConfiguration authenticationConfiguration;

    private AuthenticationManager authenticationManager;

    // Test that the authenticationManager() method returns the correct AuthenticationManager instance
    @Test
    public void testAuthenticationManager() throws Exception {
        AuthenticationManager result = applicationConfig.authenticationManager(authenticationConfiguration);

        assertSame(authenticationManager, result);
    }

    // Test that the passwordEncoder() method returns a BCryptPasswordEncoder instance
    @Test
    public void testPasswordEncoder() {
        BCryptPasswordEncoder passwordEncoder = ApplicationConfig.passwordEncoder();
        assertTrue(passwordEncoder instanceof BCryptPasswordEncoder);
    }

    // Test that the authenticationProvider() method creates a DaoAuthenticationProvider instance
// with the correct UserDetailsService and a BCryptPasswordEncoder for password encoding
    @Test
    public void testAuthenticationProvider() {
        UserDetails testUser = User.builder()
                .username("testUser")
                .password(new BCryptPasswordEncoder().encode("password"))
                .authorities(Collections.emptyList())
                .build();

        when(jpaUserDetailsService.loadUserByUsername("testUser")).thenReturn(testUser);

        AuthenticationProvider authenticationProvider = applicationConfig.authenticationProvider();

        assertTrue(authenticationProvider instanceof DaoAuthenticationProvider);

        Authentication authentication = new UsernamePasswordAuthenticationToken("testUser", "password");
        Authentication result = authenticationProvider.authenticate(authentication);

        assertTrue(result.isAuthenticated());
        assertEquals("testUser", result.getName());
    }
}