package me.jh9.wantedpreonboarding.common.jwt.infra.exception;

public class JwtDeniedException extends RuntimeException {

    public JwtDeniedException(String msg) {
        super(msg);
    }
}
