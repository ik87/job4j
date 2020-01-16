package ru.job4j.web_app_jsp.presentation;

import ru.job4j.web_app_jsp.logic.Validate;
import ru.job4j.web_app_jsp.logic.ValidateService;
import ru.job4j.web_app_jsp.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * This servlet is responsible for update User
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $id
 * @since 0.1
 */
public class UserUpdateServlet extends HttpServlet {
    //update user field
    private final Validate logic = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User id = RequestToUser.getUserParameters(req);
        User user = logic.findById(id);
        req.setAttribute("user", user);
        req.getRequestDispatcher("update.jsp").forward(req, resp);
    }
}
