package tech.notgabs.servermanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.notgabs.servermanager.model.Server;

import java.util.Optional;

public interface ServerRepository extends JpaRepository<Server, Long> {
    Optional<Server> findByIpAddress(String ipAddress);
    @Modifying
    @Query("Update Server s set s.name = :name")
    void updateServerName(@Param(value = "name") String name);
}
