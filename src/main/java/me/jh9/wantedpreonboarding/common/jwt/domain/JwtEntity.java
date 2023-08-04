package me.jh9.wantedpreonboarding.common.jwt.domain;

public record JwtEntity(
    String token,
    JwtType jwtType
) {

}
