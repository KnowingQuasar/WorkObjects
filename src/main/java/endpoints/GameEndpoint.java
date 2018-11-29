package endpoints;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import messages.*;
import model.*;

@ServerEndpoint(value = "/game")
public class GameEndpoint {
    // Per client vars:
    // Session info (client IP, etc)
    private Session session;

    // Shared vars:
    // Set of all connected clients
    private static final Set<GameEndpoint> gameEndpoints = new CopyOnWriteArraySet<>();

    // Send msg to both clients
    private static void broadcast(Message msg) {
        for (GameEndpoint endpoint : gameEndpoints) {
            try {
                endpoint.session.getBasicRemote()
                        .sendObject(msg);
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        }
    }
}
