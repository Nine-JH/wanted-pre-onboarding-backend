package me.jh9.wantedpreonboarding.common.jwt.infra.exception;

public class JwtExpiredException extends RuntimeException {

    public JwtExpiredException(String msg) {
        super(msg);
    }
}
