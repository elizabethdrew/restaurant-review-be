package dev.drew.restaurantreview.service;

import dev.drew.restaurantreview.entity.SecurityUser;
import dev.drew.restaurantreview.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JpaUserDetailsService.class);

    private final UserRepository userRepository;

    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("Searching for user with username: {}", username);
        return userRepository.findByUsername(username)
                .map(user -> {
                    LOGGER.info("Found user: {}", user);
                    return new SecurityUser(user);
                })
                .orElseThrow(() -> {
                    String message = "Username not found: " + username;
                    LOGGER.warn(message);
                    return new UsernameNotFoundException(message);
                });
    }
}
