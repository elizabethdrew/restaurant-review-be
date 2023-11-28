package dev.drew.restaurantreview.controller.unit;

import dev.drew.restaurantreview.controller.SearchController;
import dev.drew.restaurantreview.exception.GlobalExceptionHandler;
import dev.drew.restaurantreview.exception.NoResultsFoundException;
import dev.drew.restaurantreview.service.SearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.Restaurant;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class SearchControllerTest {

    @InjectMocks
    private SearchController searchController;

    @Mock
    private SearchService searchService;

    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(searchController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testGetSearch() throws Exception {
        List<Restaurant> restaurants = Arrays.asList(
                new Restaurant().id(1L).name("Restaurant 1").city("City 1"),
                new Restaurant().id(2L).name("Restaurant 2").city("City 2")
        );

        Pageable pageable = PageRequest.of(0, 20);
        when(searchService.searchRestaurantV1("Restaurant", pageable)).thenReturn(restaurants);

        mockMvc.perform(get("/api/v1/search")
                        .param("query", "Restaurant")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetSearchNoResults() throws Exception {
        List<Restaurant> restaurants = Arrays.asList(
                new Restaurant().id(1L).name("Restaurant 1").city("City 1"),
                new Restaurant().id(2L).name("Restaurant 2").city("City 2")
        );

        String query = "nonExistentTerm";
        Pageable pageable = PageRequest.of(0, 20);
        when(searchService.searchRestaurantV1(query, pageable)).thenThrow(new NoResultsFoundException("No restaurants found for the given query: " + query));

        mockMvc.perform(get("/api/v1/search")
                        .param("query", query)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}