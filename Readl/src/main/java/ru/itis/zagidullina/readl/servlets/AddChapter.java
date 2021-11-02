package ru.itis.zagidullina.readl.servlets;

import ru.itis.zagidullina.readl.exceptions.EmptyFieldException;
import ru.itis.zagidullina.readl.models.Book;
import ru.itis.zagidullina.readl.models.Chapter;
import ru.itis.zagidullina.readl.services.BookService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet("/addChapter")
@MultipartConfig
public class AddChapter extends HttpServlet {

    private ServletContext servletContext;
    private BookService bookService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        servletContext = config.getServletContext();
        bookService = (BookService) servletContext.getAttribute("bookService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("bookId", request.getParameter("id"));
        request.getRequestDispatcher("/WEB-INF/jsp/addChapter.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part part = request.getPart("file");
        Integer bookId = Integer.valueOf(request.getParameter("bookId"));

        try{
            Chapter chapter = Chapter.builder()
                    .name(request.getParameter("name"))
                    .size(part.getSize())
                    .mimeType(part.getContentType())
                    .contentPath(part.getSubmittedFileName())
                    .book(Book.builder().id(bookId).build())
                    .build();

            bookService.saveChapter(chapter, part.getInputStream());
            response.sendRedirect(servletContext.getContextPath() + "/book?=" + bookId);
        }catch (EmptyFieldException e){
            request.setAttribute("error", e.getMessage());
            request.setAttribute("bookId", bookId);
            request.getRequestDispatcher("/WEB-INF/jsp/addChapter.jsp").forward(request, response);
        }catch (NullPointerException e){
            request.setAttribute("error", e.getMessage());
            request.setAttribute("bookId", bookId);
            request.getRequestDispatcher("/WEB-INF/jsp/addChapter.jsp").forward(request, response);
        }
    }
}
