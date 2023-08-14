package dev.drew.restaurantreview.controller;

import dev.drew.restaurantreview.GlobalTestContainer;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class ReviewControllerIT extends GlobalTestContainer {

    @Test
    void testGetAllReviews() throws Exception {

        when().request("GET", "/api/v1/reviews")
                .then()
                .statusCode(200)
                .body("[0].restaurant_name", equalTo("Gastronomic Guildhall"),
                        "[1].rating", equalTo(4),
                        "[2].comment", equalTo("Couldn't be worse."),
                        "[14].id", equalTo(15));
    }

    @Test
    void testGetAllReviews_withRestaurantId() throws Exception {
        Integer restaurantId = 1;
        given().queryParam("restaurant_id", restaurantId)
                .when().request("GET", "/api/v1/reviews")
                .then()
                .statusCode(200)
                .body("[0].restaurant_name", equalTo("Gastronomic Guildhall"),
                        "[0].restaurant_id", equalTo(restaurantId));
    }

    @Test
    void testGetAllReviews_withUserId() throws Exception {
        Integer userId = 1;
        given().queryParam("user_id", userId)
                .when().request("GET", "/api/v1/reviews")
                .then()
                .statusCode(200)
                .body("[0].user_id", equalTo(userId));
    }

    @Test
    void testGetAllReviews_withRating() throws Exception {
        Integer rating = 3;
        given().queryParam("rating", rating)
                .when().request("GET", "/api/v1/reviews")
                .then()
                .statusCode(200)
                .body("[0].rating", equalTo(rating));
    }
}
