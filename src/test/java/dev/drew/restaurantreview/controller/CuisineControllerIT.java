package dev.drew.restaurantreview.controller;

import dev.drew.restaurantreview.GlobalTestContainer;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

public class CuisineControllerIT extends GlobalTestContainer {

    @Test
    void testGetAllCuisines() throws Exception {

        when().request("GET", "/api/v1/cuisines")
                .then()
                .statusCode(200)
                .body("", hasItems("American", "Thai"));
    }

    @Test
    void testAddNewCuisine() throws Exception {

        String token = authorisation();
        String cuisineName = "Cornish";
        given().log().all().contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+ token)
                .body("{\"name\": \""+ cuisineName + "\"}")
                .when().request("POST", "/api/v1/cuisines")
                .then()
                .statusCode(201)
                .body(
                        "name", is(cuisineName),
                        "id", notNullValue()
                );
    }

    @Test
    void testAddNewCuisine_alreadyExists() throws Exception {

        String token = authorisation();
        String cuisineName = "American";
        given().log().all().contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+ token)
                .body("{\"name\": \""+ cuisineName + "\"}")
                .when().request("POST", "/api/v1/cuisines")
                .then()
                .statusCode(409);
    }

    @Test
    void testDeleteCuisine() throws Exception {
        String token = authorisation();
        String cuisineName = "Indian";
        given().log().all().contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+ token)
                .when().request("DELETE", "/api/v1/cuisines/" +cuisineName)
                .then().statusCode(204);
    }

    @Test
    void testDeleteCuisine_referencedByRestaurant() throws Exception {
        String token = authorisation();
        String cuisineName = "British";
        given().log().all().contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+ token)
                .when().request("DELETE", "/api/v1/cuisines/" +cuisineName)
                .then().statusCode(409);
    }

    @Test
    void testDeleteCuisine_cuisineNotFound() throws Exception {
        String token = authorisation();
        String cuisineName = "Kebab";
        given().log().all().contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+ token)
                .when().request("DELETE", "/api/v1/cuisines/" +cuisineName)
                .then().statusCode(404);
    }

}
