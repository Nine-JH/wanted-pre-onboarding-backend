package me.jh9.wantedpreonboarding.article.infra.exception;

import me.jh9.wantedpreonboarding.common.error.exception.ApplicationException;
import me.jh9.wantedpreonboarding.common.error.exception.ErrorCode;

public class NoAuthArticleException extends ApplicationException {

    private static final ErrorCode errorCode = ErrorCode.A002;

    public NoAuthArticleException() {
        super(errorCode);
    }

    public NoAuthArticleException(String body) {
        super(errorCode, body);
    }
}
