package me.jh9.wantedpreonboarding.docs.article;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.nio.charset.StandardCharsets;
import java.util.List;
import me.jh9.wantedpreonboarding.article.api.ArticleController;
import me.jh9.wantedpreonboarding.article.api.request.ArticleCreateRequest;
import me.jh9.wantedpreonboarding.article.api.request.ArticleUpdateRequest;
import me.jh9.wantedpreonboarding.article.application.ArticleService;
import me.jh9.wantedpreonboarding.article.application.request.ArticleCreateServiceRequest;
import me.jh9.wantedpreonboarding.article.application.request.ArticleDeleteServiceRequest;
import me.jh9.wantedpreonboarding.article.application.request.ArticleUpdateServiceRequest;
import me.jh9.wantedpreonboarding.article.application.response.ArticleResponse;
import me.jh9.wantedpreonboarding.article.domain.Article;
import me.jh9.wantedpreonboarding.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class ArticleControllerDocsTest extends RestDocsSupport {

    private final ArticleService articleService = mock(ArticleService.class);

    @Override
    protected Object initController() {
        return new ArticleController(articleService);
    }

    @DisplayName("글쓰기")
    @Nested
    class Context_createArticle {

        @DisplayName("글쓰기를 할 수 있다.")
        @Test
        void _willSuccess() throws Exception {
            // given
            Article article = Article.create(1L, "title", "20자리 이상의 본문을 작성해야 합니다.");
            given(articleService.createArticle(any(ArticleCreateServiceRequest.class)))
                .willReturn(new ArticleResponse(1L, 1L, "title", "20자리 이상의 본문을 작성해야 합니다."));

            ArticleCreateRequest request = new ArticleCreateRequest("title",
                "20자리 이상의 본문을 작성해야 합니다.");

            // when then
            mockMvc.perform(
                    post("/api/v1/article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer JWT.ACCESS.TOKEN")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print())
                .andDo(document("article-create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                            fieldWithPath("title").type(JsonFieldType.STRING).description("게시글_제목")
                                .attributes(key("constraints").value("Non_Blank")),
                            fieldWithPath("content").type(JsonFieldType.STRING).description("게시글_내용")
                                .attributes(key("constraints").value("Non_Blank, 20자리_이상"))
                        ),
                        responseFields(
                            fieldWithPath("id").type(JsonFieldType.NUMBER).description("유저_식별자"),
                            fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("작성자_아이디"),
                            fieldWithPath("title").type(JsonFieldType.STRING).description("게시글_제목"),
                            fieldWithPath("content").type(JsonFieldType.STRING).description("게시글_본문"))
                    )
                );
        }
    }

    @DisplayName("글 수정")
    @Nested
    class Context_updateArticle {

        @DisplayName("글 수정을 할 수 있다.")
        @Test
        void _willSuccess() throws Exception {
            // given
            given(articleService.updateArticle(any(ArticleUpdateServiceRequest.class)))
                .willReturn(new ArticleResponse(1L, 1L, "title", "20자리 이상의 본문을 작성해야 합니다."));

            ArticleUpdateRequest request = new ArticleUpdateRequest("title",
                "20자리 이상의 본문을 작성해야 합니다.");

            // when then
            mockMvc.perform(
                    RestDocumentationRequestBuilders.put("/api/v1/article/{articleId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer JWT.ACCESS.TOKEN")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andDo(document("article-update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("articleId").description("게시글_아이디")),
                        requestFields(
                            fieldWithPath("title").type(JsonFieldType.STRING).description("수정할_게시글_제목")
                                .attributes(key("constraints").value("Non_Blank")),
                            fieldWithPath("content").type(JsonFieldType.STRING)
                                .description("수정할_게시글_내용")
                                .attributes(key("constraints").value("Non_Blank, 20자리_이상"))
                        ),
                        responseFields(
                            fieldWithPath("id").type(JsonFieldType.NUMBER).description("유저_식별자"),
                            fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("작성자_아이디"),
                            fieldWithPath("title").type(JsonFieldType.STRING).description("게시글_제목"),
                            fieldWithPath("content").type(JsonFieldType.STRING).description("게시글_본문"))
                    )
                );
        }
    }

    @DisplayName("글 삭제")
    @Nested
    class Context_deleteArticle {

        @DisplayName("글 삭제를 할 수 있다.")
        @Test
        void _willSuccess() throws Exception {
            // given
            doNothing().when(articleService).deleteArticle(any(ArticleDeleteServiceRequest.class));

            // when then
            mockMvc.perform(
                    RestDocumentationRequestBuilders.delete("/api/v1/article/{articleId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer JWT.ACCESS.TOKEN"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andDo(document("article-delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("articleId").description("게시글_아이디"))
                    )
                );
        }
    }

    @DisplayName("글 목록 조회")
    @Nested
    class Context_searchArticles {

        @DisplayName("글 목록 조회를 할 수 있다.")
        @Test
        void _willSuccess() throws Exception {
            // given
            given(articleService.searchArticles(any()))
                .willReturn(List.of(
                    new ArticleResponse(1L, 1L, "제목1", "첫 번째 게시글 내용입니다."),
                    new ArticleResponse(2L, 1L, "제목2", "두 번째 게시글 내용입니다."),
                    new ArticleResponse(3L, 2L, "제목3", "세 번째 게시글 내용입니다."),
                    new ArticleResponse(4L, 3L, "제목4", "네 번째 게시글 내용입니다.")
                ));
            // when then
            mockMvc.perform(
                    RestDocumentationRequestBuilders.get("/api/v1/article")
                        .queryParam("page", "1")
                        .queryParam("size", "5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andDo(document("article-list-search",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                            parameterWithName("page").description("페이지 번호(zero based)"),
                            parameterWithName("size").description("조회할 개수")
                        ),
                        responseFields(
                            fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("유저_식별자"),
                            fieldWithPath("[].memberId").type(JsonFieldType.NUMBER)
                                .description("작성자_아이디"),
                            fieldWithPath("[].title").type(JsonFieldType.STRING).description("게시글_제목"),
                            fieldWithPath("[].content").type(JsonFieldType.STRING)
                                .description("게시글_본문"))
                    )
                );
        }
    }

    @DisplayName("글 상세 조회")
    @Nested
    class Context_searchArticle {

        @DisplayName("글 상세 조회를 할 수 있다.")
        @Test
        void _willSuccess() throws Exception {
            // given
            given(articleService.searchArticle(anyLong()))
                .willReturn(new ArticleResponse(1L, 1L, "title", "20자리 이상의 본문을 작성해야 합니다."));

            // when then
            mockMvc.perform(
                    RestDocumentationRequestBuilders.get("/api/v1/article/{articleId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andDo(document("article-search",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("articleId").description("게시글_아이디")),
                        responseFields(
                            fieldWithPath("id").type(JsonFieldType.NUMBER).description("유저_식별자"),
                            fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("작성자_아이디"),
                            fieldWithPath("title").type(JsonFieldType.STRING).description("게시글_제목"),
                            fieldWithPath("content").type(JsonFieldType.STRING).description("게시글_본문"))
                    )
                );
        }
    }
}
