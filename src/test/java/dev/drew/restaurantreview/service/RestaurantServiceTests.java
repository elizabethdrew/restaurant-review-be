package dev.drew.restaurantreview.service;

import dev.drew.restaurantreview.entity.CuisineEntity;
import dev.drew.restaurantreview.entity.FavouriteEntity;
import dev.drew.restaurantreview.entity.RestaurantEntity;
import dev.drew.restaurantreview.exception.*;

import dev.drew.restaurantreview.mapper.RestaurantMapper;
import dev.drew.restaurantreview.repository.CuisineRepository;
import dev.drew.restaurantreview.repository.FavouriteRepository;
import dev.drew.restaurantreview.repository.RestaurantRepository;
import dev.drew.restaurantreview.repository.UserRepository;

import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
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

    @Mock
    private CuisineRepository cuisineRepository;

    @Mock
    private FavouriteRepository favouriteRepository;

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
    void testValidateRestaurantInputInvalidPriceRange() {
        RestaurantInput input = new RestaurantInput().priceRange(0); // Invalid price range
        assertThrows(InvalidInputException.class, () -> restaurantServiceImpl.validateRestaurantInput(input));

        input.setPriceRange(4); // Another invalid price range
        assertThrows(InvalidInputException.class, () -> restaurantServiceImpl.validateRestaurantInput(input));
    }

    @Test
    void testAddNewRestaurant() {

        List<String> cuisines = Arrays.asList("British", "American");

        RestaurantInput input = new RestaurantInput().name("New Restaurant").city("New City").priceRange(2).cuisines(cuisines);

        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setName(input.getName());
        restaurant.setCity(input.getCity());

        Restaurant restaurantResponse = new Restaurant().name(input.getName()).city(input.getCity());

        when(restaurantRepository.findByNameAndCity(anyString(), anyString())).thenReturn(Optional.empty());
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(securityUser.getUserEntity()));
        when(restaurantMapper.toRestaurantEntity(any(RestaurantInput.class))).thenReturn(restaurant);
        when(restaurantRepository.save(any(RestaurantEntity.class))).thenReturn(restaurant);
        when(restaurantMapper.toRestaurant(any(RestaurantEntity.class))).thenReturn(restaurantResponse);
        when(cuisineRepository.findByName("British")).thenReturn(Optional.of(new CuisineEntity()));
        when(cuisineRepository.findByName("American")).thenReturn(Optional.of(new CuisineEntity()));

        Restaurant response = restaurantServiceImpl.addNewRestaurant(input);

        assertEquals(input.getName(), response.getName());
        assertEquals(input.getCity(), response.getCity());
    }

    @Test
    void testAddNewRestaurantAlreadyExists() {
        List<String> cuisines = Arrays.asList("British", "American");
        RestaurantInput input = new RestaurantInput().name("New Restaurant").city("New City").priceRange(2).cuisines(cuisines);

        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setName(input.getName());
        restaurant.setCity(input.getCity());

        when(restaurantRepository.findByNameAndCity(anyString(), anyString())).thenReturn(Optional.of(new RestaurantEntity()));

        assertThrows(DuplicateRestaurantException.class, () -> restaurantServiceImpl.addNewRestaurant(input));
    }

    @Test
    void testAddNewRestaurantWithInvalidCuisine() {
        List<String> cuisines = Arrays.asList("InvalidCuisine");
        RestaurantInput input = new RestaurantInput().name("New Restaurant").city("New City").priceRange(2).cuisines(cuisines);

        when(restaurantRepository.findByNameAndCity(anyString(), anyString())).thenReturn(Optional.empty());
        when(cuisineRepository.findByName("InvalidCuisine")).thenReturn(Optional.empty());

        assertThrows(CuisineNotFoundException.class, () -> restaurantServiceImpl.addNewRestaurant(input));
    }

    @Test
    void testGetAllRestaurants() {

        List<RestaurantEntity> restaurantEntities = new ArrayList<>();

        RestaurantEntity restaurantEntity1 = new RestaurantEntity();
        restaurantEntity1.setId(1L);
        restaurantEntity1.setName("Restaurant 1");
        restaurantEntities.add(restaurantEntity1);

        RestaurantEntity restaurantEntity2 = new RestaurantEntity();
        restaurantEntity2.setId(2L);
        restaurantEntity2.setName("Restaurant 2");
        restaurantEntities.add(restaurantEntity2);

        Page<RestaurantEntity> page = new PageImpl<>(restaurantEntities);

        // Mocking favourites check
        FavouriteEntity favourite = new FavouriteEntity();
        when(favouriteRepository.findByRestaurantAndUser(any(RestaurantEntity.class), any(UserEntity.class)))
                .thenReturn(Optional.of(favourite));

        // Mock the repository call
        when(restaurantRepository.findAll(
                any(Specification.class),
                any(Pageable.class)
        )).thenReturn(page);

        // Mocking Restaurant transformation
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setId(1L);
        restaurant1.setName("Restaurant 1");
        restaurant1.setIsFavourite(true); // Since we are returning a favourite

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(2L);
        restaurant2.setName("Restaurant 2");
        restaurant2.setIsFavourite(true); // Since we are returning a favourite

        when(restaurantMapper.toRestaurant(restaurantEntity1)).thenReturn(restaurant1);
        when(restaurantMapper.toRestaurant(restaurantEntity2)).thenReturn(restaurant2);

        // Call the service method
        List<Restaurant> response = restaurantServiceImpl.getAllRestaurants(null, null, null, null, null, false, PageRequest.of(0, 20));

        // Verify the response status and data
        assertEquals(2, response.size());
        assertTrue(response.get(0).getIsFavourite()); // Verify favourite status
        assertTrue(response.get(1).getIsFavourite()); // Verify favourite status
    }

    @Test
    void testGetRestaurantById() {

        Long restaurantId = 1L;
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(restaurantId);
        restaurantEntity.setName("Restaurant 1");

        Restaurant expectedRestaurant = new Restaurant();
        expectedRestaurant.setId(restaurantId);
        expectedRestaurant.setName("Restaurant 1");

        // Mock the repository call using any() for the Specification
        when(restaurantRepository.findOne(any(Specification.class)))
                .thenReturn(Optional.of(restaurantEntity));

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
        Long restaurantId = 1L;
        List<String> cuisines = Arrays.asList("Cuisine");
        RestaurantInput updatedInput = new RestaurantInput().name("New Restaurant").city("New City").priceRange(2).cuisines(cuisines);

        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(restaurantId);
        restaurantEntity.setName("Restaurant 1");
        restaurantEntity.setUser(securityUser.getUserEntity());

        RestaurantEntity updatedEntity = new RestaurantEntity();
        updatedEntity.setId(restaurantId);
        updatedEntity.setName(updatedInput.getName());
        updatedEntity.setCity(updatedInput.getCity());
        updatedEntity.setUser(securityUser.getUserEntity());

        when(restaurantRepository.findOne(any(Specification.class))).thenReturn(Optional.of(restaurantEntity));
        when(restaurantRepository.save(any(RestaurantEntity.class))).thenReturn(updatedEntity);
        when(cuisineRepository.findByName("Cuisine")).thenReturn(Optional.of(new CuisineEntity()));
        when(restaurantMapper.toRestaurantEntity(any(RestaurantInput.class))).thenReturn(updatedEntity);

        Restaurant updatedRestaurantResponse = new Restaurant().id(restaurantId).name(updatedInput.getName()).city(updatedInput.getCity());
        when(restaurantMapper.toRestaurant(any(RestaurantEntity.class))).thenReturn(updatedRestaurantResponse);

        // Mock static method and ensure its scope encompasses the service method call
        try (MockedStatic<SecurityUtils> theClass = Mockito.mockStatic(SecurityUtils.class)) {
            theClass.when(() -> SecurityUtils.isAdminOrOwner(any(RestaurantEntity.class), any())).thenReturn(true);

            // 3. Execute the method
            Restaurant updatedRestaurant = restaurantServiceImpl.updateRestaurantById(restaurantId.intValue(), updatedInput);

            // 4. Assert results
            assertEquals(updatedInput.getName(), updatedRestaurant.getName());
            assertEquals(updatedInput.getCity(), updatedRestaurant.getCity());
        }
    }

    @Test
    void testDeleteRestaurantByIdNotFound() {
        // Prepare expected data
        Long restaurantId = 1L;

        // Mock the repository call
        when(restaurantRepository.findOne(any(Specification.class))).thenReturn(Optional.empty());

        // Verify the response status
        assertThrows(RestaurantNotFoundException.class, () -> restaurantServiceImpl.deleteRestaurantById(restaurantId.intValue()));

        // Ensure the save method (which indicates deletion in this context) wasn't called
        verify(restaurantRepository, never()).save(any(RestaurantEntity.class));
    }

    @Test
    void testDeleteRestaurantByIdForbidden() {
        // Prepare expected data
        Long restaurantId = 1L;
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(restaurantId);
        restaurantEntity.setName("Restaurant 1");
        restaurantEntity.setUserId(2L); // Different user ID

        // Mock the repository call
        when(restaurantRepository.findOne(any(Specification.class))).thenReturn(Optional.of(restaurantEntity));

        // Mock static method isAdminOrOwner
        try (MockedStatic<SecurityUtils> theMock = Mockito.mockStatic(SecurityUtils.class)) {
            theMock.when(() -> SecurityUtils.isAdminOrOwner(any(RestaurantEntity.class), any())).thenReturn(false);

            // Assert exception and verify methods
            assertThrows(InsufficientPermissionException.class, () -> restaurantServiceImpl.deleteRestaurantById(restaurantId.intValue()));

            // Ensure the save method (which indicates deletion in this context) wasn't called
            verify(restaurantRepository, never()).save(any(RestaurantEntity.class));
        }
    }

//
//    @Test
//    void testDeleteRestaurantByIdNotFound() {
//        // Prepare expected data
//        Long restaurantId = 1L;
//
//        // Mock the repository call
//        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());
//
//        // Verify the response status
//        assertThrows(RestaurantNotFoundException.class, () -> restaurantServiceImpl.deleteRestaurantById(restaurantId.intValue()));
//
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
//        // Change the current user to a non-admin role
//        SecurityUser securityUser = createSecurityUserWithRole(RoleEnum.REVIEWER);
//        Authentication mockAuthentication = new UsernamePasswordAuthenticationToken(
//                securityUser, null, securityUser.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(mockAuthentication);
//
//        // Mock the repository call
//        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurantEntity));
//
//        assertThrows(InsufficientPermissionException.class, () -> restaurantServiceImpl.deleteRestaurantById(restaurantId.intValue()));
//
//        verify(restaurantRepository, never()).deleteById(anyLong());
//    }
}