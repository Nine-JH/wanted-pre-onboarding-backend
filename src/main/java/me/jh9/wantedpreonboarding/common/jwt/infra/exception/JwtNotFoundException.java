package me.jh9.wantedpreonboarding.common.jwt.infra.exception;

public class JwtNotFoundException extends RuntimeException {

    public JwtNotFoundException(String msg) {
        super(msg);
    }
}
