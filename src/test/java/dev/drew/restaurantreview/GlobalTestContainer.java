package dev.drew.restaurantreview;

import io.restassured.RestAssured;
import jakarta.persistence.EntityManagerFactory;
import liquibase.integration.spring.SpringLiquibase;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(initializers = dev.drew.restaurantreview.DockerMysqlDataSourceInitializer.class)
public abstract class GlobalTestContainer {

    @Autowired
    SpringLiquibase liquibase;

    @Autowired
    EntityManagerFactory emf;

    @Container
    public static MySQLContainer<?> container = new MySQLContainer<>("mysql:latest")
            .withDatabaseName("example_db")
            .withUsername("Test")
            .withPassword("Test");

    @BeforeAll
    public static void setUp(){
        RestAssured.baseURI = "http://localhost:8081";
    }

    @AfterAll
    public static void tearDown(){
    }

    public String authorisationAdmin() {

        String token =
        given()
                .basePath("/api/v1/login")
                .contentType("application/json")
                .body("{\"username\": \"admin\", \"password\": \"admin123\"}")
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .path("token")
                .toString();

        return token;

    }

    public String authorisationReviewer() {

        String token =
                given()
                        .basePath("/api/v1/login")
                        .contentType("application/json")
                        .body("{\"username\": \"reviewer\", \"password\": \"reviewer123\"}")
                        .when()
                        .post()
                        .then()
                        .statusCode(200)
                        .extract()
                        .path("token")
                        .toString();

        return token;

    }

}