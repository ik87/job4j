package ru.job4j.webservice.controllers;

import ru.job4j.webservice.mapers.UserMapper;
import ru.job4j.webservice.mapers.UserMapperImpl;
import ru.job4j.webservice.models.Role;
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
import java.util.function.BiConsumer;

public class LoginServlet extends HttpServlet {

    private final Validate validate = ValidateService.getInstance();
    private final Map<String, BiConsumer<HttpServletRequest, HttpServletResponse>>
            actions = new ConcurrentHashMap<>();

    @Override
    public void init() throws ServletException {
        actions.put("signin", this::signin);
        actions.put("add", this::add);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view/signin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        actions.get(action).accept(req, resp);
    }

    private void signin(HttpServletRequest req, HttpServletResponse resp) {
        User user = Utils.propertiesToUser(req);
        HttpSession session = req.getSession();
        try {

            User authUser = user != null ? validate.findByLoginAndPassword(user) : null;
            if (authUser != null) {
                session.setAttribute("user", authUser);
                resp.sendRedirect(req.getContextPath());
            } else {
                req.setAttribute("error", "Credential invalid");
                doGet(req, resp);
            }
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) {
        User user = Utils.propertiesToUser(req);
        try {

            if (validate.findByLogin(user) == null) {
                User authUser = Utils.getObjectFromSession(req, "user");
                if(!"admin".equals(authUser.getRole().getRole())) {
                    Role role = new Role();
                    role.setId(2);
                    user.setRole(role);
                }
                validate.add(user);
                resp.sendRedirect(req.getContextPath());
            } else {
                req.setAttribute("error", "Credential invalid");
            }
        } catch (IOException  e) {
            e.printStackTrace();
        }

    }


}
