package tech.notgabs.servermanager.mappers;

import org.springframework.stereotype.Service;
import tech.notgabs.servermanager.model.Server;
import tech.notgabs.servermanager.dto.ServerResponseDTO;
import java.util.function.Function;
@Service
public class ServerToServerResponseMapper implements Function<Server, ServerResponseDTO> {
    @Override
    public ServerResponseDTO apply(Server server) {
        return ServerResponseDTO.builder()
                .status(server.getStatus())
                .type(server.getType())
                .ipAddress(server.getIpAddress())
                .memory(server.getMemory())
                .name(server.getName())
                .id(server.getId())
                .build();
    }
}
