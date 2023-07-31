package me.jh9.wantedpreonboarding.utils;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Transactional
@ActiveProfiles("test")
@Testcontainers
@SpringBootTest
public abstract class IntegrationTestSupport {

    @Container
    protected static MySQLContainer<?> container = new MySQLContainer<>(
        DockerImageName.parse("mysql:8"))
        .withDatabaseName("wanted");
}
