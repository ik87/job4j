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
    private final UserMapper userMapper = new UserMapperImpl();
    private final Validate validate = ValidateService.getInstance();
    private final Map<String, BiConsumer<HttpServletRequest, HttpServletResponse>>
            actions = new ConcurrentHashMap<>();

    @Override
    public void init() throws ServletException {
        actions.put("update", this::update);
        actions.put("upload", this::upload);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = validate.findAll();
        List<UserDto> usersDto = userMapper.toDto(users);
        User authAdmin = Utils.getObjectFromSession(req, "user");
        UserDto adminDto = userMapper.toDto(authAdmin);
        req.setAttribute("adminDto", adminDto);
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

    private void upload(HttpServletRequest req, HttpServletResponse resp) {
        String id = (String) req.getAttribute("id");
        User user = new User();
        user.setId(Integer.valueOf(id));
        user = validate.findById(user);

        byte[] bytes = (byte[]) req.getAttribute("bytes");
        user.setPhoto(bytes);

        validate.update(user);
        try {
            resp.sendRedirect(req.getContextPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
