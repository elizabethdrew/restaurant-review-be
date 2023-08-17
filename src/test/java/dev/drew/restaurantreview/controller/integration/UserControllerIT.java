package dev.drew.restaurantreview.controller.integration;

import dev.drew.restaurantreview.GlobalTestContainer;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class UserControllerIT extends GlobalTestContainer {
    @Test
    void testAddUser() throws Exception {
        String body = "{\"name\": \"badmin\", \n" +
                "    \"email\": \"badmin@email.com\",\n" +
                "    \"username\": \"badmin\",\n" +
                "    \"password\": \"password\",\n" +
                "    \"role\": \"ADMIN\"\n" +
                "    }";

        given().log().all().contentType(ContentType.JSON)
                .body(body)
                .when().request("POST", "/api/v1/users")
                .then()
                .statusCode(201)
                .body(
                        "name", is("badmin"),
                        "id", notNullValue(),
                        "username", is("badmin"),
                        "role", is("ADMIN")
                );
    }

    @Test
    void testAddUser_usernameExists() throws Exception {
        String body = "{\"name\": \"admin person\", \n" +
                "    \"email\": \"admin1@email.com\",\n" +
                "    \"username\": \"admin\",\n" +
                "    \"password\": \"password\",\n" +
                "    \"role\": \"ADMIN\"\n" +
                "    }";

        given().log().all().contentType(ContentType.JSON)
                .body(body)
                .when().request("POST", "/api/v1/users")
                .then()
                .statusCode(400);
    }

    @Test
    void testAddUser_emailExists() throws Exception {
        String body = "{\"name\": \"another admin\", \n" +
                "    \"email\": \"admin@example.com\",\n" +
                "    \"username\": \"admin1\",\n" +
                "    \"password\": \"password\",\n" +
                "    \"role\": \"ADMIN\"\n" +
                "    }";

        given().log().all().contentType(ContentType.JSON)
                .body(body)
                .when().request("POST", "/api/v1/users")
                .then()
                .statusCode(400);
    }

    @Test
    void testGetUserById_exists_authorised() throws Exception {
        String token = authorisationAdmin();
        Integer userId = 1;
        given().log().all().contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+ token)
                .when().request("GET", "/api/v1/users/" + userId)
                .then()
                .statusCode(200)
                .body("name", is("Admin User"),
                        "id", is(userId));
    }

    @Test
    void testGetUserById_notExists_authorised() throws Exception {
        String token = authorisationAdmin();
        Integer userId = 4;
        given().log().all().contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+ token)
                .when().request("GET", "/api/v1/users/" + userId)
                .then()
                .statusCode(404);
    }

    @Test
    void testGetUserById_exists_notAuthorised() throws Exception {
        Integer userId = 1;
        given().log().all().contentType(ContentType.JSON)
                .when().request("GET", "/api/v1/users/" + userId)
                .then()
                .statusCode(403);
    }

    @Test
    void testUpdateUserById() throws Exception {
        String token = authorisationAdmin();
        Integer userId = 2;
        String body = "{\"name\": \"updated rev\", \n" +
                "    \"email\": \"up@email.com\",\n" +
                "    \"username\": \"revupdate\",\n" +
                "    \"password\": \"password\",\n" +
                "    \"role\": \"REVIEWER\"\n" +
                "    }";

        given().log().all().contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+ token)
                .body(body)
                .when().request("PUT", "/api/v1/users/" + userId)
                .then()
                .statusCode(200)
                .body(
                        "name", is("updated rev"),
                        "id", is(userId),
                        "username", is("revupdate"),
                        "role", is("REVIEWER")
                );
    }

    @Test
    void testDeleteUserById() throws Exception {

        String token = authorisationAdmin();
        Integer userId = 3;
        given().log().all().contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+ token)
                .when().request("DELETE", "/api/v1/users/" + userId)
                .then().statusCode(204);
    }
}
