package ru.itis.zagidullina.readl.servlets;

import org.springframework.context.ApplicationContext;
import ru.itis.zagidullina.readl.models.Book;
import ru.itis.zagidullina.readl.services.AccountsService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/allusers")
public class AllUsersServlet extends HttpServlet {

    private AccountsService profileService;
    private ServletContext servletContext;

    @Override
    public void init(ServletConfig config) throws ServletException {
        servletContext = config.getServletContext();
        ApplicationContext applicationContext = (ApplicationContext)servletContext.getAttribute("springContext");
        profileService = applicationContext.getBean(AccountsService.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("users", profileService.findAll());
        request.setAttribute("comment", "Hello world");
        Book book = new Book();
        book.setDescription("CADKJCNk");
        book.setName("The Maze Runner");
        request.setAttribute("book", book);
        request.getRequestDispatcher("/WEB-INF/jsp/users.jsp").forward(request, response);
    }
}
