package me.jh9.wantedpreonboarding.article.application.request;

public record ArticleUpdateServiceRequest(
    Long memberId,
    Long articleId,
    String title,
    String content
) {

    public static ArticleUpdateServiceRequest create(Long memberId, Long articleId, String title, String content) {
        return new ArticleUpdateServiceRequest(memberId, articleId, title, content);
    }
}
