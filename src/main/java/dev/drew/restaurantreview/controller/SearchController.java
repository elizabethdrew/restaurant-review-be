package dev.drew.restaurantreview.controller;

import jakarta.validation.Valid;
import org.openapitools.api.SearchApi;
import org.openapitools.model.PaginatedRestaurantResponse;
import org.openapitools.model.Restaurant;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.drew.restaurantreview.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
@RequestMapping("/api")
@PreAuthorize("permitAll()")
public class SearchController  {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/v1/search")
    public ResponseEntity<List<Restaurant>> getSearchV1(
            @Valid @RequestParam(value = "query", required = false) String query,
            @ParameterObject final Pageable pageable) {
        List<Restaurant> result = searchService.searchRestaurantV1(query, pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/v2/search")
    public ResponseEntity<PaginatedRestaurantResponse> getSearchV2(
            @Valid @RequestParam(value = "query", required = false) String query,
            @ParameterObject final Pageable pageable) {
        PaginatedRestaurantResponse result = searchService.searchRestaurantV2(query, pageable);
        return ResponseEntity.ok(result);
    }
}