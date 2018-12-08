package model;

import java.sql.*;
import java.util.ArrayList;

public class IndexViewModel {
    private String grpname;
    private ArrayList<WorkObject> wobjs;

    public ArrayList<WorkObject> getWobjs() {
        return wobjs;
    }

    public String getGrpname() {
        return grpname;
    }

    public void setGname(String gname) {
        this.grpname = gname;
    }

    public IndexViewModel(String gname)
    {
        grpname = gname;
        wobjs = new ArrayList<>();
        Connection con = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://localhost;user=sa;password=1234!@#$qwerQWER;database=WorkObjects";
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
        String queryString = "SELECT tname, gname, tcontent " +
                "FROM dbo.wobjs WHERE gname='" + gname + "';";
        if(con == null)
            return;
        try {
            Statement getQuery = con.createStatement();
            ResultSet rs = getQuery.executeQuery(queryString);
            while(rs.next()) {
                WorkObject wobj = new WorkObject(rs.getString("tname"), rs.getString("tcontent"));
                wobjs.add(wobj);
            }
        } catch (SQLException se) {
            System.out.println("Could not execute query");
            se.printStackTrace();
        }
    }

}
