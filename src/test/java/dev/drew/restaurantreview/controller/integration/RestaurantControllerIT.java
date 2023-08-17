package dev.drew.restaurantreview.controller.integration;


import dev.drew.restaurantreview.GlobalTestContainer;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;

public class RestaurantControllerIT extends GlobalTestContainer {

    @Test
    void testGetAllRestaurants() throws Exception {

        when().request("GET", "/api/v1/restaurants")
                .then()
                .statusCode(200)
                .body("[0].name", equalTo("Gastronomic Guildhall"));
    }

    @Test
    void testGetAllRestaurants_withCity() throws Exception {

        given().queryParam("city", "Bristol")
                .when().request("GET", "/api/v1/restaurants")
                .then()
                .statusCode(200)
                .body("[0].city", equalTo("Bristol"));
    }

    @Test
    void testGetAllRestaurants_withRating() throws Exception {

        given().queryParam("rating", "3")
                .when().request("GET", "/api/v1/restaurants")
                .then()
                .statusCode(200)
                .body("[0].rating", equalTo(3));
    }

    @Test
    void testGetAllRestaurants_withPriceRange() throws Exception {
        given().queryParam("price_range", "2")
                .when().request("GET", "/api/v1/restaurants")
                .then()
                .statusCode(200)
                .body("[0].price_range", equalTo(2));
    }

    @Test
    void testGetAllRestaurants_withCuisine() throws Exception {
        given().queryParam("cuisine", "Italian")
                .when().request("GET", "/api/v1/restaurants")
                .then()
                .statusCode(200)
                .body("cuisines.flatten()", hasItem("Italian"));
    }

    @Test
    void testGetAllRestaurants_withUserId() throws Exception {

        given().queryParam("user_id", "1")
                .when().request("GET", "/api/v1/restaurants")
                .then()
                .statusCode(200)
                .body("[0].user_id", equalTo(1));
    }

    @Test
    void testGetAllRestaurants_withPagination() throws Exception {
        when().request("GET", "/api/v1/restaurants?page=1&size=5")
                .then()
                .statusCode(200)
                .body("[0].id", is(6));
    }

    @Test
    void testGetAllRestaurants_withSorting() throws Exception {
        when().request("GET", "/api/v1/restaurants?sort=city,asc")
                .then()
                .statusCode(200)
                .body("[0].city", is("Bath"));
    }

    @Test
    void testGetAllRestaurants_withCity_multiple() throws Exception {

        given().queryParam("city", "Bristol,London")
                .when().request("GET", "/api/v1/restaurants")
                .then()
                .statusCode(200)
                .body("city", not(hasItem("Bath")))
                .body("city", hasItem("Bristol"))
                .body("city", hasItem("London"));
    }

    @Test
    void testGetAllRestaurants_withRating_multiple() throws Exception {

        given().queryParam("rating", "2,3")
                .when().request("GET", "/api/v1/restaurants")
                .then()
                .statusCode(200)
                .body("rating", not(hasItem(1)))
                .body("rating", hasItem(2))
                .body("rating", hasItem(3));
    }

    @Test
    void testGetAllRestaurants_withPriceRange_multiple() throws Exception {
        given().queryParam("price_range", "1,2")
                .when().request("GET", "/api/v1/restaurants")
                .then()
                .statusCode(200)
                .body("price_range", not(hasItem(3)))
                .body("price_range", hasItem(1))
                .body("price_range", hasItem(2));
    }

    @Test
    void testGetAllRestaurants_withCuisine_multiple() throws Exception {
        given().queryParam("cuisine", "American,Italian")
                .when().request("GET", "/api/v1/restaurants")
                .then()
                .statusCode(200)
                .body("cuisines.flatten()", not(hasItem("Indian")))
                .body("cuisines.flatten()", hasItem("Italian"))
                .body("cuisines.flatten()", hasItem("American"));
    }

    @Test
    void testGetAllRestaurants_withCity_incorrect() throws Exception {

        given().queryParam("city", "Moon")
                .when().request("GET", "/api/v1/restaurants")
                .then()
                .statusCode(200);
    }

    @Test
    void testGetAllRestaurants_withRating_incorrect() throws Exception {

        given().queryParam("rating", "6")
                .when().request("GET", "/api/v1/restaurants")
                .then()
                .statusCode(200);
    }

    @Test
    void testGetAllRestaurants_withPriceRange_incorrect() throws Exception {
        given().queryParam("price_range", "4")
                .when().request("GET", "/api/v1/restaurants")
                .then()
                .statusCode(200);
    }

    @Test
    void testGetAllRestaurants_withCuisine_incorrect() throws Exception {
        given().queryParam("cuisine", "Mush")
                .when().request("GET", "/api/v1/restaurants")
                .then()
                .statusCode(200);
    }

    @Test
    void testGetAllRestaurants_withCityAndRating() throws Exception {

        given().queryParam("city", "Bristol")
                .queryParam("rating", "4")
                .when().request("GET", "/api/v1/restaurants")
                .then()
                .statusCode(200)
                .body("rating", hasItem(4))
                .body("city", hasItem("Bristol"));;
    }

