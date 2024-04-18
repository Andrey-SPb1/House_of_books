package org.andrey.integration.service;

import org.andrey.integration.annotation.IT;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;

@IT
@Sql({
        "classpath:sql/test-data.sql"
})
public abstract class IntegrationTestBase {

    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16.2");

    @BeforeAll
    static void runContainer() {
        container
//                .withEnv("LANG", "ru_RU.UTF-8")
//                .withEnv("LANGUAGE", "ru_RU:ru")
//                .withEnv("LC_LANG", "ru_RU.UTF-8")
//                .withEnv("LC_ALL", "ru_RU.UTF-8")
//                .withEnv("LC_COLLATE", "Russian_Russia.866")
                .start();
    }

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
    }
}
