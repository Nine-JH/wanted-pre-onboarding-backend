package me.jh9.wantedpreonboarding.integration.member;

import java.util.Optional;
import me.jh9.wantedpreonboarding.member.api.request.SignUpRequest;
import me.jh9.wantedpreonboarding.member.application.MemberService;
import me.jh9.wantedpreonboarding.member.application.response.MemberResponse;
import me.jh9.wantedpreonboarding.member.domain.Member;
import me.jh9.wantedpreonboarding.member.infra.MemberRepository;
import me.jh9.wantedpreonboarding.utils.IntegrationTestSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class MemberSignUpTest extends IntegrationTestSupport {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원가입을 성공할 수 있다.")
    @Test
    void signUpTest_willSuccess() {
        // given
        SignUpRequest request = new SignUpRequest("myEmail@test.com", "myPassword1");
        String signUpURL = HOST + port + "/api/v1/member/signUp";

        // when
        ResponseEntity<MemberResponse> responseEntity = testRestTemplate.postForEntity(
            signUpURL, request, MemberResponse.class);

        Member createdMember = memberRepository.findByEmail(request.email()).get();
        MemberResponse expectedResponse = MemberResponse.toDto(createdMember);

        // then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(responseEntity.getBody()).isEqualTo(expectedResponse);
    }

    @DisplayName("아이디가 이메일 포맷이 아니면 실패한다.")
    @Test
    void signUpTest_willFail() {
        // given
        String notEmailFormat = "myEmailtest.com";
        SignUpRequest badRequest = new SignUpRequest(notEmailFormat, "myPassword1");

        String signUpURL = HOST + port + "/api/v1/member/signUp";

        // when
        ResponseEntity<String> responseEntity = testRestTemplate.postForEntity(
            signUpURL, badRequest, String.class);

        Optional<Member> optionalMember = memberRepository.findByEmail(badRequest.email());

        // then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(responseEntity.getBody()).isEqualTo("{\"message\":\"아이디는 이메일 형식어야 합니다.\"}");
        Assertions.assertThat(optionalMember).isNotPresent();
    }
}
