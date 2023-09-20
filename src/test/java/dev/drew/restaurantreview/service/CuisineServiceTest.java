package dev.drew.restaurantreview.service;

import dev.drew.restaurantreview.entity.CuisineEntity;
import dev.drew.restaurantreview.entity.RestaurantEntity;
import dev.drew.restaurantreview.exception.*;
import dev.drew.restaurantreview.repository.CuisineRepository;
import dev.drew.restaurantreview.service.impl.CuisineServiceImpl;

import dev.drew.restaurantreview.util.SecurityUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CuisineServiceTest {

    @Mock
    private CuisineRepository cuisineRepository;

    @InjectMocks
    private CuisineServiceImpl cuisineService;

    @Test
    void getAllCuisineNames() {
        CuisineEntity cuisine1 = new CuisineEntity();
        cuisine1.setName("Italian");
        CuisineEntity cuisine2 = new CuisineEntity();
        cuisine2.setName("Mexican");

        when(cuisineRepository.findAll()).thenReturn(Arrays.asList(cuisine1, cuisine2));

        List<String> cuisineNames = cuisineService.getAllCuisineNames();

        assertEquals(2, cuisineNames.size());
        assertTrue(cuisineNames.contains("Italian"));
        assertTrue(cuisineNames.contains("Mexican"));
    }

    @Test
    void addCuisine() {
        String name = "Italian";
        when(cuisineRepository.findByName(name)).thenReturn(Optional.empty());
        when(cuisineRepository.save(any(CuisineEntity.class))).thenAnswer(i -> i.getArgument(0));

        CuisineEntity addedCuisine = cuisineService.addCuisine(name);

        assertEquals(name, addedCuisine.getName());
    }

    @Test
    void addCuisine_duplicate() {
        String name = "Italian";
        when(cuisineRepository.findByName(name)).thenReturn(Optional.of(new CuisineEntity()));

        assertThrows(DuplicateCuisineException.class, () -> cuisineService.addCuisine(name));
    }

    @Test
    public void testDeleteCuisine() {
        String cuisineName = "Italian";

        // Mock a cuisine entity to be returned by the repository
        CuisineEntity mockCuisine = new CuisineEntity();
        mockCuisine.setName(cuisineName);

        when(cuisineRepository.findByName(cuisineName)).thenReturn(Optional.of(mockCuisine));

        // Mocking the SecurityUtils.isAdmin() method to return true
        try (MockedStatic<SecurityUtils> utilities = mockStatic(SecurityUtils.class)) {
            utilities.when(SecurityUtils::isAdmin).thenReturn(true);

            cuisineService.deleteCuisine(cuisineName);

            verify(cuisineRepository).delete(mockCuisine);
        }
    }


    @Test
    void deleteCuisine_notFound() {
        String name = "Italian";
        when(cuisineRepository.findByName(name)).thenReturn(Optional.empty());

        assertThrows(CuisineNotFoundException.class, () -> cuisineService.deleteCuisine(name));
    }

    @Test
    public void testDeleteCuisine_NotAnAdmin() {
        String cuisineName = "Italian";
        CuisineEntity mockCuisine = new CuisineEntity();
        mockCuisine.setName(cuisineName);

        when(cuisineRepository.findByName(cuisineName)).thenReturn(Optional.of(mockCuisine));

        // Mocking the SecurityUtils.isAdmin() method to return false
        try (MockedStatic<SecurityUtils> utilities = mockStatic(SecurityUtils.class)) {
            utilities.when(SecurityUtils::isAdmin).thenReturn(false);

            assertThrows(InsufficientPermissionException.class, () -> {
                cuisineService.deleteCuisine(cuisineName);
            });
        }
    }

    @Test
    public void testDeleteCuisine_CuisineReferencedByRestaurant() {
        String cuisineName = "Italian";
        CuisineEntity mockCuisine = new CuisineEntity();
        mockCuisine.setName(cuisineName);
        mockCuisine.setRestaurants(Collections.singletonList(new RestaurantEntity())); // Set a mock restaurant to indicate it's referenced

        when(cuisineRepository.findByName(cuisineName)).thenReturn(Optional.of(mockCuisine));

        try (MockedStatic<SecurityUtils> utilities = mockStatic(SecurityUtils.class)) {
            utilities.when(SecurityUtils::isAdmin).thenReturn(true);

            assertThrows(CuisineReferencedByRestaurantException.class, () -> {
                cuisineService.deleteCuisine(cuisineName);
            });
        }
    }

}