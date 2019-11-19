package ru.job4j.web_app_arhitecture.presentation;

import ru.job4j.web_app_arhitecture.logic.Validate;
import ru.job4j.web_app_arhitecture.logic.ValidateService;
import ru.job4j.web_app_arhitecture.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UserUpdateServlet extends HttpServlet {
    //update user field
    private final Validate logic = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        String id = req.getParameter("id");
        User user = logic.findById(new User(id, null, null, null, null));

        writer.append("<!DOCTYPE html>\n" +
                "<html lang='en'>\n" +
                "<head>\n" +
                "    <meta charset='UTF-8'>\n" +
                "    <title>Form Update</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<form method='post' action='update'>\n" +
                "    <b>Name:</b><br>\n" +
                "    <input type='text' size='40' name='name' value='" + user.getName() + "'/><br>\n" +
                "    <b>Login:</b><br>\n" +
                "    <input type='text' size='40' name='login' value='" + user.getLogin() + "'/><br>\n" +
                "    <b>Email:</b><br>\n" +
                "    <input type='email' size='40' name= 'email' value='" + user.getEmail() + "'/><br><br>\n" +
                "    <input type='hidden' name='id' value='" + user.getId() + "'/>\n" +
                "    <input type='submit' value='save'/>" +
                " </form>\n" +
                "</body>\n" +
                "</html>");
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");
        String id = req.getParameter("id");
        String login = req.getParameter("login");
        String email = req.getParameter("email");

        User user = logic.findById(new User(id, name , null, null , null));
        user.setName(name);
        user.setEmail(email);
        user.setLogin(login);

        logic.update(user);
    }
}
