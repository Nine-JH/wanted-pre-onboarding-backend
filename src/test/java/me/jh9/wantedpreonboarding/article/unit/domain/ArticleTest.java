package me.jh9.wantedpreonboarding.article.unit.domain;

import me.jh9.wantedpreonboarding.article.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArticleTest {

    @DisplayName("isWriter()은 memberId가 같으면 true를 반한다.")
    @Test
    void _willSuccess(){
        // given when
        Article article = Article.create(1L, "testTitle", "testContent");

        // then
        org.assertj.core.api.Assertions.assertThat(article.isWriter(1L))
            .isTrue();
    }

    @DisplayName("isWriter()은 memberId가 다르면 false를 반한다.")
    @Test
    void differentMemberId_willFail(){
        // given when
        Article article = Article.create(1L, "testTitle", "testContent");

        // then
        org.assertj.core.api.Assertions.assertThat(article.isWriter(2L))
            .isFalse();
    }
}
