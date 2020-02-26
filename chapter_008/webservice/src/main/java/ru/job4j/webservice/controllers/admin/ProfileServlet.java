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

public class ProfileServlet extends HttpServlet {
    private final UserMapper userMapper = new UserMapperImpl();
    private final Validate validate = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        User user = new User();
        user.setId(Integer.valueOf(id));
        user = validate.findById(user);
        UserDto userDto = userMapper.toDto(user);
        req.setAttribute("userDto", userDto);
        req.getRequestDispatcher("/WEB-INF/view/profile.jsp").forward(req, resp);
    }
}
