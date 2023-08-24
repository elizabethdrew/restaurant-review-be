package dev.drew.restaurantreview.service.impl;

import dev.drew.restaurantreview.entity.RestaurantEntity;
import dev.drew.restaurantreview.exception.NoResultsFoundException;
import dev.drew.restaurantreview.mapper.RestaurantMapper;
import dev.drew.restaurantreview.service.SearchService;
import jakarta.persistence.EntityManager;
import org.openapitools.model.Restaurant;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.hibernate.search.engine.search.query.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {

    private final EntityManager entityManager;
    private final RestaurantMapper restaurantMapper;

    @Autowired
    public SearchServiceImpl(EntityManager entityManager, RestaurantMapper restaurantMapper) {
        this.entityManager = entityManager;
        this.restaurantMapper = restaurantMapper;
    }

    @Override
    public List<Restaurant> searchRestaurant(String query) {
        SearchSession searchSession = Search.session(entityManager);

        SearchResult<RestaurantEntity> result = searchSession.search(RestaurantEntity.class)
                .where(f -> f.match()
                        .fields("name", "city")
                        .matching(query)
                        .fuzzy())
                .fetch(20);

        List<Restaurant> restaurants = result.hits().stream()
                .map(restaurantMapper::toRestaurant)
                .collect(Collectors.toList());

        if (restaurants.isEmpty()) {
            throw new NoResultsFoundException("No restaurants found for the given query: " + query);
        }

        return restaurants;
    }

}