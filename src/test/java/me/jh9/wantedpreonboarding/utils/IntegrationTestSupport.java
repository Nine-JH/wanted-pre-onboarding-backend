package me.jh9.wantedpreonboarding.utils;

import java.io.File;
import me.jh9.wantedpreonboarding.common.jwt.application.JwtService;
import me.jh9.wantedpreonboarding.common.jwt.domain.JwtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles("test")
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class IntegrationTestSupport {

    @LocalServerPort
    protected int port;

    protected final String HOST = "http://localhost:";

    @Container
    public static DockerComposeContainer dockerCompose =
        new DockerComposeContainer(new File("src/test/resources/compose-test.yaml"))
            .withLocalCompose(true);

    @Autowired
    protected TestRestTemplate testRestTemplate;

    @Autowired
    protected JwtService jwtService;

    @Autowired
    protected JwtRepository jwtRepository;
}
