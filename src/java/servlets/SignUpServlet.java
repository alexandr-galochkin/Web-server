package servlets;

import base.AccountService;
import services.accountService.UserProfile;
import services.dbService.exceptions.DBException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServlet extends HttpServlet {
    private final AccountService accountService;

    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    //sign up
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if (login == null || password == null) {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println("The user already exists.");
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        try {
            if (accountService.contains(login)){
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().println("The user already exists.");
                response.setStatus(HttpServletResponse.SC_OK);
                return;
            }
        }catch (DBException e){
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println("The user already exists.");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        UserProfile newUser = new UserProfile(3,login, password);
        try {
            accountService.addNewUser(newUser);
        }catch (DBException e){
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println("The user already exists.");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println("The user is registered.");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
