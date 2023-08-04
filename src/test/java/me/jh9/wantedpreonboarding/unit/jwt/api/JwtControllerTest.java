package me.jh9.wantedpreonboarding.unit.jwt.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import me.jh9.wantedpreonboarding.common.jwt.api.request.RefreshAccessTokenRequest;
import me.jh9.wantedpreonboarding.utils.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class JwtControllerTest extends ControllerTestSupport {

    @DisplayName("refreshAccessToken(RefreshAccessTokenRequest request)은")
    @Nested
    class Context_refreshAccessToken {

        @DisplayName("accessToken 재발급을 받을 수 있다.")
        @Test
        void _willSuccess() throws Exception {
            // given
            RefreshAccessTokenRequest request = new RefreshAccessTokenRequest(
                "Bearer asdasd_=.asda_sd.vjkl==xjv");

            // when then
            mockMvc.perform(post("/api/v1/auth/token")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
        }

        @DisplayName("Element refreshToken은 ")
        @Nested
        class Element_refreshToken {

            @DisplayName("'Bearer '로 시작하지 않으면 실패한다.")
            @Test
            void notStartWithOwnPrefix_willFail() throws Exception {
                // given
                RefreshAccessTokenRequest request = new RefreshAccessTokenRequest(
                    "bad.RefreshToke.n");

                // when then
                mockMvc.perform(post("/api/v1/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
            }

            @DisplayName("JWT 표준 포맷 ('.' 2개)을 지키지 않으면 실패한다. .")
            @Test
            void notContainsTwoPeriod_willFail() throws Exception {
                // given
                RefreshAccessTokenRequest request = new RefreshAccessTokenRequest(
                    "Bearer badRefreshToken");

                // when then
                mockMvc.perform(post("/api/v1/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
            }

            @DisplayName("JWT 표준 포맷 (Base64Encoding)을 지키지 않으면 실패한다. .")
            @Test
            void notBase64EncodedToken_willFail() throws Exception {
                // given
                RefreshAccessTokenRequest request = new RefreshAccessTokenRequest(
                    "Bearer thisIs.not!@#$.base6()*4");

                // when then
                mockMvc.perform(post("/api/v1/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
            }
        }
    }
}
