package me.jh9.wantedpreonboarding.article.unit.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import me.jh9.wantedpreonboarding.article.api.request.ArticleCreateRequest;
import me.jh9.wantedpreonboarding.utils.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class ArticleControllerTest extends ControllerTestSupport {

    @DisplayName("createArticle() 은")
    @Nested
    class Context_createArticle {

        @DisplayName("게시글을 쓸 수 있다.")
        @Test
        void _willSuccess() throws Exception {
            // given
            ArticleCreateRequest request = new ArticleCreateRequest(1L, "title",
                "this is test content");

            // when then
            mockMvc.perform(post("/api/v1/article")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
        }

        @DisplayName("title은")
        @Nested
        class Element_title {
            @DisplayName("Null은 허용하지 않는다.")
            @Test
            void isNull_willFail() throws Exception {
                // given
                ArticleCreateRequest request = new ArticleCreateRequest(1L, null,
                    "this is test content");

                // when then
                mockMvc.perform(post("/api/v1/article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
            }

            @DisplayName("공백란은 허용하지 않는다.")
            @Test
            void isBlank_willFail() throws Exception {
                // given
                ArticleCreateRequest request = new ArticleCreateRequest(1L, "         ",
                    "this is test content");

                // when then
                mockMvc.perform(post("/api/v1/article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
            }
        }

        @DisplayName("content은")
        @Nested
        class Element_content {
            @DisplayName("Null은 허용하지 않는다.")
            @Test
            void isNull_willFail() throws Exception {
                // given
                ArticleCreateRequest request = new ArticleCreateRequest(1L, "title",
                    null);

                // when then
                mockMvc.perform(post("/api/v1/article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("내용을 입력해주세요."));
            }

            @DisplayName("공백란은 허용하지 않는다.")
            @Test
            void isBlank_willFail() throws Exception {
                // given
                ArticleCreateRequest request = new ArticleCreateRequest(1L, "title",
                    "                    ");

                // when then
                mockMvc.perform(post("/api/v1/article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("내용을 입력해주세요."));
            }

            @DisplayName("내용은 20자 이상으로 작성해야 한다..")
            @Test
            void lessThan20_willFail() throws Exception {
                // given
                ArticleCreateRequest request = new ArticleCreateRequest(1L, "title",
                    "content_lessThan_20");

                // when then
                mockMvc.perform(post("/api/v1/article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("본문은 최소 20자 이상 작성해주세요."));
            }
        }
    }

}
