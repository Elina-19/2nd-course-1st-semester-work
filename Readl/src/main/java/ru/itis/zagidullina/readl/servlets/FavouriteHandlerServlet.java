package ru.itis.zagidullina.readl.servlets;

import ru.itis.zagidullina.readl.models.Account;
import ru.itis.zagidullina.readl.services.AccountsService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/favouriteHandler")
public class FavouriteHandlerServlet extends HttpServlet {

    private AccountsService accountsService;
    private static final String ADD = "Add";
    private static final String DELETE = "Delete";

    @Override
    public void init(ServletConfig config) throws ServletException {
        accountsService = (AccountsService) config.getServletContext().getAttribute("usersService");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        Integer bookId = Integer.valueOf(request.getParameter("bookId"));

        Account account = (Account) request.getSession().getAttribute("account");

        if (action.equals(ADD)){
            accountsService.addToFavourite(account, bookId);
        }

        if (action.equals(DELETE)){
            accountsService.deleteFromFavourite(account, bookId);
        }
    }
}
