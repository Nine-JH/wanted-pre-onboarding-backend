package me.jh9.wantedpreonboarding.common.jwt.infra;

import java.time.Duration;
import java.util.Optional;
import me.jh9.wantedpreonboarding.common.jwt.domain.JwtEntity;
import me.jh9.wantedpreonboarding.common.jwt.domain.JwtRepository;
import me.jh9.wantedpreonboarding.common.jwt.domain.JwtType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisJwtRepository implements JwtRepository<JwtEntity> {
    private static final String KEY_PREFIX = "jwt:";
    private static final String REFRESH_TOKEN_PREFIX = KEY_PREFIX + "refresh:";
    private static final String BLACKLIST_TOKEN_PREFIX = KEY_PREFIX + "blacklist:";
    private static final String DUMMY_DATA = "dummy";

    private final RedisTemplate<String, String> redisTemplate;

    public RedisJwtRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void save(JwtEntity jwtEntity, long expiredTime) {
        if (jwtEntity.token() == null || jwtEntity.token().isBlank()) {
            throw new IllegalArgumentException("Token is empty");
        }

        if (jwtEntity.jwtType() == JwtType.REFRESH_TOKEN) {
            redisTemplate.opsForValue()
                .set(REFRESH_TOKEN_PREFIX + jwtEntity.token(), DUMMY_DATA, Duration.ofMillis(expiredTime));
        } else if (jwtEntity.jwtType() == JwtType.BLACKLIST_TOKEN) {
            redisTemplate.opsForValue()
                .set(BLACKLIST_TOKEN_PREFIX + jwtEntity.token(), DUMMY_DATA, Duration.ofMillis(expiredTime));
        } else {
            throw new IllegalArgumentException("ACCESS_TOKEN must not save in redis");
        }
    }

    @Override
    public Optional<String> findByJwtEntity(JwtEntity jwtEntity) {
        if (jwtEntity.jwtType() == JwtType.REFRESH_TOKEN) {
            return Optional.ofNullable(redisTemplate.opsForValue().get(REFRESH_TOKEN_PREFIX + jwtEntity.token()));
        } else if (jwtEntity.jwtType() == JwtType.BLACKLIST_TOKEN) {
            return Optional.ofNullable(redisTemplate.opsForValue().get(BLACKLIST_TOKEN_PREFIX + jwtEntity.token()));
        } else {
            throw new IllegalArgumentException("ACCESS_TOKEN must not saved in redis");
        }
    }

    @Override
    public void remove(JwtEntity jwtEntity) {
        if (jwtEntity.jwtType() == JwtType.REFRESH_TOKEN) {
            redisTemplate.opsForValue()
                .setIfPresent(REFRESH_TOKEN_PREFIX + jwtEntity.token(), DUMMY_DATA, Duration.ofMillis(1));
        } else if (jwtEntity.jwtType() == JwtType.BLACKLIST_TOKEN) {
            redisTemplate.opsForValue()
                .setIfPresent(BLACKLIST_TOKEN_PREFIX + jwtEntity.token(), DUMMY_DATA, Duration.ofMillis(1));
        } else {
            throw new IllegalArgumentException("ACCESS_TOKEN must not saved in redis");
        }
    }
}
