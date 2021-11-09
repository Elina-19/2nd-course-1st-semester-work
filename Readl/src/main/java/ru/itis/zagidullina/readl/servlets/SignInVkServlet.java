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
                Account account = vkService.signIn(code);

                HttpSession session = request.getSession(true);
                //session.setAttribute("isAuthenticated", true);

                account.setUuid(session.getId());
                authService.updateUuid(account.getEmail(), session.getId());
                session.setAttribute("account", account);
                Cookie cookie = new Cookie("token", account.getToken());
                response.addCookie(cookie);

                response.sendRedirect(servletContext.getContextPath() + "/profile");
            }catch (IllegalArgumentException e){
                request.setAttribute("error", e.getMessage());
                request.getRequestDispatcher("/WEB-INF/jsp/signIn.jsp").forward(request, response);
            }
        }else response.sendRedirect(vkService.getAuthorizationPath());
    }
}
