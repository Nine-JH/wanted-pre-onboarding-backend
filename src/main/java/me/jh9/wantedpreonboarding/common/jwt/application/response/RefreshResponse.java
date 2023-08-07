package me.jh9.wantedpreonboarding.common.jwt.application.response;

public record RefreshResponse(
    String accessToken,
    String refreshToken
) {

}
