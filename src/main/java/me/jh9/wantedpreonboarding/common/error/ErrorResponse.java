package me.jh9.wantedpreonboarding.common.error;

import me.jh9.wantedpreonboarding.common.error.exception.ErrorCode;

public record ErrorResponse(
    ErrorCode errorCode,
    String message
) {

}
