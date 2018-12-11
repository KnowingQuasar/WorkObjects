package model;

import java.sql.*;
import java.util.ArrayList;

public class IndexViewModel {
    private String selectedGrp;
    private ArrayList<WorkObject> wobjs;
    private ArrayList<String> groups;

    public ArrayList<String> getGroups() {
        return groups;
    }

    public ArrayList<WorkObject> getWobjs() {
        return wobjs;
    }

    public String getSelectedGrp() {
        return selectedGrp;
    }

    public void setSelectedGrp(String gname) {
        this.selectedGrp = gname;
    }

    public IndexViewModel(String gname) {
        wobjs = new ArrayList<>();
        groups = new ArrayList<>();
        Connection con;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://localhost;user=sa;password=1234!@#$qwerQWER;database=WorkObjects";
            con = DriverManager.getConnection(connectionUrl);
        } catch (ClassNotFoundException ce) {
            System.out.println("Error: could not load SQL driver");
            ce.printStackTrace();
            return;
        } catch (SQLException se) {
            System.out.println("Error: could not connect to SQL");
            se.printStackTrace();
            return;
        }
        try {
            String queryString;
            Statement getQuery;
            ResultSet rs;
            if (gname.equals("$,.")) {
                queryString = "SELECT TOP 1 gname FROM dbo.wobjs";
                getQuery = con.createStatement();
                rs = getQuery.executeQuery(queryString);
                while (rs.next()) {
                    gname = rs.getString("gname");
                }
            }
            queryString = "SELECT tname, gname, tcontent, lck " +
                    "FROM dbo.wobjs WHERE gname='" + gname + "';";
            getQuery = con.createStatement();
            rs = getQuery.executeQuery(queryString);
            while (rs.next()) {
                WorkObject wobj = new WorkObject(rs.getString("tname"), rs.getString("tcontent"), rs.getBoolean("lck"));
                wobjs.add(wobj);
            }

            queryString = "SELECT DISTINCT gname FROM dbo.wobjs;";
            getQuery = con.createStatement();
            rs = getQuery.executeQuery(queryString);
            while (rs.next()) {
                if (rs.getString("gname").equals(selectedGrp))
                    continue;
                groups.add(rs.getString("gname"));
            }
        } catch (SQLException se) {
            System.out.println("Could not execute query");
            se.printStackTrace();
        }
        selectedGrp = gname;
    }

}
