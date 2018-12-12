package endpoints;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import coders.MessageDecoder;
import coders.MessageEncoder;
import messages.*;

@ServerEndpoint(value = "/docws/{group}/{wobj}", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class WorkObjectEndpoint {
    // Per client vars:
    // Session info (client IP, etc)
    private Session session;
    private boolean isEditor = false;
    private String gname;
    private String tname;
    private Connection con;

    // Shared vars:
    // Set of all connected clients
    private static final Set<WorkObjectEndpoint> workObjectEndpoints = new CopyOnWriteArraySet<>();


    @OnOpen
    public void onOpen(Session session, @PathParam("group") String group, @PathParam("wobj") String wobj) {
        this.session = session;
        try {
            gname = URLDecoder.decode(group, StandardCharsets.UTF_8.toString());
            tname = URLDecoder.decode(wobj, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException uee) {
            onError(uee);
        }
        String qs = session.getQueryString();
        String[] queries = qs.split("=");
        if (queries[1].equals("true")) {
            con = getConnection();
            boolean lock = false;
            if (con == null) {
                onError(new Exception());
                return;
            }
            try {
                PreparedStatement stmt = con.prepareStatement("SELECT lck FROM wobjs WHERE gname=? AND tname=?;");
                stmt.setString(1, gname);
                stmt.setString(2, tname);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    lock = rs.getBoolean("lck");
                }
            } catch (SQLException se) {
                System.out.println("Could not execute query");
                se.printStackTrace();
            }
            if (lock) {
                onError(new Exception());
            } else {
                isEditor = true;
                try {
                    PreparedStatement stmt = con.prepareStatement("UPDATE wobjs SET lck=1 WHERE gname=? AND tname=?;");
                    stmt.setString(1, gname);
                    stmt.setString(2, tname);
                    stmt.executeUpdate();
                } catch(SQLException se) {
                    se.printStackTrace();
                }
            }
        } else {
            isEditor = false;
        }
        workObjectEndpoints.add(this);
    }

    @OnMessage
    public void onMessage(Message message) {
        if(isEditor) {
            String content = ((ContentMessage)message).getContent();
            try {
                PreparedStatement stmt = con.prepareStatement("UPDATE wobjs SET tcontent=? WHERE gname=? AND tname=?;");
                stmt.setString(1, content);
                stmt.setString(2, gname);
                stmt.setString(3, tname);
                stmt.executeUpdate();
            } catch(SQLException se) {
                se.printStackTrace();
            }
            broadcast(new ContentMessage(content));
        } else {
            onError(new Exception());
        }
    }

    @OnError
    public void onError(Throwable throwable) {
        unLock();
        send(new ErrorMessage());
        workObjectEndpoints.remove(this);
    }

    @OnClose
    public void onClose() {
        unLock();
        workObjectEndpoints.remove(this);
    }

    private void unLock() {
        if(isEditor) {
            try {
                PreparedStatement stmt = con.prepareStatement("UPDATE wobjs SET lck=0 WHERE gname=? AND tname=?;");
                stmt.setString(1, gname);
                stmt.setString(2, tname);
                stmt.executeUpdate();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // localhost
            //String connectionUrl = "jdbc:sqlserver://localhost;user=sa;password=1234!@#$qwerQWER;database=WorkObjects;";
            // Azure
            String connectionUrl = "jdbc:sqlserver://3750projects.database.windows.net:1433;user=admin3750;password=1234!@#$qwerQWER;database=WorkObjects;";
            con = DriverManager.getConnection(connectionUrl);
        } catch (ClassNotFoundException ce) {
            System.out.println("Error: could not load SQL driver");
            ce.printStackTrace();
        } catch (SQLException se) {
            System.out.println("Error: could not connect to SQL");
            se.printStackTrace();
        }
        return con;
    }

    private void send(Message msg)
    {
        try{
            session.getBasicRemote().sendObject(msg);
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }

    private void broadcast(Message msg) {
        for (WorkObjectEndpoint endpoint : workObjectEndpoints) {
            try {
                if(endpoint.session.getId().equals(session.getId()) || !(endpoint.gname.equals(this.gname) && endpoint.tname.equals(this.tname)))
                    continue;
                endpoint.session.getBasicRemote()
                        .sendObject(msg);
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        }
    }
}
