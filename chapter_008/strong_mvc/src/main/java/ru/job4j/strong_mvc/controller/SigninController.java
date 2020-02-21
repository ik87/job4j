package ru.job4j.strong_mvc.controller;

import ru.job4j.strong_mvc.logic.Credential;
import ru.job4j.strong_mvc.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SigninController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        HttpSession session = req.getSession();
        synchronized (session) {
            Credential credential = new Credential();
            if (credential.isCredential(login, password)) {
                User user = credential.getUser();
                session.setAttribute("login", user.getLogin());
                resp.sendRedirect("/list");
            } else {
                req.setAttribute("error", "Credential invalid");
                doGet(req, resp);
            }
        }
    }
}
