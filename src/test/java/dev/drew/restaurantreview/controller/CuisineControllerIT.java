package dev.drew.restaurantreview.controller;

import dev.drew.restaurantreview.GlobalTestContainer;
import dev.drew.restaurantreview.service.CuisineService;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

public class CuisineControllerIT extends GlobalTestContainer {

    @Autowired
    private CuisineService cuisineService;

    @Test
    void testGetAllCuisines() throws Exception {

        // Check status code
        when().request("GET", "/api/v1/cuisines")
                .then().statusCode(200);

        // Check that expected first and last cuisines included in response
        when().request("GET", "/api/v1/cuisines")
                .then().assertThat().body("", hasItems("American", "Thai"));

    }

    @Test
    void testAddNewCuisine() throws Exception {

        String cuisineName = "Cat Food";
        String newCuisine = "{\"name\":\""+ cuisineName + "\"}";

        // Check status code
        given().contentType(ContentType.JSON)
                .body(newCuisine)
                .when().request("POST", "/api/v1/cuisines")
                .then().statusCode(201)
                .body("name", is(cuisineName), "id", notNullValue());
    }

}
