package me.jh9.wantedpreonboarding.utils.fake;

import io.jsonwebtoken.Claims;
import me.jh9.wantedpreonboarding.common.jwt.application.request.RefreshAccessTokenServiceRequest;
import me.jh9.wantedpreonboarding.common.jwt.application.usecase.AccessTokenUseCase;
import me.jh9.wantedpreonboarding.common.jwt.application.usecase.RefreshTokenUseCase;
import me.jh9.wantedpreonboarding.common.jwt.application.usecase.VerifyUseCase;

/**
 *  MemberService UnitTest에서 JWT를 설정하는데 너무 많은 시간을 투자해야 하기 때문에 Fake 객체를 생성.
 *  실제로 JWT가 사용되는 모습을 보려면 통합테스트 or 시나리오(인수)테스트를 확인하세요
 */
public class JwtFakeService implements AccessTokenUseCase, RefreshTokenUseCase, VerifyUseCase {

    public static final String FAKE_ACCESS_TOKEN = "Bearer test.access.token";
    public static final String FAKE_REFRESH_TOKEN = "Bearer test.refresh.token";

    @Override
    public String createAccessToken(String subject, long currentTime) {
        return FAKE_ACCESS_TOKEN;
    }

    @Override
    public String refreshAccessToken(RefreshAccessTokenServiceRequest serviceRequest) {
        return FAKE_ACCESS_TOKEN;
    }

    @Override
    public String createRefreshToken(String subject, long currentTime) {
        return FAKE_REFRESH_TOKEN;
    }

    @Override
    public Claims verify(String token) {
        return null;
    }
}
