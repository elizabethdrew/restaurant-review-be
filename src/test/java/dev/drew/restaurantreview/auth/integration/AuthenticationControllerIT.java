package dev.drew.restaurantreview.auth.integration;

import dev.drew.restaurantreview.GlobalTestContainer;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthenticationControllerIT extends GlobalTestContainer {

    @Test
    void testLogin_credentialsCorrect() throws Exception {

        String body = "{\n" +
                "    \"username\": \"admin\",\n" +
                "    \"password\": \"admin123\"\n" +
                "}";
        given().log().all().contentType(ContentType.JSON)
                .body(body)
                .when().request("POST", "api/v1/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue(),
                        "userId", equalTo(1),
                        "expirationTime", notNullValue());
    }

    @Test
    void testLogin_wrongPassword() throws Exception {

        String body = "{\n" +
                "    \"username\": \"admin\",\n" +
                "    \"password\": \"password123\"\n" +
                "}";
        given().log().all().contentType(ContentType.JSON)
                .body(body)
                .when().request("POST", "api/v1/login")
                .then()
                .statusCode(400);
    }

    @Test
    void testLogin_notRegisteredUser() throws Exception {

        String body = "{\n" +
                "    \"username\": \"random\",\n" +
                "    \"password\": \"password123\"\n" +
                "}";
        given().log().all().contentType(ContentType.JSON)
                .body(body)
                .when().request("POST", "api/v1/login")
                .then()
                .statusCode(400);
    }

    @Test
    void testLogout_loggedInUser() throws Exception {
        String token = authorisationAdmin();
        given().log().all()
                .header("Authorization", "Bearer "+ token)
                .when().request("POST", "api/v1/logout")
                .then()
                .statusCode(200);
    }

    @Test
    void testLogout_notLoggedInUser() throws Exception {
        given().log().all()
                .when().request("POST", "api/v1/logout")
                .then()
                .statusCode(403);
    }
}
