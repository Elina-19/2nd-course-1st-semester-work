package ru.itis.zagidullina.readl.servlets;

import ru.itis.zagidullina.readl.models.Account;
import ru.itis.zagidullina.readl.models.Favourite;
import ru.itis.zagidullina.readl.services.AccountsService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/favourite")
public class FavouriteServlet extends HttpServlet {

    private ServletContext servletContext;
    private AccountsService accountsService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        servletContext = config.getServletContext();
        accountsService = (AccountsService) servletContext.getAttribute("usersService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        Favourite favourite = accountsService.getFavourite(account);

        if (favourite == null){
            request.setAttribute("message", "Нет книг в избранном");
        }

        request.setAttribute("books", favourite.getBooks());
        request.getRequestDispatcher("/WEB-INF/jsp/favourite.jsp").forward(request, response);
    }
}
