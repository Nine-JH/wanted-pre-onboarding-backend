package me.jh9.wantedpreonboarding.common.jwt.api;

import me.jh9.wantedpreonboarding.common.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class JwtControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> jwtDenied(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(exception.getMessage()));
    }
}
