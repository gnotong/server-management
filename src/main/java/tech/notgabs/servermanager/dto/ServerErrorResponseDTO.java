package tech.notgabs.servermanager.dto;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
public record ServerErrorResponseDTO(
        LocalDateTime timeStamp,
        int statusCode,
        HttpStatus status,
        String reason,
        Map<String, ?> errors
) {

}
