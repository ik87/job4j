package ru.job4j.web_app_arhitecture.presentation;

import ru.job4j.web_app_arhitecture.logic.Validate;
import ru.job4j.web_app_arhitecture.logic.ValidateService;
import ru.job4j.web_app_arhitecture.model.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        User id = RequestToUser.getUserParameters(req);
        User user = logic.findById(id);

        writer.append("<!DOCTYPE html>\n" +
                "<html lang='en'>\n" +
                "<head>\n" +
                "    <meta charset='UTF-8'>\n" +
                "    <title>Form Update</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<form method='post' action='list'>\n" +
                "    <b>Name:</b><br>\n" +
                "    <input type='text' size='40' name='name' value='" + user.getName() + "'/><br>\n" +
                "    <b>Login:</b><br>\n" +
                "    <input type='text' size='40' name='login' value='" + user.getLogin() + "'/><br>\n" +
                "    <b>Email:</b><br>\n" +
                "    <input type='email' size='40' name= 'email' value='" + user.getEmail() + "'/><br><br>\n" +
                "    <input type='submit' value='save'/>" +
                "    <input type='hidden' name='action' value='update'/><br>\n" +
                "    <input type='hidden' name='id' value='" + user.getId() + "'/><br>\n" +
                "    <input type='hidden' name='created' value='" + user.getCreateDate() + "'/><br>\n" +
                " </form>\n" +
                "</body>\n" +
                "</html>");
        writer.flush();
    }
}
