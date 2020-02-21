package ru.job4j.webservice.controllers;

import ru.job4j.webservice.models.User;
import ru.job4j.webservice.service.Validate;
import ru.job4j.webservice.service.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class LoginServlet extends HttpServlet {

    //private final Map<String, Consumer<User>> actions = new ConcurrentHashMap<>();
    private final Validate validate = ValidateService.getInstance();

    //  @Override
    //  public void init() throws ServletException {
    //      actions.put("login", x -> {
    //          User user = validate.findByLogin(x);
    //          if(Objects.equals(user.getPassword(), x.getPassword()) {
    //
    //           }
    //       });
    //   }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        if (req.getParameter("action").equals("login")) {
            synchronized (session) {
                User user = new User();
                user.setLogin("login,");
                User user = validate.findByLogin(x);
                if (Objects.equals(user.getPassword(), x.getPassword()) {

                }
            }
        }

    }
}
