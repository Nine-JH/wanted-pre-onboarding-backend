package me.jh9.wantedpreonboarding.unit.article.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import me.jh9.wantedpreonboarding.article.application.request.ArticleCreateServiceRequest;
import me.jh9.wantedpreonboarding.article.application.request.ArticleDeleteServiceRequest;
import me.jh9.wantedpreonboarding.article.application.request.ArticleUpdateServiceRequest;
import me.jh9.wantedpreonboarding.article.application.response.ArticleResponse;
import me.jh9.wantedpreonboarding.article.domain.Article;
import me.jh9.wantedpreonboarding.member.domain.Member;
import me.jh9.wantedpreonboarding.utils.UnitTestSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ArticleServiceTest extends UnitTestSupport {

    @DisplayName("createArticle() 은")
    @Nested
    class Context_createArticle {

        @DisplayName("새로운 글을 작성할 수 있다.")
        @Test
        void _willSuccess() {
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

        @DisplayName("회원 정보를 조회할 수 없으면 IllegalArgument 예외가 발생한다.")
        @Test
        void notExistMember_willFail() {
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

    @DisplayName("searchArticle(Long articleId) 은")
    @Nested
    class Context_searchArticle {

        @DisplayName("단일 게시글을 조회할 수 있다.")
        @Test
        void _willSuccess() {
            // given
            Article article = Article.create(1L, "testTitle", "testContent");
            given(articleRepository.findById(anyLong())).willReturn(Optional.of(article));

            // when
            ArticleResponse response = articleService.searchArticle(1L);

            // then
            Assertions.assertThat(response).extracting("memberId", "title", "content")
                .containsExactly(1L, "testTitle", "testContent");
        }

        @DisplayName("해당 ID를 가지는 게시글이 존재하지 않으면 IllegalArgument 예외가 발생한다.")
        @Test
        void notExistArticle_willFail() {
            // given
            given(articleRepository.findById(anyLong())).willReturn(Optional.empty());

            // when then
            Assertions.assertThatThrownBy(() -> articleService.searchArticle(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 요청, 게시글이 없습니다.");
        }
    }

    @DisplayName("searchArticles() 은")
    @Nested
    class Context_searchArticles {

    }

    @DisplayName("updateArticle(ArticleUpdateServiceRequest serviceRequest) 은")
    @Nested
    class Context_updateArticle {

        @DisplayName("단일 게시글을 수정할 수 있다.")
        @Test
        void _willSuccess() {
            // given
            Article article = Article.create(1L, "testTitle", "testContent");
            Member testMember = Member.createNewMember("test@email.com", "password");
            given(memberRepository.existsById(anyLong())).willReturn(true);
            given(articleRepository.findById(anyLong())).willReturn(Optional.of(article));

            ArticleUpdateServiceRequest request = new ArticleUpdateServiceRequest(1L, 1L,
                "newTitle", "newContent");

            // when
            ArticleResponse response = articleService.updateArticle(request);

            // then
            Assertions.assertThat(response).extracting("memberId", "title", "content")
                .containsExactly(1L, request.title(), request.content());
        }

        @DisplayName("수정하려는 게시글의 정보가 DB에 없으면 IllegalArgumentException이 발생한다.")
        @Test
        void articleNotExist_willFail() {
            // given
            Member testMember = Member.createNewMember("test@email.com", "password");
            given(memberRepository.existsById(anyLong())).willReturn(true);
            given(articleRepository.findById(anyLong())).willReturn(Optional.empty());

            ArticleUpdateServiceRequest request = new ArticleUpdateServiceRequest(1L, 1L,
                "newTitle", "newContent");

            // when then
            Assertions.assertThatThrownBy(() -> articleService.updateArticle(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 요청, 게시글이 없습니다.");
        }

        @DisplayName("게시글 작성자가 아니면 IllegalArgumentException이 발생한다. ")
        @Test
        void notArticleWriter_willFail() {
            // given
            Article article = Article.create(1L, "testTitle", "testContent");
            Member testMember = Member.createNewMember("test@email.com", "password");
            given(memberRepository.existsById(anyLong())).willReturn(true);
            given(articleRepository.findById(anyLong())).willReturn(Optional.of(article));

            ArticleUpdateServiceRequest request = new ArticleUpdateServiceRequest(2L, 1L,
                "newTitle", "newContent");

            // when then
            Assertions.assertThatThrownBy(() -> articleService.updateArticle(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("권한이 없습니다.");
        }

        @DisplayName("수정하려는 사용자의 회원 정보가 DB에 없으면 실패한다.")
        @Test
        void memberNotExist_willFail() {
            // given
            Member testMember = Member.createNewMember("test@email.com", "password");
            given(memberRepository.existsById(anyLong())).willReturn(false);

            ArticleUpdateServiceRequest request = new ArticleUpdateServiceRequest(1L, 1L,
                "newTitle", "newContent");

            // when then
            Assertions.assertThatThrownBy(() -> articleService.updateArticle(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 요청, 회원 정보가 없습니다.");
        }
    }

    @DisplayName("deleteArticle(ArticleDeleteServiceRequest serviceRequest) 은")
    @Nested
    class Context_deleteArticle {

        @DisplayName("단일 게시글을 삭제할 수 있다.")
        @Test
        void _willSuccess() {
            // given
            Article article = Article.create(1L, "testTitle", "testContent");
            Member testMember = Member.createNewMember("test@email.com", "password");
            given(memberRepository.existsById(anyLong())).willReturn(true);
            given(articleRepository.findById(anyLong())).willReturn(Optional.of(article));

            ArticleDeleteServiceRequest request = new ArticleDeleteServiceRequest(1L, 1L);

            // when
            articleService.deleteArticle(request);

            // then
            Assertions.assertThat(article.isDeleted()).isTrue();
        }

        @DisplayName("삭제하려는 게시글의 정보가 DB에 없으면 IllegalArgumentException이 발생한다.")
        @Test
        void articleNotExist_willFail() {
            // given
            Member testMember = Member.createNewMember("test@email.com", "password");
            given(memberRepository.existsById(anyLong())).willReturn(true);
            given(articleRepository.findById(anyLong())).willReturn(Optional.empty());

            ArticleDeleteServiceRequest request = new ArticleDeleteServiceRequest(1L, 1L);

            // when then
            Assertions.assertThatThrownBy(() -> articleService.deleteArticle(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 요청, 게시글이 없습니다.");
        }

        @DisplayName("게시글 작성자가 아니면 IllegalArgumentException이 발생한다. ")
        @Test
        void notArticleWriter_willFail() {
            // given
            Article article = Article.create(1L, "testTitle", "testContent");
            Member testMember = Member.createNewMember("test@email.com", "password");
            given(memberRepository.existsById(anyLong())).willReturn(true);
            given(articleRepository.findById(anyLong())).willReturn(Optional.of(article));

            ArticleDeleteServiceRequest request = new ArticleDeleteServiceRequest(2L, 1L);

            // when then
            Assertions.assertThatThrownBy(() -> articleService.deleteArticle(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("권한이 없습니다.");
            Assertions.assertThat(article.isDeleted()).isFalse();
        }

        @DisplayName("삭제하려는 사용자의 회원 정보가 DB에 없으면 실패한다.")
        @Test
        void memberNotExist_willFail() {
            // given
            Member testMember = Member.createNewMember("test@email.com", "password");
            given(memberRepository.existsById(anyLong())).willReturn(false);

            ArticleDeleteServiceRequest request = new ArticleDeleteServiceRequest(1L, 1L);

            // when then
            Assertions.assertThatThrownBy(() -> articleService.deleteArticle(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 요청, 회원 정보가 없습니다.");
        }
    }


}
