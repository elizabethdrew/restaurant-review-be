package dev.drew.restaurantreview.service;

import dev.drew.restaurantreview.entity.UserEntity;
import jakarta.persistence.EntityManagerFactory;
import liquibase.integration.spring.SpringLiquibase;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openapitools.model.User;
import org.openapitools.model.UserInput;
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

import static org.junit.jupiter.api.Assertions.*;

import java.time.OffsetDateTime;


@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(initializers = UserServiceTest2.DockerMysqlDataSourceInitializer.class)
public class UserServiceTest2 {

    @Autowired
    private UserService userService;

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
    void testAddNewUser() {
        // Prepare input data and expected response
        UserInput input = new UserInput().username("newUser").password("password").email("test@test.com").name("My Name");
        UserEntity user = new UserEntity();
        user.setUsername(input.getUsername());
        user.setPassword(input.getPassword());
        user.setCreatedAt(OffsetDateTime.now());

        // Call the service method
        User savedUser = userService.addNewUser(input);

        // Verify the response status
        assertEquals(input.getUsername(), savedUser.getUsername());
    }

    // How do I test authorization for other methods?

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