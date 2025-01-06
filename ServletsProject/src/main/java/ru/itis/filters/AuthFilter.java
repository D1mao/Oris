package ru.itis.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/userMain", "/projectPageForUser", "/taskPage"})
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;


        Object userId = request.getSession().getAttribute("userId");
        if (userId == null) {
            response.sendRedirect(request.getContextPath()+"/register?error=notAuthorized");
            return;
        }

        filterChain.doFilter(request, response);

    }
}
