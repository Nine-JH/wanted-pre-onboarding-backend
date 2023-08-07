package me.jh9.wantedpreonboarding.unit.jwt.domain;

import static java.lang.Thread.sleep;

import java.time.Duration;
import java.util.Optional;
import me.jh9.wantedpreonboarding.common.jwt.domain.JwtEntity;
import me.jh9.wantedpreonboarding.common.jwt.domain.JwtType;
import me.jh9.wantedpreonboarding.common.jwt.infra.RedisJwtRepository;
import me.jh9.wantedpreonboarding.utils.IntegrationTestSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.redis.core.RedisTemplate;

class JwtRepositoryTest extends IntegrationTestSupport {

    private final String REFRESH_TOKEN_PREFIX = "jwt:refresh:";
    private final String BLACKLIST_TOKEN_PREFIX = "jwt:blacklist:";

    @Autowired
    RedisJwtRepository jwtRepository;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @DisplayName("save(JwtEntity jwtEntity, long expiredTime)는 ")
    @Nested
    class Context_save {

        @DisplayName("refresh token 저장에 성공한다.")
        @Test
        void saveRefreshToken_willSuccess() {
            // given
            JwtEntity refreshToken = new JwtEntity("token", JwtType.REFRESH_TOKEN);

            // when
            jwtRepository.save(refreshToken, 10000);
            String result = redisTemplate.opsForValue().get(REFRESH_TOKEN_PREFIX + "token");

            // then
            Assertions.assertThat(result).isEqualTo("dummy");
        }

        @DisplayName("blackList token 저장에 성공한다.")
        @Test
        void saveBlackListToken_willSuccess() {
            // given
            JwtEntity refreshToken = new JwtEntity("token", JwtType.BLACKLIST_TOKEN);

            // when
            jwtRepository.save(refreshToken, 10000);
            String result = redisTemplate.opsForValue().get(BLACKLIST_TOKEN_PREFIX + "token");

            // then
            Assertions.assertThat(result).isEqualTo("dummy");
        }

        @DisplayName("토큰이 없으면 실패한다.")
        @Test
        void emptyToken_willFail() {
            // given
            JwtEntity refreshToken = new JwtEntity(null, JwtType.REFRESH_TOKEN);

            // when then
            Assertions.assertThatThrownBy(() -> jwtRepository.save(refreshToken, 10000))
                .isInstanceOf(InvalidDataAccessApiUsageException.class);
        }

        @DisplayName("JWT 타입이 지정되어 있지 않으면 실패한다.")
        @Test
        void noJwtType_willFail() {
            // given
            JwtEntity refreshToken = new JwtEntity("token", null);

            // when then
            Assertions.assertThatThrownBy(() -> jwtRepository.save(refreshToken, 10000))
                .isInstanceOf(InvalidDataAccessApiUsageException.class);
        }
    }

    @DisplayName("findByJwtEntity(JwtEntity jwtEntity)는 ")
    @Nested
    class Context_findByJwtEntity {

        @DisplayName("저장된 토큰을 찾는데 성공한다.")
        @Test
        void _willSuccess() {
            // given
            JwtEntity refreshToken = new JwtEntity("token", JwtType.REFRESH_TOKEN);
            redisTemplate.opsForValue().set(REFRESH_TOKEN_PREFIX + "token", "dummy",
                Duration.ofMillis(10000));

            // when
            String findByRedis = redisTemplate.opsForValue().get(REFRESH_TOKEN_PREFIX + "token");
            Optional<String> result = jwtRepository.findByJwtEntity(refreshToken);

            // then
            Assertions.assertThat(result)
                .isNotEmpty()
                .contains(findByRedis);
        }

        @DisplayName("expired 기한이 지나면 찾지 못한다.")
        @Test
        void dataIsExpired_willFail() throws InterruptedException {
            // given
            JwtEntity refreshToken = new JwtEntity("token", JwtType.REFRESH_TOKEN);
            redisTemplate.opsForValue().set(REFRESH_TOKEN_PREFIX + "token", "dummy",
                Duration.ofMillis(1));

            // when
            sleep(100);
            Optional<String> result = jwtRepository.findByJwtEntity(refreshToken);

            // then
            Assertions.assertThat(result).isEmpty();
        }
    }

    @DisplayName("remove(JwtEntity jwtEntity)는 ")
    @Nested
    class Context_remove {

        @DisplayName("삭제에 성공한다")
        @Test
        void _willSuccess() {
            // given
            JwtEntity refreshToken = new JwtEntity("token", JwtType.REFRESH_TOKEN);
            redisTemplate.opsForValue().set(REFRESH_TOKEN_PREFIX + "token", "dummy",
                Duration.ofMillis(10000));

            // when
            jwtRepository.remove(refreshToken);

            // then
            String result = redisTemplate.opsForValue().get(BLACKLIST_TOKEN_PREFIX + "token");
            Assertions.assertThat(result).isNull();
        }
    }

}
