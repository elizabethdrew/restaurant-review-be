package dev.drew.restaurantreview.auth;

import dev.drew.restaurantreview.service.JpaUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// Configuration class for authentication and authorization settings
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    // UserDetailsService implementation for JPA-based user details retrieval
    private final JpaUserDetailsService jpaUserDetailsService;

    // Declare an AuthenticationProvider bean to handle authentication using JpaUserDetailsService and password encoding
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(jpaUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // Declare an AuthenticationManager bean for managing authentication within the application
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Declare a BCryptPasswordEncoder bean for password encoding
    @Bean
    public static BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
