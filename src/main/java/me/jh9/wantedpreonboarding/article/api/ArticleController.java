package me.jh9.wantedpreonboarding.article.api;

import jakarta.validation.Valid;
import me.jh9.wantedpreonboarding.article.api.request.ArticleCreateRequest;
import me.jh9.wantedpreonboarding.article.application.ArticleService;
import me.jh9.wantedpreonboarding.article.application.request.ArticleCreateServiceRequest;
import me.jh9.wantedpreonboarding.article.application.response.ArticleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<ArticleResponse> createArticle(@RequestBody @Valid ArticleCreateRequest request) {
        ArticleResponse response = articleService.createArticle(
            ArticleCreateServiceRequest.create(request.memberId(), request.title(),
                request.content()));

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(response);
    }
}
