package tech.notgabs.servermanager.resource;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.notgabs.servermanager.enumeration.Status;
import tech.notgabs.servermanager.model.ResponseDTO;
import tech.notgabs.servermanager.model.Server;
import tech.notgabs.servermanager.service.implementation.ServerServiceImpl;

import java.io.IOException;
import java.net.ConnectException;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/servers")
@CrossOrigin
public class ServerResource extends AbstractResource {
    private final ServerServiceImpl serverService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getServers(@RequestParam("limit") int limit) {
        return this.createResponse(OK, Map.of("servers", serverService.list(limit)), "Servers retrieved");
    }

    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<ResponseDTO> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
        Server server = serverService.ping(ipAddress);
        if (server.getStatus().equals(Status.SERVER_DOWN)) {
            throw new ConnectException("Ping failed");
        }
        return this.createResponse(OK, Map.of("server", server), "Ping successful");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateServer(@PathVariable("id") Long id, @RequestBody Server server) {
        return this.createResponse(OK, Map.of("server", serverService.update(server, id)), "Server updated");
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addServer(@RequestBody @Valid Server server) {
        return this.createResponse(CREATED, Map.of("server", serverService.create(server)), "Server created");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteServer(@PathVariable("id") Long id) {
        return this.createResponse(OK, Map.of("deleted", serverService.delete(11L)), "Server deleted");
    }
}
