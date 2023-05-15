//package dev.drew.restaurantreview.service;
//
//import dev.drew.restaurantreview.entity.RestaurantEntity;
//import dev.drew.restaurantreview.mapper.RestaurantMapper;
//import dev.drew.restaurantreview.repository.RestaurantRepository;
//import dev.drew.restaurantreview.repository.ReviewRepository;
//import dev.drew.restaurantreview.util.SecurityUtils;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.openapitools.model.Restaurant;
//import org.openapitools.model.RestaurantInput;
//import org.openapitools.model.RestaurantResponse;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import dev.drew.restaurantreview.entity.UserEntity;
//import dev.drew.restaurantreview.model.SecurityUser;
//import org.openapitools.model.User.RoleEnum;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class RestaurantServiceTests {
//
//    @InjectMocks
//    private RestaurantServiceImpl restaurantServiceImpl;
//
//    @Mock
//    private RestaurantRepository restaurantRepository;
//
//    @Mock
//    private RestaurantMapper restaurantMapper;
//
//    @Mock
//    private ReviewRepository reviewRepository;
//
//    @Mock
//    private SecurityUtils securityUtils;
//
//    @BeforeEach
//    void setUp() {
//        SecurityUser securityUser = createSecurityUserWithRole(RoleEnum.ADMIN);
//
//        Authentication mockAuthentication = new UsernamePasswordAuthenticationToken(
//                securityUser, null, securityUser.getAuthorities());
//
//        SecurityContextHolder.getContext().setAuthentication(mockAuthentication);
//    }
//
//    private SecurityUser createSecurityUserWithRole(RoleEnum role) {
//        UserEntity userEntity = new UserEntity();
//        userEntity.setId(1L);
//        userEntity.setUsername("testUser");
//        userEntity.setPassword("password");
//        userEntity.setRole(role);
//
//        return new SecurityUser(userEntity);
//    }
//
//    @Test
//    void testAddNewRestaurant() {
//
//        // Prepare input data and expected response
//        RestaurantInput input = new RestaurantInput().name("New Restaurant").city("New City");
//        RestaurantEntity restaurant = new RestaurantEntity();
//        restaurant.setName(input.getName());
//        restaurant.setCity(input.getCity());
//
//        RestaurantResponse restaurantResponse = new RestaurantResponse();
//        ResponseEntity<RestaurantResponse> responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(restaurantResponse);
//
//        // Mock the repository and mapper calls
//        when(restaurantMapper.toRestaurantEntity(any(RestaurantInput.class))).thenReturn(restaurant);
//        when(restaurantRepository.save(any(RestaurantEntity.class))).thenReturn(restaurant);
//
//        // Call the service method
//        ResponseEntity<RestaurantResponse> response = restaurantServiceImpl.addNewRestaurant(input);
//
//        // Verify the response status
//        assertEquals(responseEntity.getStatusCode(), response.getStatusCode());
//    }
//
//    @Test
//    void testGetAllRestaurants() {
//        // Prepare expected data
//        List<RestaurantEntity> restaurantEntities = new ArrayList<>();
//        RestaurantEntity restaurantEntity1 = new RestaurantEntity();
//        restaurantEntity1.setId(1L);
//        restaurantEntity1.setName("Restaurant 1");
//        restaurantEntities.add(restaurantEntity1);
//
//        RestaurantEntity restaurantEntity2 = new RestaurantEntity();
//        restaurantEntity2.setId(2L);
//        restaurantEntity2.setName("Restaurant 2");
//        restaurantEntities.add(restaurantEntity2);
//
//        // Mock the repository call
//        when(restaurantRepository.findAll()).thenReturn(restaurantEntities);
//
//        // Call the service method
//        ResponseEntity<List<Restaurant>> response = restaurantServiceImpl.getAllRestaurants(null, null, null);
//
//        // Verify the response status and data
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(2, response.getBody().size());
//    }
//
//    @Test
//    void testGetRestaurantById() {
//        // Prepare expected data
//        Long restaurantId = 1L;
//        RestaurantEntity restaurantEntity = new RestaurantEntity();
//        restaurantEntity.setId(restaurantId);
//        restaurantEntity.setName("Restaurant 1");
//
//        Restaurant expectedRestaurant = new Restaurant();
//        expectedRestaurant.setId(restaurantId);
//        expectedRestaurant.setName("Restaurant 1");
//
//        // Mock the repository call
//        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurantEntity));
//
//        // Mock the mapper call
//        when(restaurantMapper.toRestaurant(restaurantEntity)).thenReturn(expectedRestaurant);
//
//        // Call the service method
//        ResponseEntity<Restaurant> response = restaurantServiceImpl.getRestaurantById(restaurantId.intValue());
//
//        // Verify the response status
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//
//        // Verify the response body
//        assertNotNull(response.getBody());
//        assertEquals(expectedRestaurant.getId(), response.getBody().getId());
//        assertEquals(expectedRestaurant.getName(), response.getBody().getName());
//    }
//
//    @Test
//    void testDeleteRestaurantByIdNotFound() {
//        // Prepare expected data
//        Long restaurantId = 1L;
//
//        // Mock the repository call
//        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());
//
//        // Call the service method
//        ResponseEntity<Void> response = restaurantServiceImpl.deleteRestaurantById(restaurantId.intValue());
//
//        // Verify the response status
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//
//        // Verify the delete method was not called on the repository
//        verify(restaurantRepository, never()).deleteById(anyLong());
//    }
//
//    @Test
//    void testDeleteRestaurantByIdForbidden() {
//        // Prepare expected data
//        Long restaurantId = 1L;
//        RestaurantEntity restaurantEntity = new RestaurantEntity();
//        restaurantEntity.setId(restaurantId);
//        restaurantEntity.setName("Restaurant 1");
//        restaurantEntity.setUserId(2L); // Different user ID
//
//        // Mock the repository call
//        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurantEntity));
//
//        // Change the current user to a non-admin role
//        SecurityUser securityUser = createSecurityUserWithRole(RoleEnum.REVIEWER);
//        Authentication mockAuthentication = new UsernamePasswordAuthenticationToken(
//                securityUser, null, securityUser.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(mockAuthentication);
//
//        // Call the service method
//        ResponseEntity<Void> response = restaurantServiceImpl.deleteRestaurantById(restaurantId.intValue());
//
//        // Verify the response status
//        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
//
//        // Verify the delete method was not called on the repository
//        verify(restaurantRepository, never()).deleteById(anyLong());
//    }
//}