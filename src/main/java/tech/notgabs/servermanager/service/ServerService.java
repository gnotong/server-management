package tech.notgabs.servermanager.service;

import tech.notgabs.servermanager.model.Server;

import java.io.IOException;
import java.util.Collection;

public interface ServerService {
    Server get(Long id);
    Collection<Server> list(int limit);
    Server create(Server server);
    Server update(Server server, Long id);
    boolean delete(Long id);
    Server ping(String ipAddress) throws IOException;
}
