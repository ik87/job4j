package ru.job4j.webservice.controllers.user;

import jdk.jshell.execution.Util;
import ru.job4j.webservice.models.User;
import ru.job4j.webservice.service.Utils;
import ru.job4j.webservice.service.Validate;
import ru.job4j.webservice.service.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class MainServlet extends HttpServlet {
    private Validate validate = ValidateService.getInstance();
    private Map<String, Consumer<User>> map = new ConcurrentHashMap<>();

    @Override
    public void init() throws ServletException {
        map.put("update", validate::update);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        synchronized (session) {
            User user = (User)session.getAttribute("login");
            req.setAttribute("user",user);
        }
        req.getRequestDispatcher("/WEB-INF/view/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        User user = Utils.propertiesToUser(req);
        map.get(action).accept(user);
        resp.sendRedirect(req.getContextPath());
    }
}
