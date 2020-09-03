package main;

import base.*;
import frontend.Frontend;
import messageSystem.MessageSystemImpl;
import services.accountService.UserProfile;
import services.accountService.AccountServiceImpl;
import services.adressService.AdressServiceImpl;
import services.resourceService.ResourceServiceImpl;
import servlets.SignInServlet;
import servlets.SignUpServlet;
import services.dbService.DBServiceImpl;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;


public class Main {
    public static void main(String[] args) {
        MessageSystem messageSystem = new MessageSystemImpl();
        AdressService adressService = new AdressServiceImpl(messageSystem);
        Frontend frontend = new Frontend(messageSystem, adressService);
        ResourceService resourceService = new ResourceServiceImpl(".\\resources");
        try {
            //services.dbService.addUser("me", "myPassword");
            UserProfile user = adressService.getDBService().getUser(1);
            System.out.println(user.toString());

            String webDir = Main.class.getResource("/resources/public_html").toExternalForm();
            ResourceHandler resource_handler = new ResourceHandler();
            resource_handler.setResourceBase(webDir);

            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            context.addServlet(new ServletHolder(new SignUpServlet(adressService.getAccountService())), "/signup");
            context.addServlet(new ServletHolder(new SignInServlet(adressService, frontend)), "/signin");
            HandlerList handlers = new HandlerList();
            handlers.setHandlers(new Handler[]{resource_handler, context});

            Server server = new Server(8080);
            server.setHandler(handlers);

            server.start();
            System.out.println("Server started");
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
