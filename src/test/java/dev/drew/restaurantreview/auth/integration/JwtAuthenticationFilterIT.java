package dev.drew.restaurantreview.auth.integration;

import dev.drew.restaurantreview.auth.JwtService;
import dev.drew.restaurantreview.service.JpaUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class JwtAuthenticationFilterIT {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private JpaUserDetailsService jpaUserDetailsService;

    @Test
    @WithMockUser
    public void testValidJwtToken() throws Exception {
        MockMvc mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

        UserDetails mockUserDetails = mock(UserDetails.class);
        when(mockUserDetails.getUsername()).thenReturn("admin");

        String mockToken = "mockTokenString";
        when(jwtService.generateToken(mockUserDetails)).thenReturn(mockToken);

        String username = "admin";

        when(jwtService.extractUsername(mockToken)).thenReturn(username);
        when(jwtService.isTokenValid(anyString(), any(UserDetails.class))).thenReturn(true);
        when(jpaUserDetailsService.loadUserByUsername(username)).thenReturn(mockUserDetails);

        mvc.perform(get("/someEndpoint")
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testInvalidJwtToken() throws Exception {
        MockMvc mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

        String invalidToken = "invalidMockToken";

        when(jwtService.extractUsername(invalidToken)).thenReturn(null);

        mvc.perform(get("/someEndpoint")
                        .header("Authorization", "Bearer " + invalidToken))
                .andExpect(status().isForbidden());
    }

}