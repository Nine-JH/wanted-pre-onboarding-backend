package me.jh9.wantedpreonboarding.docs.jwt;

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
import me.jh9.wantedpreonboarding.common.jwt.api.JwtController;
import me.jh9.wantedpreonboarding.common.jwt.api.request.RefreshAccessTokenRequest;
import me.jh9.wantedpreonboarding.common.jwt.application.JwtService;
import me.jh9.wantedpreonboarding.common.jwt.application.request.RefreshAccessTokenServiceRequest;
import me.jh9.wantedpreonboarding.common.jwt.application.response.RefreshResponse;
import me.jh9.wantedpreonboarding.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class JwtControllerDocsTest extends RestDocsSupport {

    private final JwtService jwtService = mock(JwtService.class);

    @Override
    protected Object initController() {
        return new JwtController(jwtService);
    }

    @DisplayName("accessToken 재발급")
    @Nested
    class Context_refreshAccessToken {

        @DisplayName("재발급을 할 수 있다.")
        @Test
        void _willSuccess() throws Exception {
            // given
            given(jwtService.refreshAccessToken(any(RefreshAccessTokenServiceRequest.class)))
                .willReturn(new RefreshResponse("Bearer new.access.token", "Bearer this.refresh.token"));

            RefreshAccessTokenRequest request = new RefreshAccessTokenRequest("Bearer",
                "Bearer this.refresh.token");

            // when then
            mockMvc.perform(
                post("/api/v1/auth/token")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding(StandardCharsets.UTF_8)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andDo(print())
                    .andDo(document("jwt-refreshAccessToken",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                            fieldWithPath("scope").type(JsonFieldType.STRING)
                                .description("Token SCOPE")
                                .attributes(key("constraints").value("Bearer")),
                            fieldWithPath("refreshToken").type(JsonFieldType.STRING)
                                .description("미만료 REFRESH_TOKEN")
                                .attributes(key("constraints").value(
                                    "Start With Bearer"))
                        ),
                        responseFields(
                            fieldWithPath("accessToken").type(JsonFieldType.STRING)
                                .description("재발급된 ACCESS_TOKEN"),
                            fieldWithPath("refreshToken").type(JsonFieldType.STRING)
                                .description("REFRESH_TOKEN")
                        )
                    ));
        }
    }
}
