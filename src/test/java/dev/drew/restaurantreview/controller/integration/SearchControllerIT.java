package dev.drew.restaurantreview.controller.integration;

import dev.drew.restaurantreview.GlobalTestContainer;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

public class SearchControllerIT extends GlobalTestContainer {

    @Test
    void testGetSearch_withCity() throws Exception {

        given().queryParam("query", "Bristol")
                .when().request("GET", "/api/v1/search")
                .then()
                .statusCode(200)
                .body("city", hasItem("Bristol"));
    }

    @Test
    void testGetSearch_withPartName() throws Exception {

        given().queryParam("query", "Sizzling")
                .when().request("GET", "/api/v1/search")
                .then()
                .statusCode(200)
                .body("name", hasItem("The Sizzling Skillet"));
    }

    @Test
    void testGetSearch_withPartName_FuzzyOne() throws Exception {

        given().queryParam("query", "Sizzlimg")
                .when().request("GET", "/api/v1/search")
                .then()
                .statusCode(200)
                .body("name", hasItem("The Sizzling Skillet"));
    }

    @Test
    void testGetSearch_withPartName_FuzzyTwo() throws Exception {

        given().queryParam("query", "Fizzlimg")
                .when().request("GET", "/api/v1/search")
                .then()
                .statusCode(200)
                .body("name", hasItem("The Sizzling Skillet"));
    }

    @Test
    void testGetSearch_nothingFound() throws Exception {

        given().queryParam("query", "jdhfkshf")
                .when().request("GET", "/api/v1/search")
                .then()
                .statusCode(404);
    }

    @Test
    void testGetSearch_withFullName() throws Exception {

        given().queryParam("query", "Cardinal Cravings")
                .when().request("GET", "/api/v1/search")
                .then()
                .statusCode(200)
                .body("name", hasItem("Cardinal Cravings"));
    }
}
