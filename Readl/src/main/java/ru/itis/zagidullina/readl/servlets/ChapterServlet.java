package ru.itis.zagidullina.readl.servlets;

import ru.itis.zagidullina.readl.models.Chapter;
import ru.itis.zagidullina.readl.services.BookService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/chapter")
public class ChapterServlet extends HttpServlet {

    private BookService bookService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        bookService = (BookService) config.getServletContext().getAttribute("bookService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            Chapter chapter = bookService.getChapterById(Integer.valueOf(request.getParameter("id")));

            request.setAttribute("chapter", chapter);
        }catch (IllegalArgumentException e){
            request.setAttribute("message", e.getMessage());
        }
        request.getRequestDispatcher("/WEB-INF/jsp/chapter.jsp").forward(request, response);
    }
}
