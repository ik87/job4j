package ru.job4j.web_app_arhitecture.presentation;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * This servlet is responsible for create User
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $id
 * @since 0.1
 */
public class UserCreateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Writer writer = resp.getWriter();
        writer.append("<!DOCTYPE html>\n" +
                "<html lang='en'>\n" +
                "<head>\n" +
                "    <meta charset='UTF-8'>\n" +
                "    <title>Add user</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<form method='post' action='list'>\n" +
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
                "    <input type='hidden' name='action' value='add'/><br>\n" +
                "</form>\n" +
                "</body>\n" +
                "</html>");
    }

}
