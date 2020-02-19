package ru.job4j.webservice.controllers.admin;

import ru.job4j.webservice.models.User;
import ru.job4j.webservice.service.Validate;
import ru.job4j.webservice.service.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MainServlet  extends HttpServlet {
    private final Validate validate = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = validate.findAll();
        req.setAttribute("users", users);
        req.getRequestDispatcher("/WEB-INF/views/admin/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
