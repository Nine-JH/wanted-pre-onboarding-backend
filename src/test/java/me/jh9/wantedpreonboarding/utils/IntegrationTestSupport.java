package me.jh9.wantedpreonboarding.utils;

import com.redis.testcontainers.RedisContainer;
import me.jh9.wantedpreonboarding.common.jwt.application.JwtService;
import me.jh9.wantedpreonboarding.common.jwt.domain.JwtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@ActiveProfiles("test")
@Transactional
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class IntegrationTestSupport {

    @LocalServerPort
    protected int port;

    protected final String HOST = "http://localhost:";

    @Container
    protected static MySQLContainer<?> mySQLContainer = new MySQLContainer<>(
        DockerImageName.parse("mysql:8"))
        .withDatabaseName("wanted");

    @Container
    protected static RedisContainer redisContainer = new RedisContainer(
        DockerImageName.parse("redis:7.2-rc2-alpine")).withExposedPorts(6379);

    @Autowired
    protected TestRestTemplate testRestTemplate;

    @Autowired
    protected JwtService jwtService;

    @Autowired
    protected JwtRepository jwtRepository;
}
