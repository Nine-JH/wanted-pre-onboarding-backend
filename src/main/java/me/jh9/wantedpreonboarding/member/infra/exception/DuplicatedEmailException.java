package me.jh9.wantedpreonboarding.member.infra.exception;

import me.jh9.wantedpreonboarding.common.error.exception.ApplicationException;
import me.jh9.wantedpreonboarding.common.error.exception.ErrorCode;

public class DuplicatedEmailException extends ApplicationException {

    private static final ErrorCode errorCode = ErrorCode.M003;

    public DuplicatedEmailException() {
        super(errorCode);
    }
}
