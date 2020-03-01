package ru.job4j.webservice.controllers.admin;

import ru.job4j.webservice.dto.UserDto;
import ru.job4j.webservice.mapers.UserMapper;
import ru.job4j.webservice.mapers.UserMapperImpl;
import ru.job4j.webservice.models.User;
import ru.job4j.webservice.service.Utils;
import ru.job4j.webservice.service.Validate;
import ru.job4j.webservice.service.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

public class MainServlet extends HttpServlet {
    private final UserMapper userMapper = UserMapperImpl.getInstance();
    private final Validate validate = ValidateService.getInstance();
    private final Map<String, BiConsumer<HttpServletRequest, HttpServletResponse>>
            actions = new ConcurrentHashMap<>();

    @Override
    public void init() throws ServletException {
        actions.put("update", this::update);
        actions.put("remove", this::remove);
        actions.put("deleteImg", this::deleteImg);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = validate.findAll();
        List<UserDto> usersDto = userMapper.toDto(users);
        req.setAttribute("usersDto", usersDto);
        req.getRequestDispatcher("/WEB-INF/view/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        actions.get(action).accept(req, resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) {
        User changed = Utils.propertiesToUser(req);
        User user = validate.findById(changed);

        validate.update(user, changed);

        try {
            resp.sendRedirect(req.getContextPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteImg(HttpServletRequest req, HttpServletResponse resp) {
        User user = Utils.propertiesToUser(req);
        user = validate.findById(user);
        user.setPhoto(null);
        validate.update(user);

        try {
            resp.sendRedirect(req.getContextPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void remove(HttpServletRequest req, HttpServletResponse resp) {
        User user = Utils.propertiesToUser(req);
        user = validate.findById(user);
        validate.delete(user);

        try {
            resp.sendRedirect(req.getContextPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
