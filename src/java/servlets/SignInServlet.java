package servlets;

import base.AccountService;
import base.AdressService;
import frontend.Frontend;
import messageSystem.messages.StatusOfAuthentication;
import services.accountService.UserProfile;
import services.dbService.exceptions.DBException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static messageSystem.messages.StatusOfAuthentication.*;

public class SignInServlet extends HttpServlet {
    private final AdressService adressService;
    private final Frontend frontend;

    public SignInServlet(AdressService adressService, Frontend frontend) {
        this.adressService = adressService;
        this.frontend = frontend;
    }

    //sign in
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String sessionId = request.getRequestedSessionId();
        if (login == null || password == null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        StatusOfAuthentication status = frontend.isAuthenticated(login, password, sessionId);
        if (status == WrongPassword) {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println("Wrong password");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        if (status == UserIsNotRegistered) {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println("User is not registered");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println("Authorized: " + login);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}