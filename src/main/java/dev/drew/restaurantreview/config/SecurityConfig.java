package dev.drew.restaurantreview.config;

import dev.drew.restaurantreview.service.JpaUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JpaUserDetailsService jpaUserDetailsService;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    // Configure the security settings for the application, including authentication and authorization rules
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection
                .authorizeHttpRequests()
                // Allow unauthenticated access to the following endpoints
                .requestMatchers(HttpMethod.GET,"/api/v1/restaurants").permitAll()
                .requestMatchers(HttpMethod.GET,"/api/v1/restaurants/**" ).permitAll()
                .requestMatchers(HttpMethod.GET,"/api/v1/reviews").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/reviews/**" ).permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/user" ).permitAll()
                // Require authentication for all other requests
                .anyRequest().authenticated()
                // Set session management
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                // Configure the response headers to allow frame options from the same origin
                .headers(headers -> headers.frameOptions().sameOrigin())
                // Enable basic HTTP authentication
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
