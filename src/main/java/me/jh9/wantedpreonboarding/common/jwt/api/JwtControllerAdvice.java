package me.jh9.wantedpreonboarding.common.jwt.api;

import me.jh9.wantedpreonboarding.common.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "me.jh9.wantedpreonboarding.common.jwt.api")
public class JwtControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> bindException(BindException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(
                exception.getBindingResult().getAllErrors().get(0).getDefaultMessage()));
    }
}
