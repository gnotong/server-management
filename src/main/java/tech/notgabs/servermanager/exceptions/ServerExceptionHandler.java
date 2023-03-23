package tech.notgabs.servermanager.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tech.notgabs.servermanager.model.ResponseDTO;

import java.net.ConnectException;
import java.util.Map;
import java.util.NoSuchElementException;

import static java.time.LocalDateTime.*;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ServerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseDTO> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        return this.createErrorResponse(
                CONFLICT,
                Map.of("server", ""),
                e.toString(),
                e.getMessage()
        );
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ResponseDTO> handleNoSuchElementException(NoSuchElementException e) {
        return this.createErrorResponse(
                NOT_FOUND,
                Map.of("server", ""),
                e.toString(),
                e.getMessage()
        );
    }

    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<ResponseDTO> handleConnectException(ConnectException e) {
        return this.createErrorResponse(
                NOT_FOUND,
                Map.of("server", ""),
                e.toString(),
                e.getMessage()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> handleUnCaughtException(Exception e) {
        HttpStatus status = HttpStatus.BAD_REQUEST.equals(HttpStatus.valueOf(e.getCause().getMessage())) ? NOT_FOUND : INTERNAL_SERVER_ERROR;
        return this.createErrorResponse(status, Map.of("server", ""), e.toString(), e.getMessage());
    }

    private ResponseEntity<ResponseDTO> createErrorResponse(HttpStatus status, Map<String, ?> data, String developerMsg, String message) {
        return ResponseEntity.status(status).body(
                ResponseDTO.builder()
                        .status(status)
                        .statusCode(status.value())
                        .developerMessage(developerMsg)
                        .reason(developerMsg)
                        .timeStamp(now())
                        .message(message)
                        .data(data)
                        .build()
        );
    }
}
