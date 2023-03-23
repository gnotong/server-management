package tech.notgabs.servermanager.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tech.notgabs.servermanager.model.ResponseDTO;
import java.util.Map;
import static java.time.LocalDateTime.now;

public class AbstractResource {
    protected ResponseEntity<ResponseDTO> createResponse(HttpStatus status, Map<String, ?> data, String message) {
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(status)
                        .statusCode(status.value())
                        .timeStamp(now())
                        .data(data)
                        .message(message)
                        .data(data)
                        .developerMessage(message)
                        .reason(message)
                        .build()
        );
    }
}
