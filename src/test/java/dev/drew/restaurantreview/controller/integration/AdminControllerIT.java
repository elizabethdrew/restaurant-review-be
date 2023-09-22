package dev.drew.restaurantreview.controller.integration;

import dev.drew.restaurantreview.GlobalTestContainer;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
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

    @Test
    void testAcceptClaim() throws Exception {
        String token = authorisationAdmin();
        Integer id = 4;
        given().log().all().contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+ token)
                .when().request("POST", "/api/v1/admin/claims/" + id + "/accept")
                .then()
                .statusCode(200)
                .body("status", is("ACCEPTED"));
    }

    @Test
    void testAcceptClaim_unauthorised() throws Exception {
        Integer id = 4;
        given().log().all().contentType(ContentType.JSON)
                .when().request("POST", "/api/v1/admin/claims/" + id + "/accept")
                .then()
                .statusCode(403);
    }

    @Test
    void testAcceptClaim_noClaim() throws Exception {
        String token = authorisationAdmin();
        Integer id = 34;
        given().log().all().contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+ token)
                .when().request("POST", "/api/v1/admin/claims/" + id + "/accept")
                .then()
                .statusCode(404);
    }

    @Test
    void testRejectClaim() throws Exception {
        String token = authorisationAdmin();
        Integer id = 4;
        given().log().all().contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+ token)
                .when().request("POST", "/api/v1/admin/claims/" + id + "/reject")
                .then()
                .statusCode(200)
                .body("status", is("REJECTED"));
    }

    @Test
    void testRejectClaim_unauthorised() throws Exception {
        Integer id = 4;
        given().log().all().contentType(ContentType.JSON)
                .when().request("POST", "/api/v1/admin/claims/" + id + "/reject")
                .then()
                .statusCode(403);
    }

    @Test
    void testRejectClaim_noClaim() throws Exception {
        String token = authorisationAdmin();
        Integer id = 34;
        given().log().all().contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+ token)
                .when().request("POST", "/api/v1/admin/claims/" + id + "/reject")
                .then()
                .statusCode(404);
    }

    @Test
    void testGetPendingAdminRequests() throws Exception {
        String token = authorisationAdmin();
        given().log().all().contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+ token)
                .when().request("GET", "/api/v1/admin/users/pending")
                .then()
                .statusCode(200)
                .body("status.flatten()", hasItem("PENDING"))
                .body("status.flatten()", not(hasItem("ACCEPTED")))
                .body("status.flatten()", not(hasItem("REJECTED")));
    }

    @Test
    void testGetPendingAdminRequests_unauthorised() throws Exception {
        given().log().all().contentType(ContentType.JSON)
                .when().request("GET", "/api/v1/admin/users/pending")
                .then()
                .statusCode(403);
    }

    @Test
    void testAcceptAdminRequest() throws Exception {
        String token = authorisationAdmin();
        Integer id = 1;
        given().log().all().contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+ token)
                .when().request("POST", "/api/v1/admin/users/" + id + "/accept")
                .then()
                .statusCode(200)
                .body("status", is("ACCEPTED"));
    }

    @Test
    void testAcceptAdminRequest_unauthorised() throws Exception {
        Integer id = 2;
        given().log().all().contentType(ContentType.JSON)
                .when().request("POST", "/api/v1/admin/users/" + id + "/accept")
                .then()
                .statusCode(403);
    }

    @Test
    void testAcceptAdminRequest_noClaim() throws Exception {
        String token = authorisationAdmin();
        Integer id = 30;
        given().log().all().contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+ token)
                .when().request("POST", "/api/v1/admin/claims/" + id + "/accept")
                .then()
                .statusCode(404);
    }

    @Test
    void testRejectAdminRequest() throws Exception {
        String token = authorisationAdmin();
        Integer id = 1;
        given().log().all().contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+ token)
                .when().request("POST", "/api/v1/admin/users/" + id + "/reject")
                .then()
                .statusCode(200)
                    .body("status", is("REJECTED"));
    }

    @Test
    void testRejectAdminRequest_unauthorised() throws Exception {
        Integer id = 2;
        given().log().all().contentType(ContentType.JSON)
                .when().request("POST", "/api/v1/admin/users/" + id + "/reject")
                .then()
                .statusCode(403);
    }

    @Test
    void testRejectAdminRequest_noClaim() throws Exception {
        String token = authorisationAdmin();
        Integer id = 30;
        given().log().all().contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+ token)
                .when().request("POST", "/api/v1/admin/claims/" + id + "/reject")
                .then()
                .statusCode(404);
    }
}
