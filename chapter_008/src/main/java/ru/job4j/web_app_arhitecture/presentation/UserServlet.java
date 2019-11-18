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
        writer.append("" +
                "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "</head>\n " +
                "<body>" +
                "<table>\n");

        //toDo
        writer.append("</body>\n" +
                "</html>");
        writer.append(result.toString());
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String action = req.getParameter("action");

        String name = req.getParameter("name");
        String id = req.getParameter("id");
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String createDate = req.getParameter("created");

        User user = new User(id, name, login, email, createDate);

        sent.get(action).accept(user);

        this.doGet(req, resp);
    }


}
