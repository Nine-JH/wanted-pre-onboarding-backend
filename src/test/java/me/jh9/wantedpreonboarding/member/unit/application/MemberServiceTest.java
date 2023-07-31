package me.jh9.wantedpreonboarding.member.unit.application;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import me.jh9.wantedpreonboarding.member.application.MemberService;
import me.jh9.wantedpreonboarding.member.application.request.SignUpServiceRequest;
import me.jh9.wantedpreonboarding.member.application.response.MemberResponse;
import me.jh9.wantedpreonboarding.member.domain.Member;
import me.jh9.wantedpreonboarding.member.infra.MemberRepository;
import me.jh9.wantedpreonboarding.utils.MockedUnitTestSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class MemberServiceTest extends MockedUnitTestSupport {

    @InjectMocks
    MemberService memberService;

    @Mock
    MemberRepository memberRepository;

    @DisplayName("signUp(String email, String password)는")
    @Nested
    class Context_signUp {

        @DisplayName("신규 유저를 생성할 수 있다.")
        @Test
        void _willSuccess(){
            // given
            Member createdMember = Member.createNewMember("email@test.com", "password");
            SignUpServiceRequest serviceRequest = new SignUpServiceRequest("email@test.com", "password");

            given(memberRepository.save(any(Member.class))).willReturn(createdMember);

            // when
            MemberResponse result = memberService.signUp(serviceRequest);

            // then
            Assertions.assertThat(result)
                .extracting("id", "email")
                .containsExactly(null, createdMember.getEmail());
        }
    }
}
