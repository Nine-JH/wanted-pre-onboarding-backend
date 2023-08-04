package me.jh9.wantedpreonboarding.article.unit.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import me.jh9.wantedpreonboarding.article.application.ArticleService;
import me.jh9.wantedpreonboarding.article.application.request.ArticleCreateServiceRequest;
import me.jh9.wantedpreonboarding.article.application.response.ArticleResponse;
import me.jh9.wantedpreonboarding.article.domain.Article;
import me.jh9.wantedpreonboarding.article.repository.ArticleRepository;
import me.jh9.wantedpreonboarding.member.infra.MemberRepository;
import me.jh9.wantedpreonboarding.utils.MockedUnitTestSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class ArticleServiceTest extends MockedUnitTestSupport {

    @InjectMocks
    private ArticleService articleService;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private MemberRepository memberRepository;

    @DisplayName("createArticle() 은")
    @Nested
    class Context_createArticle {
        @DisplayName("새로운 글을 작성할 수 있다.")
        @Test
        void _willSuccess(){
            // given
            Article article = Article.create(1L, "testTitle", "testContent");
            ArticleCreateServiceRequest serviceRequest = ArticleCreateServiceRequest
                .create(1L, "testTitle", "testContent");

            given(articleRepository.save(any(Article.class))).willReturn(article);
            given(memberRepository.existsById(anyLong())).willReturn(true);

            // when
            ArticleResponse response = articleService.createArticle(serviceRequest);

            // then
            Assertions.assertThat(response).extracting("memberId", "title", "content")
                .containsExactly(serviceRequest.memberId(), serviceRequest.title(),
                    serviceRequest.content());
        }

        @DisplayName("회원 정보를 조회할 수 없으면 IllArgumentException이 발생한다.")
        @Test
        void notExistMember_willFail(){
            // given
            Article article = Article.create(1L, "testTitle", "testContent");
            ArticleCreateServiceRequest serviceRequest = ArticleCreateServiceRequest
                .create(1L, "testTitle", "testContent");

            given(articleRepository.save(any(Article.class))).willReturn(article);
            given(memberRepository.existsById(anyLong())).willReturn(false);

            // when then
            Assertions.assertThatThrownBy(() -> articleService.createArticle(serviceRequest))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
