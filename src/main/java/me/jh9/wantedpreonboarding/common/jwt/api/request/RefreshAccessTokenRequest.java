package me.jh9.wantedpreonboarding.common.jwt.api.request;

import jakarta.validation.constraints.Pattern;

public record RefreshAccessTokenRequest(
    @Pattern(regexp = "Bearer") String scope,
    @Pattern(regexp = "^Bearer [A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_.+/=]*$", message = "JWT 표준 포맷을 지켜주세요") String refreshToken
) {

}
