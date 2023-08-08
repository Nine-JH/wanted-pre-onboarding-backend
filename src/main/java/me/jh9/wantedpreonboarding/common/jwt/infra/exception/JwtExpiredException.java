package me.jh9.wantedpreonboarding.common.jwt.infra.exception;

import me.jh9.wantedpreonboarding.common.error.exception.ApplicationException;
import me.jh9.wantedpreonboarding.common.error.exception.ErrorCode;

public class JwtExpiredException extends ApplicationException {

    private static final ErrorCode errorCode = ErrorCode.T001;

    public JwtExpiredException() {
        super(errorCode);
    }

    public JwtExpiredException(String body) {
        super(errorCode, body);
    }
}
