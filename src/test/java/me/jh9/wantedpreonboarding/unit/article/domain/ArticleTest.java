package me.jh9.wantedpreonboarding.unit.article.domain;

import me.jh9.wantedpreonboarding.article.domain.Article;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ArticleTest {

    @DisplayName("isWriter()는")
    @Nested
    class Context_isWriter {
        @DisplayName("memberId가 같으면 true를 반한다.")
        @Test
        void _willSuccess(){
            // given when
            Article article = Article.create(1L, "testTitle", "testContent");

            // then
            org.assertj.core.api.Assertions.assertThat(article.isWriter(1L))
                .isTrue();
        }

        @DisplayName("memberId가 다르면 false를 반한다.")
        @Test
        void differentMemberId_willFail(){
            // given when
            Article article = Article.create(1L, "testTitle", "testContent");

            // then
            org.assertj.core.api.Assertions.assertThat(article.isWriter(2L))
                .isFalse();
        }
    }

    @DisplayName("update()는")
    @Nested
    class Context_update {

        @DisplayName("title, content를 업데이트 할 수 있다.")
        @Test
        void _willSuccess(){
            // given
            Article article = Article.create(1L, "testTitle", "testContent");

            // when
            article.update("newTestTitle", "newTestContent");

            // then
            Assertions.assertThat(article).extracting("title", "content")
                .containsExactly("newTestTitle", "newTestContent");
        }

        @DisplayName("title이 null이거나 공백이라면 이를 제외하고 업데이트된다.")
        @Test
        void titleNullOrBlank_onlyContentUpdate_willSuccess(){
            // given
            Article article = Article.create(1L, "testTitle", "testContent");

            // when
            article.update("   ", "newTestContent");

            // then
            Assertions.assertThat(article).extracting("title", "content")
                .containsExactly("testTitle", "newTestContent");
        }

        @DisplayName("content가 null이거나 공백이라면 이를 제외하고 업데이트된다.")
        @Test
        void contentNullOrBlank_onlyContentUpdate_willSuccess(){
            // given
            Article article = Article.create(1L, "testTitle", "testContent");

            // when
            article.update("newTestTitle", "   ");

            // then
            Assertions.assertThat(article).extracting("title", "content")
                .containsExactly("newTestTitle", "testContent");
        }
    }
}
