package ru.itis.zagidullina.readl.servlets;

import ru.itis.zagidullina.readl.models.Account;
import ru.itis.zagidullina.readl.models.Book;
import ru.itis.zagidullina.readl.services.AccountsService;
import ru.itis.zagidullina.readl.services.BookService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/book")
public class BookServlet extends HttpServlet {

    private AccountsService accountsService;
    private BookService bookService;
    private static final String FAVOURITE_TRUE = "Delete";
    private static final String FAVOURITE_FALSE = "Add";

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        bookService = (BookService) servletContext.getAttribute("bookService");
        accountsService = (AccountsService) servletContext.getAttribute("usersService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Book book = bookService.findById(Integer.valueOf(request.getParameter("id")));

            if (book.getChapters().size() == 0){
                request.setAttribute("chapters", "Нет глав");
            }
            if(book.getGenres().size() == 0){
                book.setGenres(null);
            }

            HttpSession session = request.getSession(false);
            if(session != null){
                Account account = (Account) session.getAttribute("account");

                if (account != null) {
                    boolean st = accountsService.getStatus(account, book.getId());

                    if (st){
                        request.setAttribute("status", FAVOURITE_TRUE);
                    }else request.setAttribute("status", FAVOURITE_FALSE);

                    request.setAttribute("accountId", account.getId());
                }
            }

            request.setAttribute("book", book);
        }catch (IllegalArgumentException e){
            request.setAttribute("message", e.getMessage());
        }

        request.getRequestDispatcher("/WEB-INF/jsp/book.jsp").forward(request, response);
    }
}
