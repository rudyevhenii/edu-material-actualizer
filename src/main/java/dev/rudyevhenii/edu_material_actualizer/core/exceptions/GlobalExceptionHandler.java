package dev.rudyevhenii.edu_material_actualizer.core.exceptions;

import dev.rudyevhenii.edu_material_actualizer.core.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentTypeMismatchException ex) {
        String message = String.format("Parameter '%s' value '%s' is invalid", ex.getName(), ex.getValue());
        log.warn("Type mismatch: {}", message);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status)
                .body(buildErrorResponse(status, message));
    }

    @ExceptionHandler(DocumentInfoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(DocumentInfoNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        logErrorMessage(status, ex);
        return ResponseEntity.status(status)
                .body(buildErrorResponse(status, ex.getMessage()));
    }

    @ExceptionHandler(DocumentStatusStateException.class)
    public ResponseEntity<ErrorResponse> handleException(DocumentStatusStateException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        logErrorMessage(status, ex);
        return ResponseEntity.status(status)
                .body(buildErrorResponse(status, ex.getMessage()));
    }

    @ExceptionHandler(StorageInitializationException.class)
    public ResponseEntity<ErrorResponse> handleException(StorageInitializationException ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        logErrorMessage(status, ex);
        return ResponseEntity.status(status)
                .body(buildErrorResponse(status, ex.getMessage()));
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<ErrorResponse> handleException(FileUploadException ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        logErrorMessage(status, ex);
        return ResponseEntity.status(status)
                .body(buildErrorResponse(status, ex.getMessage()));
    }

    @ExceptionHandler(FileRetrievalException.class)
    public ResponseEntity<ErrorResponse> handleException(FileRetrievalException ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        logErrorMessage(status, ex);
        return ResponseEntity.status(status)
                .body(buildErrorResponse(status, ex.getMessage()));
    }

    @ExceptionHandler(MinioCleanupException.class)
    public ResponseEntity<ErrorResponse> handleException(MinioCleanupException ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        logErrorMessage(status, ex);
        return ResponseEntity.status(status)
                .body(buildErrorResponse(status, ex.getMessage()));
    }

    @ExceptionHandler(InstructionsReadException.class)
    public ResponseEntity<ErrorResponse> handleException(InstructionsReadException ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        logErrorMessage(status, ex);
        return ResponseEntity.status(status)
                .body(buildErrorResponse(status, ex.getMessage()));
    }

    @ExceptionHandler(ConfigurationPropertiesBindException.class)
    public ResponseEntity<ErrorResponse> handleException(ConfigurationPropertiesBindException ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        logErrorMessage(status, ex);
        return ResponseEntity.status(status)
                .body(buildErrorResponse(status, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        logErrorMessage(status, ex);
        return ResponseEntity.status(status)
                .body(buildErrorResponse(status, ex.getMessage()));
    }

    private void logErrorMessage(HttpStatus status, Exception ex) {
        log.warn("Status: {}, message: {}", status, ex.getMessage());
    }

    public ErrorResponse buildErrorResponse(HttpStatus status, String message) {
        return ErrorResponse.builder()
                .code(status.value())
                .message(message)
                .build();
    }

    public ErrorResponse buildErrorResponse(HttpStatus status, String message,
                                            List<ErrorResponse.FieldError> fieldErrors) {
        return ErrorResponse.builder()
                .code(status.value())
                .message(message)
                .errors(fieldErrors)
                .build();
    }
}
