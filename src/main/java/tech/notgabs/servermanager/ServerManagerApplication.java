package tech.notgabs.servermanager;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import tech.notgabs.servermanager.enumeration.Status;
import tech.notgabs.servermanager.model.Server;
import tech.notgabs.servermanager.repository.ServerRepository;

import java.util.List;

@SpringBootApplication
public class ServerManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerManagerApplication.class, args);
	}

	@Bean
	CommandLineRunner run(ServerRepository repository) {
		return args -> {
			repository.saveAll(List.of(
				new Server(null, "Web Server", "192.168.1.100", "Virtual Machine", "8GB", Status.SERVER_UP),
				new Server(null, "Database Server", "192.168.1.101", "Physical Machine", "16GB", Status.SERVER_UP),
				new Server(null, "Mail Server", "192.168.1.102", "Virtual Machine", "4GB", Status.SERVER_DOWN),
				new Server(null, "File Server", "192.168.1.103", "Physical Machine", "32GB", Status.SERVER_DOWN),
				new Server(null, "Backup Server", "192.168.1.104", "Virtual Machine", "16GB", Status.SERVER_UP)
			));
		};
	}

}
