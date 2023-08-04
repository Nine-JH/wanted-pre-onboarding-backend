package me.jh9.wantedpreonboarding.common.jwt.domain;

import java.util.Optional;

public interface JwtRepository<T> {

    void save(T t, long expiredTime);

    Optional<String> findByJwtEntity(T t);

    void remove(T t);
}
