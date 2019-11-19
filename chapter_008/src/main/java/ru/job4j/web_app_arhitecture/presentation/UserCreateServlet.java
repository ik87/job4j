package ru.job4j.web_app_arhitecture.presentation;

import ru.job4j.web_app_arhitecture.logic.Validate;
import ru.job4j.web_app_arhitecture.logic.ValidateService;
import ru.job4j.web_app_arhitecture.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class UserCreateServlet extends HttpServlet {
    private final Validate logic = ValidateService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Writer writer = resp.getWriter();
        writer.append("<!DOCTYPE html>\n" +
                "<html lang='en'>\n" +
                "<head>\n" +
                "    <meta charset='UTF-8'>\n" +
                "    <title>Add user</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<form method='post' action='create'>\n" +
                "    <b>id:</b><br>\n" +
                "    <input type='text' name='id' size='40'/><br>\n" +
                "    <b>Name:</b><br>\n" +
                "    <input type='text' name='name' size='40'/><br>\n" +
                "    <b>Login:</b><br>\n" +
                "    <input type='text' name='login' size='40'/><br>\n" +
                "    <b>Email:</b><br>\n" +
                "    <input type='email' name='email' size='40'/><br>\n" +
                "    <b>Date created:</b><br>\n" +
                "    <input type='date' name='created' size='40'/><br><br>\n" +
                "    <input type='submit' value='OK'/>\n" +
                "</form>\n" +
                "</body>\n" +
                "</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String created = req.getParameter("created");

        logic.add(new User(id, name , login, email , created));
    }
}
