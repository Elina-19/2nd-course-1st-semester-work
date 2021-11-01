package ru.itis.zagidullina.readl.servlets;

import org.springframework.context.ApplicationContext;
import ru.itis.zagidullina.readl.dto.SignUpForm;
import ru.itis.zagidullina.readl.exceptions.InvalidEmailException;
import ru.itis.zagidullina.readl.exceptions.NotAvailablePasswordException;
import ru.itis.zagidullina.readl.services.AuthService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {

    private ServletContext servletContext;
    private AuthService authService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        servletContext = config.getServletContext();
        authService = (AuthService) servletContext.getAttribute("authService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/signUp.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SignUpForm signUpForm = SignUpForm.builder()
                .nickname(request.getParameter("nickname"))
                .email(request.getParameter("email"))
                .password(request.getParameter("password"))
                .build();

        try{
            authService.signUp(signUpForm);
            response.sendRedirect(servletContext.getContextPath() + "/signIn");
        }catch(NullPointerException e){
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/signUp.jsp").forward(request, response);
        }catch (InvalidEmailException e){
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/signUp.jsp").forward(request, response);
        }catch (NotAvailablePasswordException e){
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/signUp.jsp").forward(request, response);
        }
    }
}
