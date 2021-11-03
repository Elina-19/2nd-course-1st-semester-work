package ru.itis.zagidullina.readl.filters;

import ru.itis.zagidullina.readl.services.BookService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AccessFilter implements Filter {

    private ServletContext servletContext;
    private final String[] availablePaths = {"/signIn", "/chapter", "/signUp", "/logout", "/main", "/book", "/reviews", "/comments"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        servletContext = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String currentPath = request.getRequestURI().substring(servletContext.getContextPath().length());

        HttpSession session = request.getSession(false);

        Boolean isAuthenticated;
        try{
            isAuthenticated = (boolean)session.getAttribute("isAuthenticated");
        }catch(NullPointerException e){
            isAuthenticated = false;
        }
        request.setAttribute("authenticated", isAuthenticated);

        for (String path: availablePaths) {
            if (path.equals(currentPath)){
                filterChain.doFilter(request, response);
                return;
            }
        }

        if (isAuthenticated){
            filterChain.doFilter(request, response);
        }else{
            response.sendRedirect(servletContext.getContextPath() + "/main");
        }
    }

    @Override
    public void destroy() {

    }
}
