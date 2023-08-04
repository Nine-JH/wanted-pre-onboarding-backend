package me.jh9.wantedpreonboarding.common.jwt.application.usecase;

import io.jsonwebtoken.Claims;

public interface VerifyUseCase {

    Claims verify(String token);

}
