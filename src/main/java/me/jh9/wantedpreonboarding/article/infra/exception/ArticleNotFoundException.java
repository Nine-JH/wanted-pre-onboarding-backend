package me.jh9.wantedpreonboarding.article.infra.exception;

import me.jh9.wantedpreonboarding.common.error.exception.ApplicationException;
import me.jh9.wantedpreonboarding.common.error.exception.ErrorCode;

public class ArticleNotFoundException extends ApplicationException {

    private static final ErrorCode errorCode = ErrorCode.A001;

    public ArticleNotFoundException() {
        super(errorCode);
    }

    public ArticleNotFoundException(String body) {
        super(errorCode, body);
    }
}
