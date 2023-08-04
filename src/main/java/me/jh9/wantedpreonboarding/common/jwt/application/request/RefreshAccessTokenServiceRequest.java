package me.jh9.wantedpreonboarding.common.jwt.application.request;

public record RefreshAccessTokenServiceRequest(
    String refreshToken,
    long currentTime
) {

    public static RefreshAccessTokenServiceRequest toServiceDto(String refreshToken, long currentTime) {
        return new RefreshAccessTokenServiceRequest(refreshToken, currentTime);
    }
}
