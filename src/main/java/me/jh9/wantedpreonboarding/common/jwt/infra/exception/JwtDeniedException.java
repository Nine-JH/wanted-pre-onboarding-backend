package me.jh9.wantedpreonboarding.common.jwt.infra.exception;

import me.jh9.wantedpreonboarding.common.error.exception.ApplicationException;
import me.jh9.wantedpreonboarding.common.error.exception.ErrorCode;

public class JwtDeniedException extends ApplicationException {

    private static final ErrorCode errorCode = ErrorCode.T002;

    public JwtDeniedException() {
        super(errorCode);
    }

    public JwtDeniedException(String body) {
        super(errorCode, body);
    }
}
