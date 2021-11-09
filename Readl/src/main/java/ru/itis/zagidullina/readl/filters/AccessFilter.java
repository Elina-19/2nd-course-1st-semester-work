package ru.itis.zagidullina.readl.filters;

import ru.itis.zagidullina.readl.models.Account;
import ru.itis.zagidullina.readl.services.AuthService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AccessFilter implements Filter {

    private ServletContext servletContext;
    //private final String[] availablePaths = {"/signIn", "/chapter", "/signUp", "/logout", "/main", "/book", "/reviews", "/comments"};
    private final String[] closedPaths = {"/profile", "/addChapter", "/addBook", "/myBooks", "/favourite"};
    private AuthService authService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        servletContext = filterConfig.getServletContext();
        authService = (AuthService) servletContext.getAttribute("authService");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String currentPath = request.getRequestURI().substring(servletContext.getContextPath().length());
        HttpSession session = request.getSession();

        Account account = (Account) session.getAttribute("account");
        Boolean isAuthenticated;

        if (account != null){
            isAuthenticated = true;
        }else {
            isAuthenticated = false;
        }

        if (!isAuthenticated){
            if (authService.authenticateByToken(request)){
                request.setAttribute("authenticated", true);
                filterChain.doFilter(request, response);
                return;
            }

            if (authService.authenticateByUUID(request)){
                request.setAttribute("authenticated", true);
                filterChain.doFilter(request, response);
                return;
            }
        }

        request.setAttribute("authenticated", isAuthenticated);

        for (String path: closedPaths) {
            if (path.equals(currentPath)){
                if (isAuthenticated){
                    filterChain.doFilter(request, response);
                    return;
                }else{
                    response.sendRedirect(servletContext.getContextPath() + "/main");
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
