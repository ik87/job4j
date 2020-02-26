package ru.job4j.webservice.controllers.user;


import ru.job4j.webservice.dto.UserDto;
import ru.job4j.webservice.mapers.UserMapper;
import ru.job4j.webservice.mapers.UserMapperImpl;
import ru.job4j.webservice.models.User;
import ru.job4j.webservice.service.Utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditServlet extends HttpServlet {
    public final UserMapper userMapper = new UserMapperImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = Utils.getObjectFromSession(req, "user");
        UserDto userDto = userMapper.toDto(user);
        req.setAttribute("userDto", userDto);
        req.getRequestDispatcher("/WEB-INF/view/edit.jsp").forward(req, resp);
    }
}