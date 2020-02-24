package ru.job4j.webservice.filters;

import ru.job4j.webservice.models.User;
import ru.job4j.webservice.service.Validate;
import ru.job4j.webservice.service.ValidateService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {
    private final Validate validate = ValidateService.getInstance();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        if (request.getRequestURI().contains("/signin")) {
            chain.doFilter(req, resp);
        } else if (request.getRequestURI().contains("/signup")) {
            chain.doFilter(req, resp);
        } else {
            HttpSession session = request.getSession();
            synchronized (session) {
                User user = (User) session.getAttribute("user");
                User authUser = user != null ? validate.findByLoginAndPassword(user) : null;
                if (authUser == null) {
                    session.invalidate();
                    ((HttpServletResponse) resp).sendRedirect("signin");
                    return;
                } else {
                    session.setAttribute("user", authUser);
                    chain.doFilter(req, resp);
                }
            }
        }

    }

    @Override
    public void destroy() {

    }
}
