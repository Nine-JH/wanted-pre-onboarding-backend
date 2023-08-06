package me.jh9.wantedpreonboarding.utils;

import me.jh9.wantedpreonboarding.common.jwt.application.JwtService;
import me.jh9.wantedpreonboarding.common.jwt.domain.JwtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public abstract class JwtTestSupport {

    protected final String BEARER_TOKEN_PREFIX = "Bearer ";

    @Value("${jwt.secret-key}")
    protected String secretKey;

    @Value("${jwt.expired-time.access}")
    protected String accessExpiredTime;

    @Value("${jwt.expired-time.refresh}")
    protected String refreshExpiredTime;

    @Autowired
    protected JwtService jwtService;

    @MockBean
    protected JwtRepository jwtRepository;
}
