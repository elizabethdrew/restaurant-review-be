package dev.drew.restaurantreview.service.impl;

import dev.drew.restaurantreview.entity.RestaurantEntity;
import dev.drew.restaurantreview.exception.NoResultsFoundException;
import dev.drew.restaurantreview.mapper.RestaurantMapper;
import dev.drew.restaurantreview.service.SearchService;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.PaginatedRestaurantResponse;
import org.openapitools.model.Restaurant;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.hibernate.search.engine.search.query.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

@Slf4j
@Service
public class SearchServiceImpl implements SearchService {

    private final EntityManager entityManager;
    private final RestaurantMapper restaurantMapper;

    @Autowired
    public SearchServiceImpl(EntityManager entityManager, RestaurantMapper restaurantMapper) {
        this.entityManager = entityManager;
        this.restaurantMapper = restaurantMapper;
    }

    private SearchResult<RestaurantEntity> executeSearch(String query, Pageable pageable) {
        log.info("Starting: Search Restaurants");
        log.info("Setting Search Session");
        SearchSession searchSession = Search.session(entityManager);

        return searchSession.search(RestaurantEntity.class)
                .where(f -> f.match()
                        .fields("name", "city")
                        .matching(query)
                        .fuzzy())
                .fetch((int) pageable.getOffset(), pageable.getPageSize());
    }

    @Override
    public List<Restaurant> searchRestaurantV1(String query, Pageable pageable) {
        SearchResult<RestaurantEntity> result = executeSearch(query, pageable);

        List<Restaurant> restaurants = result.hits().stream()
                .map(restaurantMapper::toRestaurant)
                .collect(Collectors.toList());

        if (restaurants.isEmpty()) {
            log.info("No Results");
            throw new NoResultsFoundException("No restaurants found for the given query: " + query);
        }
        log.info("Restaurants Incoming!");
        return restaurants;
    }

    @Override
    public PaginatedRestaurantResponse searchRestaurantV2(String query, Pageable pageable) {
        SearchResult<RestaurantEntity> result = executeSearch(query, pageable);

        log.info("Restaurants Incoming!");

        List<Restaurant> restaurants = result.hits().stream()
                .map(restaurantMapper::toRestaurant)
                .collect(Collectors.toList());

        if (restaurants.isEmpty()) {
            log.info("No Results");
            throw new NoResultsFoundException("No restaurants found for the given query: " + query);
        }

        PaginatedRestaurantResponse response = new PaginatedRestaurantResponse();
        response.setItems(new ArrayList<>(restaurants));
        response.setTotal(result.total().hitCount()); // Correctly get the total number of hits

        return response;
    }
}
