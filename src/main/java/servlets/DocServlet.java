package servlets;

import model.NotesViewModel;

import java.io.IOException;
public class DocServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) {

    }

    // URL pattern /index.jsp?gid=2
    // View /view.jsp?tid=1234
    // Edit /edit.jsp?tid=1234
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String tname = request.getParameter("wobj");
        String gname = request.getParameter("group");
        String action = request.getParameter("action");
        if (tname == null || gname == null || action == null) {
            request.getRequestDispatcher("Error.jsp").forward(request, response);
            return;
        }
        if (!action.equals("view") && !action.equals("edit")) {
            request.getRequestDispatcher("Error.jsp").forward(request, response);
            return;
        }
        NotesViewModel nvm = new NotesViewModel(tname, gname, action.equals("edit"));
        if(action.equals("edit") && !nvm.isEdit()) {
            request.getRequestDispatcher("Error.jsp").forward(request, response);
            return;
        }
        request.setAttribute("viewModel", nvm);
        request.getRequestDispatcher("notes.jsp").forward(request, response);
    }
}
