package ru.job4j.webservice.controllers.user;

import ru.job4j.webservice.models.User;
import ru.job4j.webservice.service.Utils;
import ru.job4j.webservice.service.Validate;
import ru.job4j.webservice.service.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

public class MainServlet extends HttpServlet {
    private Validate validate = ValidateService.getInstance();
    private Map<String, BiConsumer<HttpServletRequest, HttpServletResponse>> actions = new ConcurrentHashMap<>();

    @Override
    public void init() throws ServletException {

        actions.put("update", (req, resp) -> {
            User changed = Utils.propertiesToUser(req);
            User authUser = Utils.getObjectFromSession(req, "user");
            User user = validate.findById(authUser);
            validate.update(user, changed);
            try {
                resp.sendRedirect(req.getContextPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");
        actions.get(action).accept(req, resp);
    }


}
