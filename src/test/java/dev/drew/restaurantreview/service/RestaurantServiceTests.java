package dev.drew.restaurantreview.service;

import dev.drew.restaurantreview.entity.RestaurantEntity;
import dev.drew.restaurantreview.exception.InsufficientPermissionException;
import dev.drew.restaurantreview.exception.RestaurantNotFoundException;
import dev.drew.restaurantreview.mapper.RestaurantMapper;
import dev.drew.restaurantreview.repository.RestaurantRepository;
import dev.drew.restaurantreview.repository.UserRepository;
import dev.drew.restaurantreview.service.impl.RestaurantServiceImpl;
import dev.drew.restaurantreview.util.SecurityUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.Restaurant;
import org.openapitools.model.RestaurantInput;
import dev.drew.restaurantreview.entity.UserEntity;
import dev.drew.restaurantreview.model.SecurityUser;
import org.openapitools.model.User.RoleEnum;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTests {

    @InjectMocks
    private RestaurantServiceImpl restaurantServiceImpl;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private RestaurantMapper restaurantMapper;

    @Mock
    private SecurityUser securityUser;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        securityUser = createSecurityUserWithRole(RoleEnum.ADMIN);
        SecurityUtils.setCurrentUser(securityUser);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
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
        restaurant.setUser(securityUser.getUserEntity());

        Restaurant restaurantResponse = new Restaurant().name(input.getName()).city(input.getCity());

        // Mock the repository and mapper calls
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(securityUser.getUserEntity()));
        when(restaurantMapper.toRestaurantEntity(any(RestaurantInput.class))).thenReturn(restaurant);
        when(restaurantRepository.save(any(RestaurantEntity.class))).thenReturn(restaurant);
        when(restaurantMapper.toRestaurant(any(RestaurantEntity.class))).thenReturn(restaurantResponse);

        // Call the service method
        Restaurant response = restaurantServiceImpl.addNewRestaurant(input);

        // Verify the response status
        assertEquals(input.getName(), response.getName());
        assertEquals(input.getCity(), response.getCity());
    }

    @Test
    void testGetAllRestaurants() {
        // Prepare expected data
        List<RestaurantEntity> restaurantEntities = new ArrayList<>();

        RestaurantEntity restaurantEntity1 = new RestaurantEntity();
        restaurantEntity1.setId(1L);
        restaurantEntity1.setName("Restaurant 1");
        restaurantEntities.add(restaurantEntity1);

        RestaurantEntity restaurantEntity2 = new RestaurantEntity();
        restaurantEntity2.setId(2L);
        restaurantEntity2.setName("Restaurant 2");
        restaurantEntities.add(restaurantEntity2);

        // Mock the repository call
        when(restaurantRepository.findAll()).thenReturn(restaurantEntities);

        // Call the service method
        List<Restaurant> response = restaurantServiceImpl.getAllRestaurants(null, null, null);

        // Verify the response status and data
        assertEquals(2, response.size());
    }

    @Test
    void testGetRestaurantById() {
        // Prepare expected data
        Long restaurantId = 1L;
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(restaurantId);
        restaurantEntity.setName("Restaurant 1");

        Restaurant expectedRestaurant = new Restaurant();
        expectedRestaurant.setId(restaurantId);
        expectedRestaurant.setName("Restaurant 1");

        // Mock the repository call
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurantEntity));

        // Mock the mapper call
        when(restaurantMapper.toRestaurant(restaurantEntity)).thenReturn(expectedRestaurant);

        // Call the service method
        Restaurant response = restaurantServiceImpl.getRestaurantById(restaurantId.intValue());

        // Verify the response status
        assertNotNull(response);
        assertEquals(expectedRestaurant.getId(), response.getId());
        assertEquals(expectedRestaurant.getName(), response.getName());
    }

    @Test
    void testUpdateRestaurantById() {
        // Prepare input data
        Long restaurantId = 1L;
        RestaurantInput updatedInput = new RestaurantInput().name("Updated Restaurant").city("Updated City");

        // Prepare expected data
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(restaurantId);
        restaurantEntity.setName("Restaurant 1");
        restaurantEntity.setUser(securityUser.getUserEntity());

        RestaurantEntity updatedEntity = new RestaurantEntity();
        updatedEntity.setId(restaurantId);
        updatedEntity.setName(updatedInput.getName());
        updatedEntity.setCity(updatedInput.getCity());
        updatedEntity.setUser(securityUser.getUserEntity());

        // Mock the repository and mapper calls
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurantEntity));
        when(restaurantMapper.toRestaurantEntity(any(RestaurantInput.class))).thenReturn(updatedEntity);
        when(restaurantRepository.save(any(RestaurantEntity.class))).thenReturn(updatedEntity);

        Restaurant updatedRestaurantResponse = new Restaurant().id(restaurantId).name(updatedInput.getName()).city(updatedInput.getCity());
        when(restaurantMapper.toRestaurant(any(RestaurantEntity.class))).thenReturn(updatedRestaurantResponse);

        // Call the service method
        Restaurant updatedRestaurant = restaurantServiceImpl.updateRestaurantById(restaurantId.intValue(), updatedInput);

        // Verify the response
        assertEquals(updatedInput.getName(), updatedRestaurant.getName());
        assertEquals(updatedInput.getCity(), updatedRestaurant.getCity());
    }

    @Test
    void testDeleteRestaurantByIdNotFound() {
        // Prepare expected data
        Long restaurantId = 1L;

        // Mock the repository call
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        // Verify the response status
        assertThrows(RestaurantNotFoundException.class, () -> restaurantServiceImpl.deleteRestaurantById(restaurantId.intValue()));

        verify(restaurantRepository, never()).deleteById(anyLong());
    }

    @Test
    void testDeleteRestaurantByIdForbidden() {
        // Prepare expected data
        Long restaurantId = 1L;
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(restaurantId);
        restaurantEntity.setName("Restaurant 1");
        restaurantEntity.setUserId(2L); // Different user ID

        // Change the current user to a non-admin role
        SecurityUser securityUser = createSecurityUserWithRole(RoleEnum.REVIEWER);
        Authentication mockAuthentication = new UsernamePasswordAuthenticationToken(
                securityUser, null, securityUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(mockAuthentication);

        // Mock the repository call
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurantEntity));

        assertThrows(InsufficientPermissionException.class, () -> restaurantServiceImpl.deleteRestaurantById(restaurantId.intValue()));

        verify(restaurantRepository, never()).deleteById(anyLong());
    }
}