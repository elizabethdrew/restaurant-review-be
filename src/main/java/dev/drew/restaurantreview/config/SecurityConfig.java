package dev.drew.restaurantreview.config;

import dev.drew.restaurantreview.service.JpaUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    private final JpaUserDetailsService jpaUserDetailsService;

    public SecurityConfig(JpaUserDetailsService jpaUserDetailsService) {
        this.jpaUserDetailsService = jpaUserDetailsService;
    }

    // Declare a BCryptPasswordEncoder bean for password encoding
    @Bean
    public static BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Configure the security settings for the application, including authentication and authorization rules
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf().disable() // Disable CSRF protection
                .authorizeHttpRequests(auth -> auth
                        // Allow unauthenticated access to the following endpoints
                        .requestMatchers(HttpMethod.GET,"/api/v1/restaurants").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/restaurants/**" ).permitAll()
                        .requestMatchers(HttpMethod.GET,"/reviews").permitAll()
                        .requestMatchers(HttpMethod.GET, "/reviews/**" ).permitAll()
                        .requestMatchers(HttpMethod.POST, "/user" ).permitAll()
                        // Require authentication for all other requests
                        .anyRequest().authenticated())
                // Set the user details service for authentication
                .userDetailsService(jpaUserDetailsService)
                // Configure the response headers to allow frame options from the same origin
                .headers(headers -> headers.frameOptions().sameOrigin())
                // Enable basic HTTP authentication
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
