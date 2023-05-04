package tech.notgabs.servermanager.resource;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.notgabs.servermanager.dto.ServerResponseDTO;
import tech.notgabs.servermanager.enumeration.Status;
import tech.notgabs.servermanager.mappers.ServerToServerResponseMapper;
import tech.notgabs.servermanager.model.Server;
import tech.notgabs.servermanager.service.implementation.ServerServiceImpl;

import java.io.IOException;
import java.net.ConnectException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/servers")
@CrossOrigin
public class ServerResource {
    private final ServerServiceImpl serverService;
    private final ServerToServerResponseMapper serverResponseMapper;

    @GetMapping
    public ResponseEntity<List<ServerResponseDTO>> getServers(@RequestParam("limit") int limit) {
        return ResponseEntity.ok(
                serverService.list(limit)
                        .stream()
                        .filter(Objects::nonNull)
                        .map(serverResponseMapper)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<ServerResponseDTO> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
        Server server = serverService.ping(ipAddress);
        if (server.getStatus().equals(Status.SERVER_DOWN)) {
            throw new ConnectException("Ping failed");
        }
        return ResponseEntity.ok(serverResponseMapper.apply(server));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServerResponseDTO> updateServer(@PathVariable("id") Long id, @RequestBody Server server) {
        Server serverUpdated = serverService.update(server, id);
        return ResponseEntity.ok(serverResponseMapper.apply(serverUpdated));
    }

    @PostMapping
    public ResponseEntity<ServerResponseDTO> addServer(@RequestBody @Valid Server server) {
        Server newServer = serverService.create(server);
        return ResponseEntity.status(CREATED).body(serverResponseMapper.apply(newServer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteServer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(serverService.delete(id));
    }
}
