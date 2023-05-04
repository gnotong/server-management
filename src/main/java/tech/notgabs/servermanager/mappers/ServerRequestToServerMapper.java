package tech.notgabs.servermanager.mappers;

import org.springframework.stereotype.Service;
import tech.notgabs.servermanager.model.Server;

import java.util.function.BiFunction;

@Service
public class ServerRequestToServerMapper implements BiFunction<Server, Server, Server> {
    @Override
    public Server apply(Server serverReq, Server serverToUpdate) {
        serverToUpdate.setName(serverReq.getName());
        serverToUpdate.setType(serverReq.getType());
        serverToUpdate.setMemory(serverReq.getMemory());
        serverToUpdate.setIpAddress(serverReq.getIpAddress());
        serverToUpdate.setStatus(serverReq.getStatus());

        return serverToUpdate;
    }
}
