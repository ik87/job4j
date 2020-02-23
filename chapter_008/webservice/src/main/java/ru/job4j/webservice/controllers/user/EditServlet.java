package ru.job4j.webservice.controllers.user;

import ru.job4j.webservice.models.User;
import ru.job4j.webservice.service.Validate;
import ru.job4j.webservice.service.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EditServlet extends HttpServlet {
    Validate validate = ValidateService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        synchronized (session) {
            User current = (User) session.getAttribute("login");
            User user = validate.findById(current);
            req.setAttribute("user",user);
        }
        req.getRequestDispatcher("/WEB-INF/view/edit.jsp").forward(req, resp);
    }
}