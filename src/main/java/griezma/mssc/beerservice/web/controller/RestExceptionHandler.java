package griezma.mssc.beerservice.web.controller;

import griezma.mssc.beerservice.web.model.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstaintViolation(ConstraintViolationException ex) {
        log.info("constraintVioloations: {} | {} | {}", ex, ex.getConstraintViolations(), ex.getMessage());

        var errorBuilder = ApiError.builder()
                .reason(ex.getConstraintViolations().stream().findFirst().get().toString());

        return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorBuilder.build());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiError> bindException(BindException e) {
        log.info("bindException: {}", e.getFieldError());
        List<String> fields = e.getFieldErrors().stream().map(fe -> fe.getField()).collect(Collectors.toList());

        var errorBuilder = ApiError.builder()
                .reason(e.getFieldError().toString());

        return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorBuilder.build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> otherException(Exception e) {
        log.info("otherException: {}", e);
        return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(ApiError.builder().reason(e.getMessage()).build());
    }
}
