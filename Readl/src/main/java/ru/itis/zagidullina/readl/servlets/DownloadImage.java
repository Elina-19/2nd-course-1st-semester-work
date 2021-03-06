package ru.itis.zagidullina.readl.servlets;

import ru.itis.zagidullina.readl.models.Book;
import ru.itis.zagidullina.readl.services.BookService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/downloadImage")
public class DownloadImage extends HttpServlet {

    private BookService bookService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        bookService = (BookService) config.getServletContext().getAttribute("bookService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Book book = bookService.findById(Integer.valueOf(request.getParameter("id")));

        bookService.downloadImage(book, response.getOutputStream());
    }
}
