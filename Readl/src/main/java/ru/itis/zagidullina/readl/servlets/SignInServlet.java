package ru.itis.zagidullina.readl.servlets;

import ru.itis.zagidullina.readl.dto.SignInForm;
import ru.itis.zagidullina.readl.exceptions.InvalidEmailException;
import ru.itis.zagidullina.readl.exceptions.InvalidPasswordException;
import ru.itis.zagidullina.readl.models.Account;
import ru.itis.zagidullina.readl.services.AuthService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/signIn")
public class SignInServlet extends HttpServlet {

    private ServletContext servletContext;
    private AuthService authService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        servletContext = config.getServletContext();
        authService = (AuthService) servletContext.getAttribute("authService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/signIn.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SignInForm signInForm = SignInForm.builder()
                .email(request.getParameter("email"))
                .password(request.getParameter("password"))
                .build();

        try{
            Account account = authService.signIn(signInForm);
            HttpSession httpSession = request.getSession(true);
            //httpSession.setAttribute("isAuthenticated", true);

            account.setUuid(httpSession.getId());
            authService.updateUuid(account.getEmail(), httpSession.getId());

            httpSession.setAttribute("account", account);
            response.sendRedirect(servletContext.getContextPath() + "/profile");
        }catch (NullPointerException e){
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/signIn.jsp").forward(request, response);
        }catch(InvalidEmailException e){
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/signIn.jsp").forward(request, response);
        }catch(InvalidPasswordException e){
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/signIn.jsp").forward(request, response);
        }
    }
}
