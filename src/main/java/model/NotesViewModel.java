package model;

import java.sql.*;

public class NotesViewModel {
    private String title;
    private String group;
    private String content;
    private boolean edit;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public NotesViewModel(String tname, String gname, boolean edit) {
        this.edit = edit;
        this.group = gname;
        Connection con = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://localhost;user=sa;password=1234!@#$qwerQWER;database=WorkObjects;";
            con = DriverManager.getConnection(connectionUrl);
        } catch (ClassNotFoundException ce)
        {
            System.out.println("Error: could not load SQL driver");
            ce.printStackTrace();
        }
        catch (SQLException se)
        {
            System.out.println("Error: could not connect to SQL");
            se.printStackTrace();
        }
        String queryString = "SELECT tcontent, lck FROM wobjs WHERE gname = '" + gname + "' AND tname = '" + tname + "';";
        if(con == null)
            return;
        try {
            Statement getQuery = con.createStatement();
            ResultSet rs = getQuery.executeQuery(queryString);
            while(rs.next())
            {
                title = tname;
                content = rs.getString("tcontent");
                if(edit)
                {
                    if(rs.getBoolean("lck"))
                    {
                        this.edit = false;
                    }
                }
            }
        } catch (SQLException se) {
            System.out.println("Could not execute query");
            se.printStackTrace();
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
