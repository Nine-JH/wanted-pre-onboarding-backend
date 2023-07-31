package me.jh9.wantedpreonboarding.member.application.usecase;

import me.jh9.wantedpreonboarding.member.application.request.LoginServiceRequest;
import me.jh9.wantedpreonboarding.member.application.response.MemberResponse;

public interface LoginUseCase {

    MemberResponse login(LoginServiceRequest request);
}
