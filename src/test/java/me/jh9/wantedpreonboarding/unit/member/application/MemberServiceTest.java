package me.jh9.wantedpreonboarding.unit.member.application;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import me.jh9.wantedpreonboarding.member.application.MemberService;
import me.jh9.wantedpreonboarding.member.application.request.LoginServiceRequest;
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

    @DisplayName("signUp(String email, String password)는 ")
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

    @DisplayName("login(String email, String password)는 ")
    @Nested
    class Context_login {

        @DisplayName("로그인을 할 수 있다.")
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

        @DisplayName("존재하지 않는 Email이면 로그인에 실패한다.")
        @Test
        void nowhereEmail_willFail(){
            // given
            Member createdMember = Member.createNewMember("email@test.com", "password");
            LoginServiceRequest noWhereEmailRequest = new LoginServiceRequest("email1@test.com", "password");

            given(memberRepository.save(any(Member.class))).willReturn(createdMember);

            // when then
            Assertions.assertThatThrownBy(() -> memberService.login(noWhereEmailRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("로그인 정보가 정확하지 않습니다.");
        }

        @DisplayName("비밀번호가 정확하지 않으면 로그인에 실패한다.")
        @Test
        void notMatchPassword_willFail(){
            // given
            Member createdMember = Member.createNewMember("email@test.com", "password");
            LoginServiceRequest notMatchPasswordRequest = new LoginServiceRequest("email1@test.com", "password123");

            given(memberRepository.save(any(Member.class))).willReturn(createdMember);

            // when then
            Assertions.assertThatThrownBy(() -> memberService.login(notMatchPasswordRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("로그인 정보가 정확하지 않습니다.");
        }
    }
}
