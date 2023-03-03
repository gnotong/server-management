package tech.notgabs.servermanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.notgabs.servermanager.model.Server;

public interface ServerRepository extends JpaRepository<Server, Long> {
    Server findByIpAddress(String ipAddress);
}
