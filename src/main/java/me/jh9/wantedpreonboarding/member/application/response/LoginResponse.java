package me.jh9.wantedpreonboarding.member.application.response;

public record LoginResponse(
    Long memberId,
    JwtFair jwtFair
) {

    public static LoginResponse toDto(Long memberId, JwtFair jwtFair) {
        return new LoginResponse(memberId, jwtFair);
    }

    public record JwtFair(
        String accessToken,
        String refreshToken
    ) {

    }
}
