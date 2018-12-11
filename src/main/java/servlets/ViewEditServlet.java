package servlets;

import java.io.IOException;

public class ViewEditServlet extends javax.servlet.http.HttpServlet  {
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.getRequestDispatcher("index.jsp").forward(request, response);
        String view = request.getParameter("SubmitView");
        String edit = request.getParameter("SubmitEdit");
        System.out.println("View: " + view + "\nEdit: " + edit);
    }
}




