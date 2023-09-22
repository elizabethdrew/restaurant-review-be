package dev.drew.restaurantreview.controller;

import jakarta.validation.Valid;
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
@RequestMapping("/api/v1/search")
@PreAuthorize("permitAll()")
public class SearchController  {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getSearch(
            @Valid @RequestParam(value = "query", required = false) String query,
            @ParameterObject final Pageable pageable) {
        List<Restaurant> result = searchService.searchRestaurant(query, pageable);
        return ResponseEntity.ok(result);
    }
}