package ru.job4j.webservice.controllers;

import ru.job4j.webservice.models.Role;
import ru.job4j.webservice.models.User;
import ru.job4j.webservice.service.Utils;
import ru.job4j.webservice.service.Validate;
import ru.job4j.webservice.service.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignupServlet extends HttpServlet {
    private final Validate validate = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view/add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = Utils.propertiesToUser(req);
        try {
            if (validate.findByLogin(user) == null) {
                User authUser = Utils.getObjectFromSession(req, "user");
                if (authUser == null || !"admin".equals(authUser.getRole().getRole())) {
                    Role role = new Role();
                    role.setId(2);
                    user.setRole(role);
                }
                validate.add(user);
                resp.sendRedirect(req.getContextPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

