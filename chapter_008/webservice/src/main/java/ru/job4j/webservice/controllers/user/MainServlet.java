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

        actions.put("update", this::update);
        actions.put("upload", this::upload);


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = Utils.getObjectFromSession(req, "user");

        String photo = user.getPhoto() != null ?
                new String(user.getPhoto(), "UTF-8") : null;

        String created = Utils.millisecondToStringDate(user.getCreated());

        req.setAttribute("photo", photo);
        req.setAttribute("created", created);

        req.getRequestDispatcher("/WEB-INF/view/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");
        actions.get(action).accept(req, resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) {
        User changed = Utils.propertiesToUser(req);
        User authUser = Utils.getObjectFromSession(req, "user");
        User user = validate.findById(authUser);

        validate.update(user, changed);
        try {
            resp.sendRedirect(req.getContextPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void upload(HttpServletRequest req, HttpServletResponse resp) {
        User changed = new User();
        User authUser = Utils.getObjectFromSession(req, "user");
        User user = validate.findById(authUser);

        byte[] bytes = (byte[]) req.getAttribute("bytes");
        changed.setPhoto(bytes);

        validate.update(user, changed);
        try {
            resp.sendRedirect(req.getContextPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}