    @Test
    void testAddNewRestaurant_authorised() throws Exception {
        String token = authorisationAdmin();
        String body = "{\n" +
                "    \"name\": \"New Restaurant 1\", \n" +
                "    \"city\": \"New City\",\n" +
                "    \"price_range\": 2,\n" +
                "    \"latitude\": 51.5074,\n" +
                "    \"longitude\": 0.1278,\n" +
                "    \"userId\": 1,\n" +
                "    \"cuisines\": [\"British\", \"American\"]\n" +
                "}";

        given().log().all().contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+ token)
                .body(body)
                .when().request("POST", "/api/v1/restaurants")
                .then()
                .statusCode(201)
                .body(
                        "name", is("New Restaurant 1"),
                        "id", notNullValue(),
                        "user_id", notNullValue()
                );
    }

    @Test
    void testAddNewRestaurant_unauthorised() throws Exception {
        String body = "{\n" +
                "    \"name\": \"New Restaurant 2\", \n" +
                "    \"city\": \"New City\",\n" +
                "    \"price_range\": 2,\n" +
                "    \"latitude\": 51.5074,\n" +
                "    \"longitude\": 0.1278,\n" +
                "    \"userId\": 1,\n" +
                "    \"cuisines\": [\"British\", \"American\"]\n" +
                "}";

        given().log().all().contentType(ContentType.JSON)
                .body(body)
                .when().request("POST", "/api/v1/restaurants")
                .then()
                .statusCode(403);
    }

    @Test
    void testAddNewRestaurant_alreadyExists() throws Exception {
        String token = authorisationAdmin();
        String body = "{\n" +
                "    \"name\": \"Gastronomic Guildhall\", \n" +
                "    \"city\": \"London\",\n" +
                "    \"price_range\": 2,\n" +
                "    \"latitude\": 51.5074,\n" +
                "    \"longitude\": 0.1278,\n" +
                "    \"userId\": 1,\n" +
                "    \"cuisines\": [\"British\", \"American\"]\n" +
                "}";

        given().log().all().contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+ token)
                .body(body)
                .when().request("POST", "/api/v1/restaurants")
                .then()
                .statusCode(409);
    }

    @Test
    void testAddNewRestaurant_nullCity() throws Exception {
        String token = authorisationAdmin();
        String body = "{\n" +
                "    \"name\": \"New Restaurant 3\", \n" +
                "    \"city\": null,\n" +
                "    \"price_range\": 2,\n" +
                "    \"latitude\": 51.5074,\n" +
                "    \"longitude\": 0.1278,\n" +
                "    \"userId\": 1,\n" +
                "    \"cuisines\": [\"British\", \"American\"]\n" +
                "}";

        given().log().all().contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+ token)
                .body(body)
                .when().request("POST", "/api/v1/restaurants")
                .then()
                .statusCode(500);
    }

    @Test
    void testAddNewRestaurant_invalidPrice() throws Exception {
        String token = authorisationAdmin();
        String body = "{\n" +
                "    \"name\": \"New Restaurant 4\", \n" +
                "    \"city\": \"New City\",\n" +
                "    \"price_range\": 5,\n" +
                "    \"latitude\": 51.5074,\n" +
                "    \"longitude\": 0.1278,\n" +
                "    \"userId\": 1,\n" +
                "    \"cuisines\": [\"British\", \"American\"]\n" +
                "}";

        given().log().all().contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+ token)
                .body(body)
                .when().request("POST", "/api/v1/restaurants")
                .then()
                .statusCode(500);
    }

    @Test
    void testGetRestaurantById_exists() throws Exception {

        Integer resId = 1;
        when().request("GET", "/api/v1/restaurants/" +resId)
                .then()
                .statusCode(200)
                .body("name", is("Gastronomic Guildhall"));
    }

    @Test
    void testGetRestaurantById_notExist() throws Exception {

        Integer resId = 100;
        when().request("GET", "/api/v1/restaurants/" +resId)
                .then()
                .statusCode(404);
    }

    @Test
    void testUpdateRestaurantById_authorised() throws Exception {
        String token = authorisationAdmin();
        Integer resId = 5;
        String body = "{\n" +
                "    \"name\": \"Updated Restaurant 1\", \n" +
                "    \"city\": \"Updated City\",\n" +
                "    \"price_range\": 2,\n" +
                "    \"latitude\": 51.5074,\n" +
                "    \"longitude\": 0.1278,\n" +
                "    \"userId\": 1,\n" +
                "    \"rating\": 4,\n" +
                "    \"cuisines\": [\"British\", \"American\"]\n" +
                "}";

        given().log().all().contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+ token)
                .body(body)
                .when().request("PUT", "/api/v1/restaurants/" + resId)
                .then()
                .statusCode(200)
                .body(
                        "name", is("Updated Restaurant 1"),
                        "id", equalTo(resId),
                        "user_id", notNullValue()
                );
    }

    @Test
    void testUpdateRestaurantById_notAuthorised() throws Exception {
        Integer resId = 5;
        String body = "{\n" +
                "    \"name\": \"Updated Restaurant 2\", \n" +
                "    \"city\": \"Updated City\",\n" +
                "    \"price_range\": 2,\n" +
                "    \"latitude\": 51.5074,\n" +
                "    \"longitude\": 0.1278,\n" +
                "    \"userId\": 1,\n" +
                "    \"rating\": 4,\n" +
                "    \"cuisines\": [\"British\", \"American\"]\n" +
                "}";

        given().log().all().contentType(ContentType.JSON)
                .body(body)
                .when().request("PUT", "/api/v1/restaurants/" + resId)
                .then()
                .statusCode(403);
    }

    @Test
    void testDeleteRestaurantById() throws Exception {
        String token = authorisationAdmin();
        Integer resId = 5;
        given().log().all().contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+ token)
                .when().request("DELETE", "/api/v1/restaurants/" + resId)
                .then().statusCode(204);
    }

}