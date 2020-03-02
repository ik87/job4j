package ru.job4j.webservice.filters;

import ru.job4j.webservice.models.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RoleFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession();

        synchronized (session) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                if (request.getRequestURI().contains("/signout")) {
                    chain.doFilter(req, resp);
                }  else if (request.getRequestURI().contains("/signup")) {
                    chain.doFilter(req, resp);
                } else if (request.getRequestURI().contains("/upload")) {
                    chain.doFilter(req, resp);
                } else if ("admin".equals(user.getRole().getRole())) {
                    if (request.getRequestURI().contains("/admin")) {
                        chain.doFilter(req, resp);
                    }  else {
                        ((HttpServletResponse) resp).sendRedirect("admin");
                        return;
                    }
                } else if ("user".equals(user.getRole().getRole())) {
                    if (request.getRequestURI().contains("/user")) {
                        chain.doFilter(req, resp);
                    } else {
                        ((HttpServletResponse) resp).sendRedirect("user");
                        return;
                    }

                }
            } else {
                chain.doFilter(req, resp);
            }
        }

    }

    @Override
    public void destroy() {

    }
}
