package me.jh9.wantedpreonboarding.docs.member;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.nio.charset.StandardCharsets;
import me.jh9.wantedpreonboarding.docs.RestDocsSupport;
import me.jh9.wantedpreonboarding.member.api.MemberController;
import me.jh9.wantedpreonboarding.member.api.request.LoginRequest;
import me.jh9.wantedpreonboarding.member.api.request.SignUpRequest;
import me.jh9.wantedpreonboarding.member.application.request.LoginServiceRequest;
import me.jh9.wantedpreonboarding.member.application.request.SignUpServiceRequest;
import me.jh9.wantedpreonboarding.member.application.response.LoginResponse;
import me.jh9.wantedpreonboarding.member.application.response.LoginResponse.JwtFair;
import me.jh9.wantedpreonboarding.member.application.response.MemberResponse;
import me.jh9.wantedpreonboarding.member.application.usecase.LoginUseCase;
import me.jh9.wantedpreonboarding.member.application.usecase.SignUpUseCase;
import me.jh9.wantedpreonboarding.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class MemberControllerDocsTest extends RestDocsSupport {

    private final SignUpUseCase signUpUseCase = mock(SignUpUseCase.class);
    private final LoginUseCase loginUseCase = mock(LoginUseCase.class);

    @Override
    protected Object initController() {
        return new MemberController(loginUseCase, signUpUseCase);
    }

    @DisplayName("회원가입")
    @Nested
    class Context_signUp {

        @DisplayName("회원가입을 할 수 있다.")
        @Test
        void _willSuccess() throws Exception {
            // given
            Member member = Member.createNewMember("test@email.com", "password");
            given(signUpUseCase.signUp(any(SignUpServiceRequest.class)))
                .willReturn(
                    new MemberResponse(1L));

            SignUpRequest requestDto = new SignUpRequest("test@email.com", "password");

            // when then
            mockMvc.perform(
                    post("/api/v1/member/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print())
                .andDo(document("member-signUp",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestFields(
                        fieldWithPath("email").type(JsonFieldType.STRING).description("유저_아이디")
                            .attributes(key("constraints").value("Non_Blank, EMAIL_FORMAT")),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("유저_비밀번호")
                            .attributes(key("constraints").value("Non_Blank, 8자리_이상"))
                    ),
                    responseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("유저_식별자")
                    )
                ));
        }
    }

    @DisplayName("로그인")
    @Nested
    class Context_login {

        @DisplayName("로그인을 할 수 있다.")
        @Test
        void _willSuccess() throws Exception {
            // given
            Member member = Member.createNewMember("test@email.com", "password");
            given(loginUseCase.login(any(LoginServiceRequest.class)))
                .willReturn(
                    new LoginResponse(1L,
                        new JwtFair("Bearer access.token", "Bearer refresh.token")));

            LoginRequest requestDto = new LoginRequest("test@email.com", "password");

            // when then
            mockMvc.perform(
                    post("/api/v1/member/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andDo(document("member-login",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestFields(
                        fieldWithPath("email").type(JsonFieldType.STRING).description("유저_아이디")
                            .attributes(key("constraints").value("Non_Blank, EMAIL_FORMAT")),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("유저_비밀번호")
                            .attributes(key("constraints").value("Non_Blank, 8자리_이상"))
                    ),
                    responseFields(
                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("유저_식별자"),
                        fieldWithPath("jwtFair['accessToken']").type(JsonFieldType.STRING)
                            .description("access_token"),
                        fieldWithPath("jwtFair['refreshToken']").type(JsonFieldType.STRING)
                            .description("refresh_token")
                    )
                ));
        }
    }
}
