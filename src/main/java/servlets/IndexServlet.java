package servlets;

import endpoints.WorkObjectEndpoint;
import model.IndexViewModel;
import model.WorkObject;

import javax.servlet.ServletException;
import java.io.IOException;
import java.rmi.server.ExportException;
import java.sql.*;
import java.util.ArrayList;


public class IndexServlet extends javax.servlet.http.HttpServlet {
    private void doSql(String queryString) {
        Connection con = WorkObjectEndpoint.getConnection();
        if (con == null)
            return;
        try {
            Statement getQuery = con.createStatement();
            getQuery.execute(queryString);
        } catch (SQLException se) {
            System.out.println("Could not execute query");
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) {
        String queryString;
        queryString = "INSERT INTO dbo.wobjs VALUES ('" + request.getParameter("title") + "', '" + request.getParameter("group") + "', 'Sample Content', 0);";
        doSql(queryString);
    }

    // URL pattern /index.jsp?gid=2
    // View /view.jsp?tid=1234
    // Edit /edit.jsp?tid=1234
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String grp = request.getParameter("group");
        String ngrp = request.getParameter("newGroup");
        String viewGrp = "$,.";
        if(ngrp != null)
        {
            doSql("INSERT INTO dbo.wobjs VALUES ('Title', '" + ngrp + "', 'Sample content', 0);");
            viewGrp = ngrp;
        }
        else if(grp != null)
            viewGrp = grp;
        IndexViewModel viewModel = new IndexViewModel(viewGrp);
        request.setAttribute("viewModel", viewModel);
        response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
