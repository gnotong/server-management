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
        return this.createResponse(OK, Map.of("servers", serverService.list(limit)), "Servers retrieved");
    }
    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
        Server server = serverService.ping(ipAddress);
        return this.createResponse(OK, Map.of("server", server), server.getStatus().equals(Status.SERVER_UP) ? "Ping successful" : "Ping failed");
    }
    @GetMapping("/{id}")
    public ResponseEntity<Response> getServer(@PathVariable("id") Long id) {
        try {
            return this.createResponse(OK, Map.of("server", serverService.get(id)), "Server retrieved");
        } catch (NoSuchElementException e) {
            return this.createErrorResponse(NOT_FOUND, e.getMessage(),"Server not found");
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Response> updateServer(@PathVariable("id") Long id, @RequestBody Server server) {
        try {
            return this.createResponse(OK, Map.of("server", serverService.update(server, id)), "Server updated");
        } catch (NoSuchElementException e) {
            return this.createErrorResponse(NOT_FOUND, e.getMessage(),"Server not updated");
        }
    }
    @PostMapping
    public ResponseEntity<Response> addServer(@RequestBody @Valid Server server) {
        return this.createResponse(CREATED, Map.of("server", serverService.create(server)), "Server created");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id) {
        try {
            return this.createResponse(OK, Map.of("server", serverService.delete(id)), "Server deleted");
        } catch (NoSuchElementException e) {
            return this.createErrorResponse(NOT_FOUND, e.getMessage(), "Server not deleted");
        }
    }
}
