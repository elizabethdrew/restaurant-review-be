package dev.drew.restaurantreview.config;

import dev.drew.restaurantreview.auth.JwtAuthenticationFilter;
import dev.drew.restaurantreview.service.JpaUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SecurityConfigTest {

    @Autowired
    private JpaUserDetailsService jpaUserDetailsService;
    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;
    @Autowired
    private AuthenticationProvider authenticationProvider;

    private SecurityConfig securityConfig;

    @BeforeEach
    void setUp() {
        securityConfig = new SecurityConfig(jpaUserDetailsService, jwtAuthFilter, authenticationProvider);
    }

    @Test
    void testConstructorInjection() {
        assertNotNull(securityConfig, "SecurityConfig constructor injection failed");
    }
}
