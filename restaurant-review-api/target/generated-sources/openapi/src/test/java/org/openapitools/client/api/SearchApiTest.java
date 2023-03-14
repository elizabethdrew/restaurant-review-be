/*
 * Restaurant Review API
 * An API for managing restaurant reviews
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.api;

import org.openapitools.client.ApiException;
import org.openapitools.client.model.Restaurant;
import org.openapitools.client.model.Review;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for SearchApi
 */
@Disabled
public class SearchApiTest {

    private final SearchApi api = new SearchApi();

    /**
     * Search restaurants by review rating or city
     *
     * Returns a list of restaurants matching the specified search criteria.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void searchRestaurantsByRatingOrCityTest() throws ApiException {
        Integer rating = null;
        String city = null;
        List<Restaurant> response = api.searchRestaurantsByRatingOrCity(rating, city);
        // TODO: test validations
    }

    /**
     * Search reviews by rating
     *
     * Returns a list of reviews with the specified rating.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void searchReviewByRatingTest() throws ApiException {
        Integer rating = null;
        List<Review> response = api.searchReviewByRating(rating);
        // TODO: test validations
    }

}
