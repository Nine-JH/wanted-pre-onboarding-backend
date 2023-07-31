package me.jh9.wantedpreonboarding.member.application;

import me.jh9.wantedpreonboarding.member.application.request.LoginServiceRequest;
import me.jh9.wantedpreonboarding.member.application.request.SignUpServiceRequest;
import me.jh9.wantedpreonboarding.member.application.response.MemberResponse;
import me.jh9.wantedpreonboarding.member.application.usecase.LoginUseCase;
import me.jh9.wantedpreonboarding.member.application.usecase.SignUpUseCase;
import me.jh9.wantedpreonboarding.member.domain.Member;
import me.jh9.wantedpreonboarding.member.infra.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class MemberService implements LoginUseCase, SignUpUseCase {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    @Override
    public MemberResponse signUp(SignUpServiceRequest request) {
        Member savedMember = memberRepository.save(
            Member.createNewMember(request.email(), request.password()));

        return MemberResponse.toDto(savedMember);
    }

    @Override
    public MemberResponse login(LoginServiceRequest request) {
        Member member = memberRepository.findByEmail(request.email())
            .orElseThrow(() -> new IllegalArgumentException("로그인 정보가 정확하지 않습니다."));

        if (isPasswordMatch(request.password(), member.getPassword())) {
            throw new IllegalArgumentException("로그인 정보가 정확하지 않습니다.");
        }
        return MemberResponse.toDto(member);
    }

    private static boolean isPasswordMatch(String targetPassword, String actualPassword) {
        return targetPassword.equals(actualPassword);
    }
}
