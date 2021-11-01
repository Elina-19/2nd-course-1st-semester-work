package ru.itis.zagidullina.readl.servlets;

import ru.itis.zagidullina.readl.models.Account;
import ru.itis.zagidullina.readl.models.Book;
import ru.itis.zagidullina.readl.services.BookService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/book")
public class BookServlet extends HttpServlet {

    private BookService bookService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        bookService = (BookService) config.getServletContext().getAttribute("bookService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Book book = bookService.findById(Integer.valueOf(request.getParameter("id")));
        if (book == null){
            request.setAttribute("message", "Такой книги нет");
        }else{
            request.setAttribute("book", book);
        }

        HttpSession session = request.getSession(false);
        if(session != null){
            Account account = (Account) session.getAttribute("account");
            if (account != null) {
                request.setAttribute("accountId", account.getId());
            }
        }

        request.getRequestDispatcher("/WEB-INF/jsp/book.jsp").forward(request, response);
    }
}
