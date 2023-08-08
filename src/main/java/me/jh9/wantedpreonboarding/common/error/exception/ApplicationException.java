package me.jh9.wantedpreonboarding.common.error.exception;

public class ApplicationException extends RuntimeException{

    private ErrorCode errorcode;
    private String body;

    protected ApplicationException(ErrorCode errorcode) {
        super(errorcode.getValue());
        this.errorcode = errorcode;
        this.body = errorcode.getValue();
    }

    protected ApplicationException(ErrorCode errorcode, String body) {
        super(errorcode.getValue());
        this.errorcode = errorcode;
        this.body = body;
    }

    public ErrorCode getErrorcode() {
        return errorcode;
    }

    public String getBody() {
        return body;
    }
}
