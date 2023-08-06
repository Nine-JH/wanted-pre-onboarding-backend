package me.jh9.wantedpreonboarding.utils.config;

import me.jh9.wantedpreonboarding.utils.fake.JwtFakeService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class UnitTestConfig {

    @Bean
    @Primary
    public JwtFakeService jwtFakeService() {
        return new JwtFakeService();
    }
}
