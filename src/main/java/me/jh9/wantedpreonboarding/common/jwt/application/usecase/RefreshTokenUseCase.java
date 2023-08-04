package me.jh9.wantedpreonboarding.common.jwt.application.usecase;

public interface RefreshTokenUseCase {
    String createRefreshToken(String subject, long currentTime);
}
