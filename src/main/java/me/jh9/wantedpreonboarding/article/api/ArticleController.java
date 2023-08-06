package me.jh9.wantedpreonboarding.article.api;

import jakarta.validation.Valid;
import java.util.List;
import me.jh9.wantedpreonboarding.article.api.request.ArticleCreateRequest;
import me.jh9.wantedpreonboarding.article.api.request.ArticleUpdateRequest;
import me.jh9.wantedpreonboarding.article.application.ArticleService;
import me.jh9.wantedpreonboarding.article.application.request.ArticleCreateServiceRequest;
import me.jh9.wantedpreonboarding.article.application.request.ArticleDeleteServiceRequest;
import me.jh9.wantedpreonboarding.article.application.request.ArticleUpdateServiceRequest;
import me.jh9.wantedpreonboarding.article.application.response.ArticleResponse;
import me.jh9.wantedpreonboarding.common.jwt.infra.JwtInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/article")
@RestController
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public ResponseEntity<ArticleResponse> createArticle(
        @RequestBody @Valid ArticleCreateRequest request, @JwtInfo Long memberId) {

        ArticleResponse response = articleService.createArticle(
            ArticleCreateServiceRequest.create(memberId, request.title(),
                request.content()));

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(response);
    }

    @PutMapping("/{articleId}")
    public ResponseEntity<ArticleResponse> updateArticle(
        @RequestBody @Valid ArticleUpdateRequest request, @PathVariable Long articleId,
        @JwtInfo Long memberId) {

        ArticleResponse response = articleService.updateArticle(
            ArticleUpdateServiceRequest.create(memberId,
                articleId, request.title(), request.content()));

        return ResponseEntity.status(HttpStatus.OK)
            .body(response);
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long articleId,
        @JwtInfo Long memberId) {

        articleService.deleteArticle(ArticleDeleteServiceRequest.create(memberId, articleId));

        return ResponseEntity.status(HttpStatus.OK)
            .build();
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleResponse> searchArticle(@PathVariable Long articleId) {

        ArticleResponse response = articleService.searchArticle(articleId);

        return ResponseEntity.status(HttpStatus.OK)
            .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ArticleResponse>> searchArticles(
        @PageableDefault Pageable pageable) {

        List<ArticleResponse> response = articleService.searchArticles(pageable);

        return ResponseEntity.status(HttpStatus.OK)
            .body(response);
    }
}
