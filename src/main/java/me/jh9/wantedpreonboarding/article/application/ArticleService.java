package me.jh9.wantedpreonboarding.article.application;

import java.util.List;
import me.jh9.wantedpreonboarding.article.application.request.ArticleCreateServiceRequest;
import me.jh9.wantedpreonboarding.article.application.request.ArticleDeleteServiceRequest;
import me.jh9.wantedpreonboarding.article.application.request.ArticleUpdateServiceRequest;
import me.jh9.wantedpreonboarding.article.application.response.ArticleResponse;
import me.jh9.wantedpreonboarding.article.domain.Article;
import me.jh9.wantedpreonboarding.article.infra.ArticleRepository;
import me.jh9.wantedpreonboarding.member.infra.MemberRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    public ArticleService(ArticleRepository articleRepository, MemberRepository memberRepository) {
        this.articleRepository = articleRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public ArticleResponse createArticle(ArticleCreateServiceRequest serviceRequest) {

        validateMemberExists(serviceRequest.memberId());
        Article article = Article.create(serviceRequest.memberId(), serviceRequest.title(),
            serviceRequest.content());

        Article savedArticle = articleRepository.save(article);

        return ArticleResponse.toResponseDto(savedArticle);
    }

    public ArticleResponse searchArticle(Long articleId) {

        return ArticleResponse.toResponseDto(findOrThrowById(articleId));
    }

    private Article findOrThrowById(Long articleId) {

        return articleRepository.findById(articleId)
            .orElseThrow(() -> new IllegalArgumentException("잘못된 요청, 게시글이 없습니다."));
    }

    @Transactional
    public List<ArticleResponse> searchArticles(Pageable pageable) {

        return articleRepository.findAllArticles(pageable).stream()
            .map(ArticleResponse::toResponseDto)
            .toList();
    }

    @Transactional
    public ArticleResponse updateArticle(ArticleUpdateServiceRequest serviceRequest) {

        validateMemberExists(serviceRequest.memberId());
        Article article = findOrThrowById(serviceRequest.articleId());
        validateArticleWriter(serviceRequest.memberId(), article.getMemberId());

        article.update(serviceRequest.title(), serviceRequest.content());

        return ArticleResponse.toResponseDto(article);
    }

    @Transactional
    public void deleteArticle(ArticleDeleteServiceRequest serviceRequest) {

        validateMemberExists(serviceRequest.memberId());
        Article article = findOrThrowById(serviceRequest.articleId());
        validateArticleWriter(serviceRequest.memberId(), article.getMemberId());

        article.delete();
    }

    private void validateMemberExists(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new IllegalArgumentException("잘못된 요청, 회원 정보가 없습니다.");
        }
    }

    private void validateArticleWriter(long memberId, long writerId) {
        if (memberId != writerId) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
    }

}
