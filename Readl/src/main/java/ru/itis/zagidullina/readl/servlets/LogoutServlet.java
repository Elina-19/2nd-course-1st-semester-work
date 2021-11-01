package ru.itis.zagidullina.readl.servlets;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ru.itis.zagidullina.readl.services.AuthService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    private ServletContext servletContext;
    private AuthService authService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        servletContext = config.getServletContext();
        authService = (AuthService) servletContext.getAttribute("authService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        authService.logout(request.getSession());
        response.sendRedirect(servletContext.getContextPath() + "/main");
    }
}
