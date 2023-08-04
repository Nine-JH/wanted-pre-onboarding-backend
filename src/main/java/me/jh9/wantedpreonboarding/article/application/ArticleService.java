package me.jh9.wantedpreonboarding.article.application;

import me.jh9.wantedpreonboarding.article.application.request.ArticleCreateServiceRequest;
import me.jh9.wantedpreonboarding.article.application.response.ArticleResponse;
import me.jh9.wantedpreonboarding.article.domain.Article;
import me.jh9.wantedpreonboarding.article.repository.ArticleRepository;
import me.jh9.wantedpreonboarding.member.infra.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    public ArticleService(ArticleRepository articleRepository, MemberRepository memberRepository) {
        this.articleRepository = articleRepository;
        this.memberRepository = memberRepository;
    }

    public ArticleResponse createArticle(ArticleCreateServiceRequest serviceRequest) {

        validateMemberExists(serviceRequest);

        Article article = Article.create(serviceRequest.memberId(), serviceRequest.title(),
            serviceRequest.content());

        Article savedArticle = articleRepository.save(article);

        return ArticleResponse.toResponseDto(savedArticle);
    }

    private void validateMemberExists(ArticleCreateServiceRequest serviceRequest) {
        if (!memberRepository.existsById(serviceRequest.memberId())) {
            throw new IllegalArgumentException("잘못된 요청, 회원 정보가 없습니다.");
        }
    }

}
