package me.jh9.wantedpreonboarding.common.jwt.application.usecase;

import me.jh9.wantedpreonboarding.common.jwt.application.request.RefreshAccessTokenServiceRequest;

public interface AccessTokenUseCase {

    String createAccessToken(String subject, long currentTime);

    String refreshAccessToken(RefreshAccessTokenServiceRequest serviceRequest);
}
