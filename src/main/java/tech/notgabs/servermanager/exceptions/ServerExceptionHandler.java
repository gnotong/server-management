package tech.notgabs.servermanager.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tech.notgabs.servermanager.dto.ResponseDTO;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static java.time.LocalDateTime.*;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ServerExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errorMap = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
        return this.createErrorResponse(BAD_REQUEST, Map.of("error", errorMap), e.toString());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseDTO> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        return this.createErrorResponse(CONFLICT, Map.of("error", e.getMostSpecificCause().getMessage()), e.toString());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ResponseDTO> handleNoSuchElementException(NoSuchElementException e) {
        return this.createErrorResponse(NOT_FOUND, Map.of("error", e.getMessage()), e.toString());
    }

    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<ResponseDTO> handleConnectException(ConnectException e) {
        return this.createErrorResponse(NOT_FOUND, Map.of("error", e.getMessage()), e.toString());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> handleUnCaughtException(Exception e) {
        return this.createErrorResponse(INTERNAL_SERVER_ERROR, Map.of("error", e.getMessage()), e.toString());
    }

    private ResponseEntity<ResponseDTO> createErrorResponse(HttpStatus status, Map<String, ?> data, String message) {
        return ResponseEntity.status(status).body(
                ResponseDTO.builder()
                        .status(status)
                        .statusCode(status.value())
                        .reason(message)
                        .timeStamp(now())
                        .data(data)
                        .build()
        );
    }
}
