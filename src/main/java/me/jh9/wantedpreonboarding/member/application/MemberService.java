package me.jh9.wantedpreonboarding.member.application;

import me.jh9.wantedpreonboarding.member.application.request.SignUpServiceRequest;
import me.jh9.wantedpreonboarding.member.application.response.MemberResponse;
import me.jh9.wantedpreonboarding.member.application.usecase.SignUpUseCase;
import me.jh9.wantedpreonboarding.member.domain.Member;
import me.jh9.wantedpreonboarding.member.infra.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class MemberService implements SignUpUseCase {

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
}
