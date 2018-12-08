package servlets;

import model.IndexViewModel;

import java.io.IOException;

public class IndexServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        IndexViewModel viewModel = new IndexViewModel("test");
        request.setAttribute("viewModel", viewModel);
        response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
