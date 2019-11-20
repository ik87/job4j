package ru.job4j.web_app_arhitecture.presentation;

import ru.job4j.web_app_arhitecture.logic.Validate;
import ru.job4j.web_app_arhitecture.logic.ValidateService;
import ru.job4j.web_app_arhitecture.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * This servlet is responsible for CRUD operation with users through "POST" request
 * and print them through "GET" response
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $id
 * @since 0.1
 */
public class UserServlet extends HttpServlet {

    private final Validate logic = ValidateService.getInstance();

    private final Map<String, Consumer<User>> sent = new ConcurrentHashMap<>();


    @Override
    public void init() throws ServletException {
        sent.put("add", logic::add);
        sent.put("update", logic::update);
        sent.put("delete", logic::delete);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        List<User> result = logic.findAll();
        StringBuilder sb = new StringBuilder();

        for (User user : result) {
            sb.append(
                    String.format("<tr align='center'><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td>\n" +
                                    "        <td>\n" +
                                    "            <form action='update' method='get'>\n" +
                                    "                <input type='hidden' name='id' value='" + user.getId() + "'/>\n" +
                                    "                <input  type='submit' value='edit'/>\n" +
                                    "            </form>\n" +
                                    "        </td>\n" +
                                    "        <td>\n" +
                                    "            <form action='' method='post'>\n" +
                                    "                <input type='hidden' name='action' value='delete'/>\n" +
                                    "                <input type='hidden' name='id' value='" + user.getId() + "'/>\n" +
                                    "                <input type='submit' value='del'/>\n" +
                                    "            </form>\n" +
                                    "        </td>\n" +
                                    "    </tr>",
                            user.getId(),
                            user.getName(),
                            user.getLogin(),
                            user.getEmail(),
                            user.getCreateDate()));
        }

        writer.append("<!DOCTYPE html>\n" +
                "<html lang='en'>\n" +
                "<head>\n" +
                "    <meta charset='UTF-8'>\n" +
                "    <title>Users table</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<table border='1' cellpadding='3'>\n" +
                "    <caption>Users table</caption>\n" +
                "    <tr align='center'>\n" +
                "        <th>Id</th>\n" +
                "        <th>Name</th>\n" +
                "        <th>Login</th>\n" +
                "        <th>Email</th>\n" +
                "        <th>Data of creation</th>\n" +
                "        <th colspan='2'>Operation</th>\n" +
                "    </tr>");
        writer.append(sb.toString());
        writer.append("</table>\n" +
                "<form action='create' method='get'>\n" +
                "    <input type='submit' value='Add new user'/>\n" +
                "</form>" +
                "</body>\n" +
                "</html>");
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        User user = RequestToUser.getUserParameters(req);
        sent.get(action).accept(user);
        this.doGet(req, resp);
    }


}
