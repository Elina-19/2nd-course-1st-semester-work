package ru.itis.zagidullina.readl.servlets;

import ru.itis.zagidullina.readl.models.Account;
import ru.itis.zagidullina.readl.services.AccountsService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private ServletContext servletContext;
    private AccountsService usersService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        servletContext = config.getServletContext();
        usersService = (AccountsService)servletContext.getAttribute("usersService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        request.setAttribute("account", account);
        request.getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(request, response);
    }
}
