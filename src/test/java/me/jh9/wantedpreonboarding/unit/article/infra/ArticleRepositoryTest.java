package me.jh9.wantedpreonboarding.unit.article.infra;

import java.util.ArrayList;
import java.util.List;
import me.jh9.wantedpreonboarding.article.domain.Article;
import me.jh9.wantedpreonboarding.article.infra.ArticleRepository;
import me.jh9.wantedpreonboarding.utils.IntegrationTestSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

class ArticleRepositoryTest extends IntegrationTestSupport {

    // 현재 조회기능만 테스트하기 때문에 static을 감안해 사용합니다.
    // 만약 CUD 기능이 추가된다면, 해당 static을 사용하면 안됩니다.
    private static List<Article> articles;

    @Autowired
    private ArticleRepository articleRepository;

    static {
        articles = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            articles.add(Article.create((long) (i + 1), "title" + i, "content" + i));
        }
    }

    @DisplayName("findAllArticles(Pageable) 은")
    @Nested
    class Context_findAllArticles {

        @DisplayName("Pageable을 사용해 페이징 처리를 할 수 있다.")
        @ValueSource(ints = {5, 10, 15, 20})
        @ParameterizedTest
        void _willSuccess(int size) {
            // given
            articleRepository.saveAll(articles);

            // when
            List<Article> result = articleRepository.findAllArticles(Pageable.ofSize(size));

            // then
            Assertions.assertThat(result).hasSize(size);
        }
    }
}
