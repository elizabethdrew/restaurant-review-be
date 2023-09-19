package dev.drew.restaurantreview.controller.integration;

import dev.drew.restaurantreview.GlobalTestContainer;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

public class AdminControllerIT extends GlobalTestContainer {

    @Test
    void testGetPendingClaims() throws Exception {
        String token = authorisationAdmin();
        given().log().all().contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+ token)
                .when().request("GET", "/api/v1/admin/claims/pending")
                .then()
                .statusCode(200)
                .body("status.flatten()", hasItem("PENDING"))
                .body("status.flatten()", not(hasItem("ACCEPTED")))
                .body("status.flatten()", not(hasItem("REJECTED")));
    }

    @Test
    void testGetPendingClaims_unauthorised() throws Exception {
        given().log().all().contentType(ContentType.JSON)
                .when().request("GET", "/api/v1/admin/claims/pending")
                .then()
                .statusCode(403);
    }
}
