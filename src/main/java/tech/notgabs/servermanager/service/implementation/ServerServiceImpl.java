package tech.notgabs.servermanager.service.implementation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tech.notgabs.servermanager.model.Server;
import tech.notgabs.servermanager.repository.ServerRepository;
import tech.notgabs.servermanager.service.ServerService;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

import static tech.notgabs.servermanager.enumeration.Status.*;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {
    private final ServerRepository serverRepository;
    @Override
    public Server get(Long id) {
        log.info("Fetching servers by id: {}", id);
        return serverRepository.findById(id).orElseThrow(() -> {throw new NoSuchElementException("No server found with id: " + id);});
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching all servers");
        return serverRepository.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Server create(Server server) {
        log.info("Creating server {}", server.getName());
        return serverRepository.save(server);
    }

    @Override
    public Server update(Server server, Long id) {
        log.info("Updating server: {}", server.getName());

        Server existingServer = serverRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Server id: " + id + " not found"));
        Server updatedServer = Server.builder()
                .id(existingServer.getId())
                .name(server.getName())
                .type(server.getType())
                .memory(server.getMemory())
                .status(server.getStatus())
                .ipAddress(server.getIpAddress())
                .build();

        return serverRepository.save(updatedServer);
    }

    @Override
    public boolean delete(Long id) {
        log.info("Deleting server id: {}", id);

        Optional<Server> server = serverRepository.findById(id);
        if (server.isEmpty()) {
            throw new NoSuchElementException("Server with id: " + id + " not found");
        }

        serverRepository.deleteById(id);

        return true;
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging server IP: {}", ipAddress);

        Optional<Server> server = serverRepository.findByIpAddress(ipAddress);
        if (server.isEmpty()) {
            throw new NoSuchElementException("Server with ip: " + ipAddress + " not found");
        }
        InetAddress address = InetAddress.getByName(ipAddress);

        server.get().setStatus(address.isReachable(5000) ? SERVER_UP : SERVER_DOWN);

        return serverRepository.save(server.get());
    }
}
