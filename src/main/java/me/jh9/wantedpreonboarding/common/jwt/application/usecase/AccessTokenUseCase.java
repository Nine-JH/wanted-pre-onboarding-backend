package me.jh9.wantedpreonboarding.common.jwt.application.usecase;

import me.jh9.wantedpreonboarding.common.jwt.application.request.RefreshAccessTokenServiceRequest;
import me.jh9.wantedpreonboarding.common.jwt.application.response.RefreshResponse;

public interface AccessTokenUseCase {

    String createAccessToken(String subject, long currentTime);

    RefreshResponse refreshAccessToken(RefreshAccessTokenServiceRequest serviceRequest);
}
