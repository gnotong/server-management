package tech.notgabs.servermanager.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tech.notgabs.servermanager.dto.ServerErrorResponseDTO;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ServerExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ServerErrorResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errorMap = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
        return this.createErrorResponse(BAD_REQUEST, errorMap, e.toString());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ServerErrorResponseDTO> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        return this.createErrorResponse(CONFLICT, Map.of("message", e.getMostSpecificCause().getMessage()), e.toString());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ServerErrorResponseDTO> handleNoSuchElementException(NoSuchElementException e) {
        return this.createErrorResponse(NOT_FOUND, Map.of("message", e.getMessage()), e.toString());
    }

    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<ServerErrorResponseDTO> handleConnectException(ConnectException e) {
        return this.createErrorResponse(NOT_FOUND, Map.of("message", e.getMessage()), e.toString());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ServerErrorResponseDTO> handleUnCaughtException(Exception e) {
        return this.createErrorResponse(INTERNAL_SERVER_ERROR, Map.of("message", e.getMessage()), e.toString());
    }

    private ResponseEntity<ServerErrorResponseDTO> createErrorResponse(HttpStatus status, Map<String, ?> data, String message) {
        return ResponseEntity.status(status).body(
                ServerErrorResponseDTO.builder()
                        .status(status)
                        .statusCode(status.value())
                        .reason(message)
                        .timeStamp(now())
                        .errors(data)
                        .build()
        );
    }
}
