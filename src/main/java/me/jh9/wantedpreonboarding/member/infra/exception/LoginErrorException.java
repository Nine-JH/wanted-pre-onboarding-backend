package me.jh9.wantedpreonboarding.member.infra.exception;

import me.jh9.wantedpreonboarding.common.error.exception.ApplicationException;
import me.jh9.wantedpreonboarding.common.error.exception.ErrorCode;

/**
 * BadCredentialException 과 같은 역할을 함.
 */
public class LoginErrorException extends ApplicationException {

    private static final ErrorCode errorCode = ErrorCode.M001;

    public LoginErrorException() {
        super(errorCode);
    }

    public LoginErrorException(String body) {
        super(errorCode, body);
    }
}
