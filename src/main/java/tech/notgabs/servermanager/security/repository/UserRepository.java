package tech.notgabs.servermanager.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.notgabs.servermanager.security.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
