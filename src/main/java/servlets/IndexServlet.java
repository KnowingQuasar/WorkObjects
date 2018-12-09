package servlets;

import model.IndexViewModel;
import model.WorkObject;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class IndexServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) {

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
        String queryString = "INSERT INTO dbo.wobjs VALUES ('Title', '" + request.getParameter("group") + "', '');";
        if(con == null)
            return;
        try {
            Statement getQuery = con.createStatement();
            getQuery.execute(queryString);
        } catch (SQLException se) {
            System.out.println("Could not execute query");
            se.printStackTrace();
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String grp = request.getParameter("group");
        IndexViewModel viewModel = new IndexViewModel(grp == null ? "$,." : grp);
        request.setAttribute("viewModel", viewModel);
        response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        request.getRequestDispatcher("index.jsp").forward(request, response);
        String sub = request.getParameter("Submit");
        String view = request.getParameter("SubmitView");
        String edit = request.getParameter("SubmitEdit");
        System.out.println("New Group: " + sub + "\nView: " + view + "\nEdit: " + edit);
    }
}
