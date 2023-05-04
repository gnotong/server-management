package tech.notgabs.servermanager.dto;

import lombok.Builder;
import tech.notgabs.servermanager.enumeration.Status;
@Builder
public record ServerResponseDTO(
        Long id,
        String name,
        String ipAddress,
        String type,
        String memory,
        Status status
){}
