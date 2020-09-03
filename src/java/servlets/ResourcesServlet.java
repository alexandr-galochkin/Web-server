package servlets;

import base.ResourceService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResourcesServlet extends HttpServlet {
    private final ResourceService resourceService;

    public ResourcesServlet(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String path = request.getParameter("path");
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(resourceService.createObjectFromFile(path).toString());
        response.setStatus(HttpServletResponse.SC_OK);

    }
}