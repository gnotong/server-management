package tech.notgabs.servermanager.resource;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.notgabs.servermanager.enumeration.Status;
import tech.notgabs.servermanager.model.Response;
import tech.notgabs.servermanager.model.Server;
import tech.notgabs.servermanager.service.implementation.ServerServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/servers")
@CrossOrigin
public class ServerResource extends AbstractResource {
    private final ServerServiceImpl serverService;
    @GetMapping
    public ResponseEntity<Response> getServers(@RequestParam("limit") int limit) {
        try {
            return this.createResponse(OK, Map.of("servers", serverService.list(limit)), "Servers retrieved");
        } catch (Exception e) {
            return this.createErrorResponse(BAD_REQUEST, Map.of("servers", new ArrayList<>()), e.getMessage(),  "Could not retrieve the servers");
        }
    }
    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
        try {
            Server server = serverService.ping(ipAddress);
            return this.createResponse(OK, Map.of("server", server), server.getStatus().equals(Status.SERVER_UP) ? "Ping successful" : "Ping failed");
        } catch (Exception e) {
            return this.createErrorResponse(BAD_REQUEST, Map.of("server", new Server()), e.getMessage(),  "Could not contact the server");
        }

    }
    @PutMapping("/{id}")
    public ResponseEntity<Response> updateServer(@PathVariable("id") Long id, @RequestBody Server server) {
        try {
            return this.createResponse(OK, Map.of("server", serverService.update(server, id)), "Server updated");
        } catch (NoSuchElementException e) {
            return this.createErrorResponse(NOT_FOUND, Map.of("server", new ArrayList<>()), e.getMessage(),"Server not updated");
        }
    }
    @PostMapping
    public ResponseEntity<Response> addServer(@RequestBody @Valid Server server) {
        try {
            return this.createResponse(CREATED, Map.of("server", serverService.create(server)), "Server created");
        } catch (Exception e) {
            return this.createErrorResponse(BAD_REQUEST, Map.of("server", new Server()), e.getMessage(),  "Server could not be created");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id) {
        try {
            return this.createResponse(OK, Map.of("deleted", serverService.delete(id)), "Server deleted");
        } catch (NoSuchElementException e) {
            return this.createErrorResponse(NOT_FOUND, Map.of("deleted", false), e.getMessage(),  "Cannot delete the server: not found");
        }
    }
}
