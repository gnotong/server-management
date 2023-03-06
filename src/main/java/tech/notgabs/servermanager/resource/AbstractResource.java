package tech.notgabs.servermanager.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tech.notgabs.servermanager.model.Response;
import java.util.Map;
import static java.time.LocalDateTime.now;

public class AbstractResource {
    protected ResponseEntity<Response> createResponse(HttpStatus status, Map<String, ?> data, String message) {
        return ResponseEntity.ok(
                Response.builder()
                        .status(status)
                        .statusCode(status.value())
                        .timeStamp(now())
                        .data(data)
                        .message(message)
                        .build()
        );
    }

    protected ResponseEntity<Response> createErrorResponse(HttpStatus status, String developerMsg, String message) {
        return ResponseEntity.status(status).body(
                Response.builder()
                        .status(status)
                        .statusCode(status.value())
                        .timeStamp(now())
                        .message(message)
                        .developerMessage(developerMsg)
                        .build()
        );
    }
}
