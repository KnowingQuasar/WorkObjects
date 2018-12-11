package endpoints;

import java.io.IOException;
import java.sql.Connection;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import messages.*;

@ServerEndpoint(value = "/docws/{group}/{wobj}")
public class WorkObjectEndpoint {
    // Per client vars:
    // Session info (client IP, etc)
    private Session session;

    // Shared vars:
    // Set of all connected clients
    private static final Set<WorkObjectEndpoint> workObjectEndpoints = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("group") String group, @PathParam("wobj") String wobj) {
        String qs = session.getQueryString();
        Connection con;

    }

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
