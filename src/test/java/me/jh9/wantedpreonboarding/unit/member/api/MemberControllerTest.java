package me.jh9.wantedpreonboarding.unit.member.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import me.jh9.wantedpreonboarding.member.api.request.SignUpRequest;
import me.jh9.wantedpreonboarding.utils.ControllerUnitTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class MemberControllerTest extends ControllerUnitTestSupport {

    @DisplayName("signUp() 은")
    @Nested
    class Context_signUp {

        @DisplayName("회원가입을 할 수 있다.")
        @Test
        void _willSuccess() throws Exception{
            // given
            SignUpRequest request = new SignUpRequest("test@email.com", "password");

            // when then
            mockMvc.perform(post("/api/v1/member/signUp")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
        }

        @DisplayName("email 은")
        @Nested
        class Element_email {
            @DisplayName("이메일 형식이 아니면 실패한다.")
            @Test
            void notEmailFormat_willFail() throws Exception {
                // given
                SignUpRequest notEmailFormat = new SignUpRequest("testemail.com", "password");

                // when then
                mockMvc.perform(post("/api/v1/member/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(notEmailFormat)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
            }
        }

        @DisplayName("password 은")
        @Nested
        class Element_password {
            @DisplayName("8자리보다 짧으면 실패한다.")
            @Test
            void shorterThan8_willFail() throws Exception {
                // given
                SignUpRequest request = new SignUpRequest("test@email.com", "passwor");

                // when then
                mockMvc.perform(post("/api/v1/member/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
            }
        }
    }

    @DisplayName("login() 은")
    @Nested
    class Context_login {

        @DisplayName("로그인을 할 수 있다.")
        @Test
        void _willSuccess() throws Exception {
            // given
            SignUpRequest request = new SignUpRequest("test@email.com", "password");

            // when then
            mockMvc.perform(post("/api/v1/member/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
        }

        @DisplayName("email 은")
        @Nested
        class Element_email {
            @DisplayName("이메일 형식이 아니면 실패한다.")
            @Test
            void notEmailFormat_willFail() throws Exception {
                // given
                SignUpRequest request = new SignUpRequest("testemail.com", "password");

                // when then
                mockMvc.perform(post("/api/v1/member/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
            }
        }

        @DisplayName("password 은")
        @Nested
        class Element_password {
            @DisplayName("8자리보다 짧으면 실패한다.")
            @Test
            void shorterThan8_willFail() throws Exception {
                // given
                SignUpRequest request = new SignUpRequest("test@email.com", "passwor");

                // when then
                mockMvc.perform(post("/api/v1/member/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
            }
        }
    }
}
