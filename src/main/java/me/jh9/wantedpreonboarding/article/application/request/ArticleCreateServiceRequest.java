package me.jh9.wantedpreonboarding.article.application.request;

public record ArticleCreateServiceRequest(
    Long memberId,
    String title,
    String content
) {

    public static ArticleCreateServiceRequest create(Long memberId, String title, String content) {
        return new ArticleCreateServiceRequest(memberId, title, content);
    }
}
