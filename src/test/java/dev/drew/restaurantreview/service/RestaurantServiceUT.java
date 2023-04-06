package dev.drew.restaurantreview.service;

import dev.drew.restaurantreview.entity.RestaurantEntity;
import dev.drew.restaurantreview.mapper.RestaurantMapper;
import dev.drew.restaurantreview.repository.RestaurantRepository;
import dev.drew.restaurantreview.repository.ReviewRepository;
import dev.drew.restaurantreview.util.SecurityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.RestaurantInput;
import org.openapitools.model.RestaurantResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import dev.drew.restaurantreview.entity.UserEntity;
import dev.drew.restaurantreview.entity.SecurityUser;
import org.openapitools.model.User.RoleEnum;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceUT {

    @InjectMocks
    private RestaurantServiceImpl restaurantServiceImpl;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private RestaurantMapper restaurantMapper;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private SecurityUtils securityUtils;

    @BeforeEach
    void setUp() {
        SecurityUser securityUser = createSecurityUserWithRole(RoleEnum.ADMIN);

        Authentication mockAuthentication = new UsernamePasswordAuthenticationToken(
                securityUser, null, securityUser.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(mockAuthentication);
    }

    private SecurityUser createSecurityUserWithRole(RoleEnum role) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("testUser");
        userEntity.setPassword("password");
        userEntity.setRole(role);

        return new SecurityUser(userEntity);
    }

    @Test
    void testAddNewRestaurant() {

        // Prepare input data and expected response
        RestaurantInput input = new RestaurantInput().name("New Restaurant").city("New City");
        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setName(input.getName());
        restaurant.setCity(input.getCity());

        RestaurantResponse restaurantResponse = new RestaurantResponse();
        ResponseEntity<RestaurantResponse> responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(restaurantResponse);

        // Mock the repository and mapper calls
        when(restaurantMapper.toRestaurantEntity(any(RestaurantInput.class))).thenReturn(restaurant);
        when(restaurantRepository.save(any(RestaurantEntity.class))).thenReturn(restaurant);

        // Call the service method
        ResponseEntity<RestaurantResponse> response = restaurantServiceImpl.addNewRestaurant(input);

        // Verify the response status
        assertEquals(responseEntity.getStatusCode(), response.getStatusCode());
    }
}