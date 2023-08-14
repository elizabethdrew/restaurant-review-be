package dev.drew.restaurantreview.controller;

import dev.drew.restaurantreview.GlobalTestContainer;
import org.junit.jupiter.api.Test;

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
}
