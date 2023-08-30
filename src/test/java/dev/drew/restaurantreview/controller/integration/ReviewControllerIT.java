package dev.drew.restaurantreview.controller.integration;

import dev.drew.restaurantreview.GlobalTestContainer;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;

public class ReviewControllerIT extends GlobalTestContainer {

    @Test
    void testGetAllReviews() throws Exception {

        when().request("GET", "/api/v1/reviews")
                .then()
                .statusCode(200)
                .body("[0].restaurant_name", equalTo("Gastronomic Guildhall"),
                        "[1].rating", equalTo(4),
                        "[2].comment", equalTo("Couldn't be worse."));
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

    @Test
    void testGetAllReviews_withRating_multpile() throws Exception {
        given().queryParam("rating", "2,3")
                .then()
                .statusCode(200)
                .body("rating", not(hasItem(1)))
                .body("rating", hasItem(2))
                .body("rating", hasItem(3));
    }

    @Test
    void testGetAllReviews_withPagination() throws Exception {
        when().request("GET", "/api/v1/reviews?page=0&size=5")
                .then()
                .statusCode(200)
                .body("[0].id", is(1));
    }

    @Test
    void testGetAllReviews_withSorting() throws Exception {
        when().request("GET", "/api/v1/reviews?sort=restaurant_name,asc")
                .then()
                .statusCode(200)
                .body("[0].restaurant_name", is("Aroma Abbey"));
    }

    @Test
    void testGetReviewById_exists() throws Exception {
        Integer revId = 1;
        when().request("GET", "/api/v1/reviews/" + revId)
                .then()
                .statusCode(200)
                .body("comment", is("Good ambience and service"),
                "rating", is(3));
    }

    @Test
    void testGetReviewById_notExist() throws Exception {
        Integer revId = 20;
        when().request("GET", "/api/v1/reviews/" + revId)
                .then()
                .statusCode(404);
    }

    @Test
    void testAddNewReview_authorised() throws Exception {
        String token = authorisationAdmin();
        String body = "{\"restaurant_id\": \"15\", \n" +
                "    \"rating\": \"4\",\n" +
                "    \"comment\": \"Great Food.\"\n" +
                "    }";

        given().log().all().contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+ token)
                .body(body)
                .when().request("POST", "/api/v1/reviews")
                .then()
                .statusCode(201)
                .body(
                        "restaurant_id", is(15),
                        "comment", is("Great Food."),
                        "rating", is(4),
                        "id", notNullValue(),
                        "user_id", notNullValue()
                );
    }

    @Test
    void testAddNewReview_unauthorised() throws Exception {
        String body = "{\"restaurant_id\": \"14\", \n" +
                "    \"rating\": \"3\",\n" +
                "    \"comment\": \"Meh.\"\n" +
                "    }";

        given().log().all().contentType(ContentType.JSON)
                .body(body)
                .when().request("POST", "/api/v1/reviews")
                .then()
                .statusCode(403);
    }

    @Test
    void testAddNewReview_alreadyExists() throws Exception {
        String body = "{\"restaurant_id\": \"3\", \n" +
                "    \"rating\": \"5\",\n" +
                "    \"comment\": \"Can I eat here every day?\"\n" +
                "    }";

        given().log().all().contentType(ContentType.JSON)
                .body(body)
                .when().request("POST", "/api/v1/reviews")
                .then()
                .statusCode(403);
    }

    @Test
    void testAddNewReview_restaurantOwner() throws Exception {
        String body = "{\"restaurant_id\": \"1\", \n" +
                "    \"rating\": \"5\",\n" +
                "    \"comment\": \"Can I eat here every day?\"\n" +
                "    }";

        given().log().all().contentType(ContentType.JSON)
                .body(body)
                .when().request("POST", "/api/v1/reviews")
                .then()
                .statusCode(403);
    }

    @Test
    void testAddNewReview_restaurantNotFound() throws Exception {
        String token = authorisationAdmin();
        String body = "{\"restaurant_id\": \"16\", \n" +
                "    \"rating\": \"4\",\n" +
                "    \"comment\": \"Great Food.\"\n" +
                "    }";

        given().log().all().contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+ token)
                .body(body)
                .when().request("POST", "/api/v1/reviews")
                .then()
                .statusCode(404);
    }

    @Test
    void testAddNewReview_missingRating() throws Exception {
        String token = authorisationAdmin();
        String body = "{\"restaurant_id\": \"2\", \n" +
                "    \"comment\": \"Great Food.\"\n" +
                "    }";

        given().log().all().contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+ token)
                .body(body)
                .when().request("POST", "/api/v1/reviews")
                .then()
                .statusCode(500);
    }

    @Test
    void testAddNewReview_invaildRating() throws Exception {
        String token = authorisationAdmin();
        String body = "{\"restaurant_id\": \"5\", \n" +
                "    \"rating\": \"6\",\n" +
                "    \"comment\": \"Great Food.\"\n" +
                "    }";

        given().log().all().contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+ token)
                .body(body)
                .when().request("POST", "/api/v1/reviews")
                .then()
                .statusCode(500);
    }

    @Test
    void testUpdateReviewById_authorised() throws Exception {
        String token = authorisationAdmin();
        Integer revId = 7;
        String body = "{\"restaurant_id\": \"5\", \n" +
                "    \"rating\": \"5\",\n" +
                "    \"comment\": \"Great Food.\"\n" +
                "    }";

        given().log().all().contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(body)
                .when().request("PUT", "/api/v1/reviews/" + revId)
                .then()
                .statusCode(200)
                .body(
                        "rating", is(5),
                        "id", equalTo(revId),
                        "user_id", notNullValue(),
                        "comment", is("Great Food.")
                );
    }

    @Test
    void testUpdateReviewById_unauthorised() throws Exception {
        Integer revId = 5;
        String body = "{\"rating\": 1,\n" +
                "    \"comment\": \"Urghh! Gross!\"\n" +
                "    }";

        given().log().all().contentType(ContentType.JSON)
                .body(body)
                .when().request("PUT", "/api/v1/reviews/" + revId)
                .then()
                .statusCode(403);
    }

    @Test
    void testDeleteReviewById_authorised() throws Exception {
        String token = authorisationAdmin();
        Integer revId = 1;
        given().log().all().contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+ token)
                .when().request("DELETE", "/api/v1/reviews/" + revId)
                .then().statusCode(204);
    }

    @Test
    void testDeleteReviewById_unauthorised() throws Exception {
        Integer revId = 6;
        given().log().all().contentType(ContentType.JSON)
                .when().request("DELETE", "/api/v1/reviews/" + revId)
                .then().statusCode(403);
    }
}
