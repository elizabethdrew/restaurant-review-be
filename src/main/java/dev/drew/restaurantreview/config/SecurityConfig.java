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

    @Bean
    public static BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    // Configure to use just basic
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                    .csrf().disable()
                .authorizeRequests(auth -> auth
                        .requestMatchers("/restaurants").permitAll()
                        .requestMatchers(HttpMethod.GET,"/restaurants/**" ).permitAll()
                        .requestMatchers(HttpMethod.POST,"/restaurants/**" ).permitAll()
                        .requestMatchers("/reviews").permitAll()
                        .requestMatchers(HttpMethod.GET, "/reviews/**" ).permitAll()
                        .requestMatchers(HttpMethod.POST, "/reviews/**" ).permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/**" ).permitAll()
                        .anyRequest().authenticated())
                    .userDetailsService(jpaUserDetailsService)
                    .headers(headers -> headers.frameOptions().sameOrigin())
                    .httpBasic(Customizer.withDefaults())
                    .build();
    }

}
