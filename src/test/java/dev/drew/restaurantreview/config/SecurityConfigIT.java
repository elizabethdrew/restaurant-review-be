package dev.drew.restaurantreview.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.drew.restaurantreview.entity.RestaurantEntity;
import dev.drew.restaurantreview.entity.ReviewEntity;
import dev.drew.restaurantreview.entity.SecurityUser;
import dev.drew.restaurantreview.entity.UserEntity;
import dev.drew.restaurantreview.repository.RestaurantRepository;
import dev.drew.restaurantreview.repository.ReviewRepository;
import dev.drew.restaurantreview.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Nested;
import org.mockito.Mockito;
import org.openapitools.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.openapitools.model.User.RoleEnum.ADMIN;
import static org.openapitools.model.User.RoleEnum.REVIEWER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantRepository restaurantRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ReviewRepository reviewRepository;

    private RestaurantEntity restaurantEntity;
    private UserEntity userEntity;
    private ReviewEntity reviewEntity;

    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;

    public SecurityConfigIT() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    private SecurityUser createSecurityUserWithRole(User.RoleEnum role) {
        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("adminUser");
        userEntity.setPassword(passwordEncoder.encode("password"));
        userEntity.setRole(role);

        return new SecurityUser(userEntity);
    }

    // Set up the test environment before each test
    @BeforeEach
    public void setUp() {
        restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(1L);
        restaurantEntity.setName("Test Restaurant");
        restaurantEntity.setCity("City");

        reviewEntity = new ReviewEntity();
        reviewEntity.setId(1L);
        reviewEntity.setRestaurantId(1L);
        reviewEntity.setUserId(1L);
        reviewEntity.setRating(4.0f);
        reviewEntity.setComment("Great food!");

        userEntity = new UserEntity();
        userEntity.setId(2L);
        userEntity.setUsername("REVIEWER");
        userEntity.setPassword(passwordEncoder.encode("password"));
        userEntity.setRole(REVIEWER);

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurantEntity));
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(reviewEntity));

    }

    // Test accessing public endpoints without authentication
    @Nested
    class WithoutAuthentication {

        @Test
        public void testGetRestaurants() throws Exception {
            mockMvc.perform(get("/restaurants")).andExpect(status().isOk());
        }

        @Test
        public void testGetRestaurantById() throws Exception {
            mockMvc.perform(get("/restaurants/1")).andExpect(status().isOk());
        }

        @Test
        public void testGetReviews() throws Exception {
            mockMvc.perform(get("/reviews")).andExpect(status().isOk());
        }

        @Test
        public void testGetReviewById() throws Exception {
            mockMvc.perform(get("/reviews/1")).andExpect(status().isOk());
        }

        @Test
        public void testPostUser() throws Exception {
            // Create a new user to be added
            UserEntity newUser = new UserEntity();
            newUser.setUsername("newUser");
            newUser.setPassword(passwordEncoder.encode("password"));
            newUser.setRole(UserEntity.RoleEnum.REVIEWER);

            // Convert the newUser object to a JSON string
            ObjectMapper objectMapper = new ObjectMapper();
            String newUserJson = objectMapper.writeValueAsString(newUser);

            mockMvc.perform(post("/user")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(newUserJson))
                    .andExpect(status().isCreated());
        }

    }



    // Test accessing protected endpoints without authentication
    @Nested
    class ProtectedWithoutAuthentication {
        @Test
        public void testGetUserById() throws Exception {
            mockMvc.perform(get("/user/1")).andExpect(status().isUnauthorized());
        }

        @Test
        public void testDeleteUserById() throws Exception {
            mockMvc.perform(delete("/user/1")).andExpect(status().isUnauthorized());
        }

        @Test
        public void testPutUserById() throws Exception {
            // Create an object to represent the updated user data
            UserEntity updatedUser = new UserEntity();
            updatedUser.setId(1L);
            updatedUser.setUsername("updatedUsername");
            updatedUser.setPassword("updatedPassword");
            updatedUser.setRole(UserEntity.RoleEnum.ADMIN);

            // Convert the updatedUser object to a JSON string
            ObjectMapper objectMapper = new ObjectMapper();
            String updatedUserJson = objectMapper.writeValueAsString(updatedUser);

            mockMvc.perform(put("/user/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(updatedUserJson))
                    .andExpect(status().isUnauthorized());
        }
    }

    // Test accessing protected endpoints with authentication
    @Nested
    class ProtectedWithAuthentication {

        @BeforeEach
        public void setUpProtectedWithAuthentication() {

            // Create SecurityUser and set it as the principal in the Authentication object
            SecurityUser securityUser = createSecurityUserWithRole(ADMIN);
            Authentication authentication = Mockito.mock(Authentication.class);
            when(authentication.getPrincipal()).thenReturn(securityUser);
            when(authentication.isAuthenticated()).thenReturn(true);

            // Set the Authentication object in the SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
        @Test
        @WithMockUser
        public void testAuthenticatedGetUserById() throws Exception {
            mockMvc.perform(get("/user/1")).andExpect(status().isOk());
        }

        @Test
        @WithMockUser
        public void testAuthenticatedDeleteUserById() throws Exception {
            mockMvc.perform(delete("/user/1")).andExpect(status().isNoContent());
        }

        @Test
        @WithMockUser
        public void testAuthenticatedPutUserById() throws Exception {
            // Create an object to represent the updated user data
            UserEntity updatedUser = new UserEntity();
            updatedUser.setId(1L);
            updatedUser.setUsername("updatedUsername");
            updatedUser.setPassword("updatedPassword");
            updatedUser.setRole(UserEntity.RoleEnum.ADMIN);

            // Convert the updatedUser object to a JSON string
            ObjectMapper objectMapper = new ObjectMapper();
            String updatedUserJson = objectMapper.writeValueAsString(updatedUser);

            mockMvc.perform(put("/user/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(updatedUserJson))
                    .andExpect(status().isOk());
        }
    }
}