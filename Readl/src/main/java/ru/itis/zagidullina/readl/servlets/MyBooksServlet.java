package ru.itis.zagidullina.readl.servlets;

import ru.itis.zagidullina.readl.filters.AccessFilter;
import ru.itis.zagidullina.readl.models.Account;
import ru.itis.zagidullina.readl.models.Book;
import ru.itis.zagidullina.readl.repositories.BookRepository;
import ru.itis.zagidullina.readl.services.BookService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/myBooks")
public class MyBooksServlet extends HttpServlet {

    private BookService bookService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        bookService = (BookService) config.getServletContext().getAttribute("bookService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Account account = (Account)request.getSession().getAttribute("account");
        List<Book> books = bookService.findBooksOfAccount(account.getId());
        request.setAttribute("books", books);
        request.getRequestDispatcher("/WEB-INF/jsp/myBooks.jsp").forward(request, response);
    }
}
