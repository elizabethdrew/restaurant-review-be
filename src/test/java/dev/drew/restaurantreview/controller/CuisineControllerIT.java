package dev.drew.restaurantreview.controller;

import dev.drew.restaurantreview.GlobalTestContainer;
import dev.drew.restaurantreview.repository.CuisineRepository;
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

    private static CuisineRepository cuisineRepository;

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

        String token = authorisation();

        System.out.println("I love this token: " +token);

        String cuisineName = "Dogs";

        // Check status code
        given().log().all().contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+ token)
                .body("{\"name\": \""+ cuisineName + "\"}")
                .when().request("POST", "/api/v1/cuisines")
                .then().statusCode(201)
                .body("name", is(cuisineName), "id", notNullValue());
    }

}
