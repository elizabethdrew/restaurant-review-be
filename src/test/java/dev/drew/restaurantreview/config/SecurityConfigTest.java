package dev.drew.restaurantreview.config;

import dev.drew.restaurantreview.auth.ApplicationConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Use the SpringExtension for JUnit 5 to integrate with Spring Boot
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
// Load the Spring Boot application context to create necessary beans
@SpringBootTest
public class SecurityConfigTest {

    @Autowired
    private SecurityConfig securityConfig;

    private HttpSecurity httpSecurity;

    // Test the passwordEncoder() method to ensure it returns a non-null BCryptPasswordEncoder instance
//    @Test
//    public void testPasswordEncoder() {
//        BCryptPasswordEncoder encoder = ApplicationConfig.passwordEncoder();
//        assertNotNull(encoder, "PasswordEncoder should not be null");
//        assertTrue(encoder instanceof BCryptPasswordEncoder, "PasswordEncoder should be an instance of BCryptPasswordEncoder");
//    }
//
//    // Test the securityFilterChain(HttpSecurity) method to ensure it returns a non-null SecurityFilterChain
//    @Test
//    public void testSecurityFilterChain() throws Exception {
//        SecurityFilterChain securityFilterChain = securityConfig.securityFilterChain(httpSecurity);
//        assertNotNull(securityFilterChain, "SecurityFilterChain should not be null");
//    }
}