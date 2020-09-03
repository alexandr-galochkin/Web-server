package main;


import accountService.Message;
import accountService.UserProfile;
import base.AccountService;
import base.DBService;
import accountService.AccountServiceImpl;
import base.ResourceService;
import dbService.dataSets.UsersDataSet;
import resourceService.ResourceServiceImpl;
import servlets.ResourcesServlet;
import servlets.SignInServlet;
import servlets.SignUpServlet;
import dbService.DBServiceImpl;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.WebSocketChatServlet;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;


public class Main {
    public static void main(String[] args) {
        //DBService dbService = new DBServiceImpl();
        //dbService.printConnectInfo();
        ResourceService resourceService = new ResourceServiceImpl(".\\resources");
        try {
            //dbService.addUser("me", "myPassword");
            //UserProfile user = dbService.getUser(1);
            //System.out.println(user.toString());

            //System.out.println(dbService.allMessages());
            String webDir = Main.class.getResource("/resources/public_html").toExternalForm();
            ResourceHandler resource_handler = new ResourceHandler();
            resource_handler.setResourceBase(webDir);

            //AccountService accountService = new AccountServiceImpl(dbService);

            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            //context.addServlet(new ServletHolder(new SignUpServlet(accountService)), "/signup");
            //context.addServlet(new ServletHolder(new SignInServlet(accountService)), "/signin");
            context.addServlet(new ServletHolder(new ResourcesServlet(resourceService)), "/resources");
            //context.addServlet(new ServletHolder(new WebSocketChatServlet()), "*.chat");
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
