package tech.notgabs.servermanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.notgabs.servermanager.model.Server;

import java.util.Optional;

public interface ServerRepository extends JpaRepository<Server, Long> {
    Optional<Server> findByIpAddress(String ipAddress);
}
