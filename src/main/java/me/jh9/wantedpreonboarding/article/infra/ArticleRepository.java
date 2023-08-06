package me.jh9.wantedpreonboarding.article.infra;

import java.util.List;
import me.jh9.wantedpreonboarding.article.domain.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("select a from Article a order by a.createdAt desc")
    List<Article> findAllArticles(Pageable pageable);
}
