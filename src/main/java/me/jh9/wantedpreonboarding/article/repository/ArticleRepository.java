package me.jh9.wantedpreonboarding.article.repository;

import me.jh9.wantedpreonboarding.article.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
