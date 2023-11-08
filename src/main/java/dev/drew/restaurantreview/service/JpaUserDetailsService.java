package dev.drew.restaurantreview.service;

import dev.drew.restaurantreview.model.SecurityUser;
import dev.drew.restaurantreview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// JpaUserDetailsService is responsible for loading user-specific data by their username for the authentication process.
@Slf4j
@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    // UserRepository to interact with the database and manage UserEntity objects.
    private final UserRepository userRepository;

    // Load user details by their username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Searching for user with username: {}", username);
        return userRepository.findByUsername(username)
                .map(user -> {
                    log.info("Found user: {}", user);
                    return new SecurityUser(user); // Create a new SecurityUser object with the retrieved user
                })
                .orElseThrow(() -> {
                    String message = "Username not found: " + username;
                    log.warn(message);
                    return new UsernameNotFoundException(message);
                });
    }
}