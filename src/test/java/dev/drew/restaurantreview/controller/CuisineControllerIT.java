package dev.drew.restaurantreview.controller;

import dev.drew.restaurantreview.service.CuisineService;

import jakarta.persistence.EntityManagerFactory;
import liquibase.integration.spring.SpringLiquibase;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.hasItems;


@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(initializers = CuisineControllerIT.DockerMysqlDataSourceInitializer.class)
public class CuisineControllerIT {

    @Autowired
    private CuisineService cuisineService;

    @Autowired
    SpringLiquibase liquibase;

    @Autowired
    EntityManagerFactory emf;

    @Container
    public static MySQLContainer container = new MySQLContainer("mysql:latest")
            .withDatabaseName("example_db")
            .withUsername("Test")
            .withPassword("Test");

    @BeforeAll
    public static void setUp(){
        container.withReuse(true);
        container.start();
    }

    @Test
    void testGetAllCuisines() throws Exception {

        // Check status code
        when().request("GET", "/api/v1/cuisines")
                .then().statusCode(200);

        // Check that expected first and last cuisines included in response
        when().request("GET", "/api/v1/cuisines")
                .then().assertThat().body("", hasItems("American", "Thai"));

    }

    @AfterAll
    public static void tearDown(){
        container.stop();
    }

    public static class DockerMysqlDataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            String jdbcUrl = container.getJdbcUrl();
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.datasource.url=" + jdbcUrl,
                    "spring.datasource.username=" + container.getUsername(),
                    "spring.datasource.password=" + container.getPassword(),
                    "spring.liquibase.change-log=classpath:/db/changelog/db.changelog-master.yaml",
                    "spring.liquibase.contexts=standard,seed"
            );
        }
    }
}
