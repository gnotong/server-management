package tech.notgabs.servermanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import tech.notgabs.servermanager.enumeration.Status;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Server {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty(message = "Name cannot be empty or null")
    private String name;
    @Column(unique = true)
    @NotEmpty(message = "IP address cannot be empty or null")
    private String ipAddress;
    @NotEmpty(message = "Type cannot be empty or null")
    private String type;
    @NotEmpty(message = "RAM cannot be empty or null")
    private String memory;
    private Status status;
}
