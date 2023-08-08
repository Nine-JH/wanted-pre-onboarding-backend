package me.jh9.wantedpreonboarding.common.error;

import me.jh9.wantedpreonboarding.common.error.exception.ApplicationException;
import me.jh9.wantedpreonboarding.common.error.exception.ErrorCode;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> bindException(BindException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(ErrorCode.B001,
                exception.getBindingResult().getAllErrors().get(0).getDefaultMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> applicationException(ApplicationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(exception.getErrorcode(), exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> dataAccessException(DataAccessException exception) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorResponse(ErrorCode.S001, exception.getMessage()));
    }


    @ExceptionHandler
    public ResponseEntity<ErrorResponse> serverException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorResponse(ErrorCode.S001, exception.getMessage()));
    }
}
