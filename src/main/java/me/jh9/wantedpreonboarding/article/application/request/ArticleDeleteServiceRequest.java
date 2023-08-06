package me.jh9.wantedpreonboarding.article.application.request;

public record ArticleDeleteServiceRequest(
    Long memberId,
    Long articleId
) {

    public static ArticleDeleteServiceRequest create(Long memberId, Long articleId) {
        return new ArticleDeleteServiceRequest(memberId, articleId);
    }
}
