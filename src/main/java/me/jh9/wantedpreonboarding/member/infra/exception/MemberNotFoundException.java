package me.jh9.wantedpreonboarding.member.infra.exception;

import me.jh9.wantedpreonboarding.common.error.exception.ApplicationException;
import me.jh9.wantedpreonboarding.common.error.exception.ErrorCode;

public class MemberNotFoundException extends ApplicationException {

    private static final ErrorCode errorCode = ErrorCode.M002;

    public MemberNotFoundException() {
        super(errorCode);
    }

    public MemberNotFoundException(String body) {
        super(errorCode, body);
    }
}
