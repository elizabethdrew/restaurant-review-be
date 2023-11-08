package dev.drew.restaurantreview.auth;

import dev.drew.restaurantreview.service.JpaUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.validation.constraints.NotNull;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final JpaUserDetailsService jpaUserDetailsService;
    private final JwtBlacklistRepository jwtBlacklistRepository;

    // Filter incoming requests and extract JWT token from the Authorization header
    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String username;

        log.info("Starting Authentication Process...");

        // If the Authorization header is missing or does not start with "Bearer ", proceed with the filter chain
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.info("BAD");
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the JWT token and username
        jwtToken = authHeader.substring(7);
        log.info("JWT Extracted");
        username = jwtService.extractUsername(jwtToken);
        log.info("Username Extracted");

        if (jwtBlacklistRepository.findByToken(jwtToken).isPresent()) {
            log.info("JWT Blacklisted - Log In Again");
            // Reject the request or handle it accordingly
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token is blacklisted");
            return;
        }

        // If the username is not null and there is no existing authentication, validate the token and set the authentication
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){

            log.info("Validating Username...");
            UserDetails userDetails = jpaUserDetailsService.loadUserByUsername(username);

            log.info("Validating Token...");
            if (jwtService.isTokenValid(jwtToken, userDetails)) {

                log.info("Token Valid");
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
