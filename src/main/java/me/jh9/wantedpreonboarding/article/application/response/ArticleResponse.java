package me.jh9.wantedpreonboarding.article.application.response;

import me.jh9.wantedpreonboarding.article.domain.Article;

public record ArticleResponse(
    Long id,
    Long memberId,
    String title,
    String content
) {

    public static ArticleResponse toResponseDto(Article article) {
        return new ArticleResponse(article.getId(), article.getMemberId(), article.getTitle(),
            article.getContent());
    }
}
