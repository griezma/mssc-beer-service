package griezma.mssc.beerservice.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstaintViolation(ConstraintViolationException ex) {
        log.info("constraintVioloations: {} | {} | {}", ex, ex.getConstraintViolations(), ex.getMessage());

        var errorBuilder = ApiError.builder()
                .reason(ex.getConstraintViolations().stream()
                        .findFirst()
                        .map(ConstraintViolation::toString)
                        .orElse("no data"));

        return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorBuilder.build());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiError> bindException(BindException e) {
        log.debug("bindException: {}", e.getFieldError());

        String reason = e.getFieldError() != null
                ? e.getFieldError().toString()
                : e.toString();

        var errorBuilder = ApiError.builder()
                .reason(reason);

        return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorBuilder.build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> otherException(Exception e) {
        log.debug("otherException: {}", e.toString());
        return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(ApiError.builder().reason(e.getMessage()).build());
    }
}
