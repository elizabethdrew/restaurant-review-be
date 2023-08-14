package dev.drew.restaurantreview.controller;

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
                .when().request("POST", "/api/v1/signup")
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
                .when().request("POST", "/api/v1/signup")
                .then()
                .statusCode(400);
    }

    @Test
    void testAddUser_emailExists() throws Exception {
        String body = "{\"name\": \"another admin\", \n" +
                "    \"email\": \"admin@email.com\",\n" +
                "    \"username\": \"admin1\",\n" +
                "    \"password\": \"password\",\n" +
                "    \"role\": \"ADMIN\"\n" +
                "    }";

        given().log().all().contentType(ContentType.JSON)
                .body(body)
                .when().request("POST", "/api/v1/signup")
                .then()
                .statusCode(400);
    }
}
