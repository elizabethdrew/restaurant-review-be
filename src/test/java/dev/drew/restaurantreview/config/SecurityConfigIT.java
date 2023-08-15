package dev.drew.restaurantreview.config;

import dev.drew.restaurantreview.GlobalTestContainer;
import io.restassured.http.ContentType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

public class SecurityConfigIT extends GlobalTestContainer {

    //Test accessing public endpoints without authentication
    @Nested
    class WithoutAuthentication {

        @Test
        public void testGetRestaurants() throws Exception {
            when().request("GET", "/api/v1/restaurants")
                    .then()
                    .statusCode(200);
        }

        @Test
        public void testGetCuisines() throws Exception {
            when().request("GET", "/api/v1/cuisines")
                    .then()
                    .statusCode(200);
        }

        @Test
        public void testGetRestaurantById() throws Exception {
            when().request("GET", "/api/v1/restaurants/1")
                    .then()
                    .statusCode(200);
        }

        @Test
        public void testGetReviews() throws Exception {
            when().request("GET", "/api/v1/reviews")
                    .then()
                    .statusCode(200);
        }

        @Test
        public void testGetReviewById() throws Exception {
            when().request("GET", "/api/v1/reviews/1")
                    .then()
                    .statusCode(200);
        }

        @Test
        public void testPostUser() throws Exception {
            String body = "{\"name\": \"rev\", \n" +
                    "    \"email\": \"rev@email.com\",\n" +
                    "    \"username\": \"rev\",\n" +
                    "    \"password\": \"password\",\n" +
                    "    \"role\": \"REVIEWER\"\n" +
                    "    }";

            given().log().all().contentType(ContentType.JSON)
                    .body(body)
                    .when().request("POST", "/api/v1/users")
                    .then()
                    .statusCode(201)
                    .body(
                            "name", is("rev"),
                            "id", notNullValue(),
                            "username", is("rev"),
                                "role", is("REVIEWER")
                    );
        }
    }

    // Test accessing protected endpoints with Admin Authentication
    @Nested
    class ProtectedWithAdminAuthentication {

        @Test
        public void testAdminAuthenticated_GetUserById() throws Exception {
            String token = authorisationAdmin();
            Integer userId = 1;
            given().log().all().contentType(ContentType.JSON)
                    .header("Authorization", "Bearer "+ token)
                    .when().request("GET", "/api/v1/users/" + userId)
                    .then()
                    .statusCode(200);
        }

        @Test
        public void testAdminAuthenticated_DeleteUserById() throws Exception {
            String token = authorisationAdmin();
            Integer userId = 1;
            given().log().all().contentType(ContentType.JSON)
                    .header("Authorization", "Bearer "+ token)
                    .when().request("DELETE", "/api/v1/users/" + userId)
                    .then()
                    .statusCode(204);
        }

        @Test
        public void testAdminAuthenticated_EditUserById() throws Exception {
            String token = authorisationAdmin();
            Integer userId = 1;
            String body = "{\"name\": \"updated admin\", \n" +
                    "    \"email\": \"admin@email.com\",\n" +
                    "    \"username\": \"admin\",\n" +
                    "    \"role\": \"ADMIN\"\n" +
                    "    }";
            given().log().all().contentType(ContentType.JSON)
                    .header("Authorization", "Bearer "+ token)
                    .body(body)
                    .when().request("PUT", "/api/v1/users/" + userId)
                    .then()
                    .statusCode(200)
                    .body(
                            "name", is("updated admin")
                    );
        }

    }

    // Test accessing protected endpoints with Reviewer authentication
    @Nested
    class ProtectedWithReviewerAuthentication {

        @Test
        public void testReviewerAuthenticated_GetUserById() throws Exception {
            String token = authorisationReviewer();
            Integer userId = 1;
            given().log().all().contentType(ContentType.JSON)
                    .header("Authorization", "Bearer "+ token)
                    .when().request("GET", "/api/v1/users/" + userId)
                    .then()
                    .statusCode(403);
        }

        @Test
        public void testReviewerAuthenticated_DeleteUserById() throws Exception {
            String token = authorisationReviewer();
            Integer userId = 1;
            given().log().all().contentType(ContentType.JSON)
                    .header("Authorization", "Bearer "+ token)
                    .when().request("DELETE", "/api/v1/users/" + userId)
                    .then()
                    .statusCode(403);
        }

        @Test
        public void testReviewerAuthenticated_EditUserById() throws Exception {
            String token = authorisationReviewer();
            Integer userId = 1;
            String body = "{\"name\": \"updated admin\", \n" +
                    "    \"email\": \"admin@email.com\",\n" +
                    "    \"username\": \"admin\",\n" +
                    "    \"role\": \"ADMIN\"\n" +
                    "    }";
            given().log().all().contentType(ContentType.JSON)
                    .header("Authorization", "Bearer "+ token)
                    .body(body)
                    .when().request("PUT", "/api/v1/users/" + userId)
                    .then()
                    .statusCode(403);
        }

    }
}
