package ru.itis.zagidullina.readl.servlets;

import ru.itis.zagidullina.readl.dto.AddBookForm;
import ru.itis.zagidullina.readl.dto.SignInForm;
import ru.itis.zagidullina.readl.exceptions.EmptyFieldException;
import ru.itis.zagidullina.readl.exceptions.InvalidEmailException;
import ru.itis.zagidullina.readl.exceptions.InvalidPasswordException;
import ru.itis.zagidullina.readl.models.Account;
import ru.itis.zagidullina.readl.models.Book;
import ru.itis.zagidullina.readl.services.BookService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/addBook")
@MultipartConfig
public class AddBookServlet extends HttpServlet {

    private ServletContext servletContext;
    private BookService bookService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        servletContext = config.getServletContext();
        bookService = (BookService) servletContext.getAttribute("bookService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/addBook.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part part = request.getPart("file");

        AddBookForm addBookForm = AddBookForm.builder()
                .name(request.getParameter("name"))
                .description(request.getParameter("description"))
                .imageName(part.getSubmittedFileName())
                .size(part.getSize())
                .build();

        Account account = (Account) request.getSession().getAttribute("account");

        try{
            bookService.save(addBookForm, account, part.getInputStream());
            response.sendRedirect(servletContext.getContextPath() + "/profile");
        }catch (EmptyFieldException e){
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/addBook.jsp").forward(request, response);
        }
    }
}
