package ru.job4j.strong_mvc.controller;

import ru.job4j.strong_mvc.logic.FileHandler;
import ru.job4j.strong_mvc.logic.Validate;
import ru.job4j.strong_mvc.logic.ValidateService;
import ru.job4j.strong_mvc.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * This servlet is responsible for CRUD operation with users through "POST" request
 * and print them through "GET" response
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $id
 * @since 0.1
 */
public class UserServlet extends HttpServlet {

    private final Validate validate = ValidateService.getInstance();

    private final Map<String, Consumer<User>> send = new ConcurrentHashMap<>();
    //key: Role,
    private final Map<String, Consumer<String>> roleRedirection = new ConcurrentHashMap<>();


    @Override
    public void init() {

        send.put("add", validate::add);
        send.put("update", validate::update);
        send.put("delete", (user) -> {
            FileHandler fileHandler = (FileHandler) getServletContext().
                    getAttribute("fileHandler");
            fileHandler.delete(validate.findById(user).getPhotoid());
            validate.delete(user);
        });
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<User> users = validate.findAll();
        req.setAttribute("users", users);
        req.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        User user = RequestToUser.getUserParameters(req);
        send.get(action).accept(user);
        resp.sendRedirect("list");
    }


}
