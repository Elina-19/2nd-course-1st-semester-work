package ru.itis.zagidullina.readl.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AccessFilter implements Filter {

    private ServletContext servletContext;
    private final String[] availablePaths = {"/signIn", "/signUp", "/logout"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        servletContext = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String currentPath = request.getRequestURI().substring(servletContext.getContextPath().length());
        for (String path: availablePaths) {
            if (path.equals(currentPath)){
                request.setAttribute("authenticated", false);
                filterChain.doFilter(request, response);
                return;
            }
        }

        HttpSession session = request.getSession(false);

        boolean isAuthenticated;
        try{
            isAuthenticated = (boolean)session.getAttribute("isAuthenticated");
        }catch(NullPointerException e){
            isAuthenticated = false;
        }

        if (isAuthenticated){
            request.setAttribute("authenticated", true);
            filterChain.doFilter(request, response);
        }else{
            response.sendRedirect(servletContext.getContextPath() + "/signIn");
        }
    }

    @Override
    public void destroy() {

    }
}
