package dev.drew.restaurantreview;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.MySQLContainer;

public class DockerMysqlDataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                applicationContext,
                "spring.datasource.url=" + GlobalTestContainer.container.getJdbcUrl(),
                "spring.datasource.username=" + GlobalTestContainer.container.getUsername(),
                "spring.datasource.password=" + GlobalTestContainer.container.getPassword(),
                "spring.liquibase.change-log=classpath:/db/changelog/db.changelog-master.yaml",
                "spring.liquibase.contexts=standard,seed",
                "server.port=8081"
        );

        System.out.println("spring.datasource.url=" + GlobalTestContainer.container.getJdbcUrl());
    }
}