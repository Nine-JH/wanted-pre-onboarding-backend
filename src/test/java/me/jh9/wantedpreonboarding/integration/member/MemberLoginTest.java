package me.jh9.wantedpreonboarding.integration.member;

import java.util.Optional;
import me.jh9.wantedpreonboarding.member.api.request.LoginRequest;
import me.jh9.wantedpreonboarding.member.api.request.SignUpRequest;
import me.jh9.wantedpreonboarding.member.application.MemberService;
import me.jh9.wantedpreonboarding.member.application.request.SignUpServiceRequest;
import me.jh9.wantedpreonboarding.member.application.response.MemberResponse;
import me.jh9.wantedpreonboarding.member.domain.Member;
import me.jh9.wantedpreonboarding.member.infra.MemberRepository;
import me.jh9.wantedpreonboarding.utils.IntegrationTestSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class MemberLoginTest extends IntegrationTestSupport {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("로그인을 성공할 수 있다.")
    @Test
    void signUpTest_willSuccess() {
        // given
        MemberResponse expectedResponse = memberService.signUp(
            new SignUpServiceRequest("test@email.com", "myPassword1"));

        LoginRequest request = new LoginRequest("test@email.com", "myPassword1");
        String signUpURL = HOST + port + "/api/v1/member/login";

        // when
        ResponseEntity<MemberResponse> responseEntity = testRestTemplate
            .postForEntity(signUpURL, request, MemberResponse.class);

        // then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isEqualTo(expectedResponse);
        Assertions.assertThat(responseEntity.getHeaders().get(HttpHeaders.AUTHORIZATION))
            .isNotNull();
        Assertions.assertThat(responseEntity.getHeaders().get("refresh-token")).isNotNull();
    }

    @DisplayName("아이디가 이메일 포맷이 아니면 실패한다.")
    @Test
    void signUpTest_willFail() {
        // given
        String notEmailFormat = "myEmailtest.com";
        LoginRequest badRequest = new LoginRequest(notEmailFormat, "myPassword1");

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

    @DisplayName("Password Encoding이 되어 있지 않으면 로그인에 실패한다.")
    @Test
    void notPasswordEncoded_willFail() {
        // given
        Member newMember = Member.createNewMember("myEmail@test.com", "myPassword1");
        SignUpRequest request = new SignUpRequest("myEmail@test.com", "myPassword1");
        String signUpURL = HOST + port + "/api/v1/member/login";

        // when
        ResponseEntity<String> responseEntity = testRestTemplate.postForEntity(
            signUpURL, request, String.class);

        Optional<Member> optionalMember = memberRepository.findByEmail(request.email());

        // then
        Assertions.assertThat(optionalMember).isNotPresent();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//        Assertions.assertThat(responseEntity.getBody()).isEqualTo("");
    }
}
