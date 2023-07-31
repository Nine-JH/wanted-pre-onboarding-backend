package me.jh9.wantedpreonboarding.member.application.usecase;

import me.jh9.wantedpreonboarding.member.application.response.MemberResponse;
import me.jh9.wantedpreonboarding.member.application.request.SignUpServiceRequest;

public interface SignUpUseCase {

    MemberResponse signUp(SignUpServiceRequest request);
}
