package endpoints;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import messages.*;

@ServerEndpoint(value = "/game")
public class WorkObjectEndpoint {
    // Per client vars:
    // Session info (client IP, etc)
    private Session session;

    // Shared vars:
    // Set of all connected clients
    private static final Set<WorkObjectEndpoint> workObjectEndpoints = new CopyOnWriteArraySet<>();

    // Send msg to both clients
    private static void broadcast(Message msg) {
        for (WorkObjectEndpoint endpoint : workObjectEndpoints) {
            try {
                endpoint.session.getBasicRemote()
                        .sendObject(msg);
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        }
    }
}
