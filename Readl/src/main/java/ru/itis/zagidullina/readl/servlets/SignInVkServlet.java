package ru.itis.zagidullina.readl.servlets;

import ru.itis.zagidullina.readl.models.Account;
import ru.itis.zagidullina.readl.services.AuthService;
import ru.itis.zagidullina.readl.services.VkService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/signInVk")
public class SignInVkServlet extends HttpServlet {

    private ServletContext servletContext;
    private VkService vkService;
    private AuthService authService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        servletContext = config.getServletContext();
        vkService = (VkService) servletContext.getAttribute("vkService");
        authService = (AuthService) servletContext.getAttribute("authService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");

        if (code != null){
            try {
                vkService.signIn(code, request, response);

                response.sendRedirect(servletContext.getContextPath() + "/profile");
            }catch (IllegalArgumentException e){
                request.setAttribute("error", e.getMessage());
                request.getRequestDispatcher("/WEB-INF/jsp/signIn.jsp").forward(request, response);
            }
        }else response.sendRedirect(vkService.getAuthorizationPath());
    }
}
