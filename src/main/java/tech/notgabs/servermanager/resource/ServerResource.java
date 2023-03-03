package tech.notgabs.servermanager.resource;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.notgabs.servermanager.enumeration.Status;
import tech.notgabs.servermanager.model.Response;
import tech.notgabs.servermanager.model.Server;
import tech.notgabs.servermanager.service.implementation.ServerServiceImpl;

import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;

import static java.time.LocalDateTime.*;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/servers")
@CrossOrigin
public class ServerResource {
    private final ServerServiceImpl serverService;
    @GetMapping
    public ResponseEntity<Response> getServers(@RequestParam("limit") int limit) {
        return ResponseEntity.ok(
                Response.builder()
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(now())
                        .data(Map.of("servers", serverService.list(limit)))
                        .message("Servers retrieved")
                        .build()
        );
    }
    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
        Server server = serverService.ping(ipAddress);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("server", server))
                        .message(server.getStatus().equals(Status.SERVER_UP) ? "Ping successful" : "Ping failed")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<Response> getServer(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .status(OK)
                            .statusCode(OK.value())
                            .timeStamp(now())
                            .data(Map.of("server", serverService.get(id)))
                            .message("Server retrieved")
                            .build()
            );
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(NOT_FOUND).body(
                    Response.builder()
                            .status(NOT_FOUND)
                            .statusCode(NOT_FOUND.value())
                            .timeStamp(now())
                            .message("Server not found")
                            .developerMessage(e.getMessage())
                            .build()
            );
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Response> updateServer(@PathVariable("id") Long id, @RequestBody Server server) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .status(OK)
                            .statusCode(OK.value())
                            .timeStamp(now())
                            .data(Map.of("server", serverService.update(server, id)))
                            .message("Server updated")
                            .build()
            );
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(NOT_FOUND).body(
                    Response.builder()
                            .status(NOT_FOUND)
                            .statusCode(NOT_FOUND.value())
                            .timeStamp(now())
                            .data(Map.of("server", server))
                            .message("Server not updated")
                            .developerMessage(e.getMessage())
                            .build()
            );
        }
    }
    @PostMapping
    public ResponseEntity<Response> addServer(@RequestBody @Valid Server server) {
        return ResponseEntity.ok(
                Response.builder()
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .timeStamp(now())
                        .data(Map.of("server", serverService.create(server)))
                        .message("Server created")
                        .build()
        );
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                Response.builder()
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(now())
                        .data(Map.of("deleted", serverService.delete(id)))
                        .message("Server deleted")
                        .build()
        );
    }
}
