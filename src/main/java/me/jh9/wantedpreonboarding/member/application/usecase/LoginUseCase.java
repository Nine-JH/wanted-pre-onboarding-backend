package me.jh9.wantedpreonboarding.member.application.usecase;

import me.jh9.wantedpreonboarding.member.application.request.LoginServiceRequest;
import me.jh9.wantedpreonboarding.member.application.response.LoginResponse;

public interface LoginUseCase {

    LoginResponse login(LoginServiceRequest request);
}
