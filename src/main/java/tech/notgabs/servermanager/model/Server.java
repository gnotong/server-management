package tech.notgabs.servermanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.notgabs.servermanager.enumeration.Status;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Server {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(unique = true)
    @NotEmpty(message = "IP address cannot be empty or null")
    private String ipAddress;
    private String type;
    private String memory;
    private Status status;

    public void copy(Server server) {
        this.setIpAddress(server.getIpAddress());
        this.setName(server.getName());
        this.setMemory(server.getMemory());
        this.setType(server.getType());
        this.setStatus(server.getStatus());
    }
}
