package com.example.demo_testcontainers.users;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class PostgresContainer {

    private static final String DATABASE_NAME = "test_db";
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "postgres";

    @Container
    public static PostgreSQLContainer<?> testDB = new PostgreSQLContainer<>("postgres:15-alpine3.18")
            .withReuse(true)
            .withDatabaseName(DATABASE_NAME)
            .withUsername(USER_NAME)
            .withPassword(PASSWORD);


    static class Initialize implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.username=" + testDB.getUsername(),
                    "spring.datasource.password=" + testDB.getPassword(),
                    "spring.datasource.url=" + testDB.getJdbcUrl()
            ).applyTo(applicationContext.getEnvironment());
        }
    }
}
