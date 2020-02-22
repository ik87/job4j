package ru.job4j.webservice.controllers;

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

public class LoginServlet extends HttpServlet {

    private final Validate validate = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view/signin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //get login data
        User user = Utils.propertiesToUser(req);
        HttpSession session = req.getSession();
        if (req.getParameter("action").equals("signin")) {
            synchronized (session) {
                User foundUser = validate.findByLogin(user);
                if (foundUser != null && foundUser.getPassword().equals(user.getPassword())) {
                    session.setAttribute("login", foundUser);
                    resp.sendRedirect(req.getContextPath());
                } else {
                    req.setAttribute("error", "Credential invalid");
                    doGet(req, resp);
                }
            }

        }
    }
}
