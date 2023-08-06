package me.jh9.wantedpreonboarding.article.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ArticleUpdateRequest(
    @NotBlank(message = "title을 입력해주세요.") String title,
    @NotBlank(message = "내용을 입력해주세요.") @Size(min = 20, message = "본문은 최소 20자 이상 작성해주세요.") String content
) {

}
