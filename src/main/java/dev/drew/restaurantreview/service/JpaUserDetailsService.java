package dev.drew.restaurantreview.service;

import dev.drew.restaurantreview.entity.SecurityUser;
import dev.drew.restaurantreview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// JpaUserDetailsService is responsible for loading user-specific data by their username for the authentication process.
@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    // Logger to log information and warnings
    private static final Logger LOGGER = LoggerFactory.getLogger(JpaUserDetailsService.class);

    // UserRepository to interact with the database and manage UserEntity objects.
    private final UserRepository userRepository;

    // Load user details by their username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("Searching for user with username: {}", username);
        return userRepository.findByUsername(username)
                .map(user -> {
                    LOGGER.info("Found user: {}", user);
                    return new SecurityUser(user); // Create a new SecurityUser object with the retrieved user
                })
                .orElseThrow(() -> {
                    String message = "Username not found: " + username;
                    LOGGER.warn(message);
                    return new UsernameNotFoundException(message);
                });
    }
}