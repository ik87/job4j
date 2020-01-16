package ru.job4j.web_app.presentation;

import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.when;

public class UserServletTest {
    @Test
    public void whenRequestToPostThenResponseFromGet() throws Exception {
        HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);

        when(httpServletRequest.getParameter("action")).thenReturn("add");
        when(httpServletRequest.getParameter("id")).thenReturn("1");
        when(httpServletRequest.getParameter("name")).thenReturn("dex");
        when(httpServletRequest.getParameter("login")).thenReturn("dexx");
        when(httpServletRequest.getParameter("created")).thenReturn("10-11-2019");
        when(httpServletRequest.getParameter("email")).thenReturn("dex@mail.ru");


        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(httpServletResponse.getWriter()).thenReturn(writer);
        when(httpServletResponse.getWriter()).thenReturn(writer);
        UserServlet userServlet = new UserServlet();
        userServlet.init();
        userServlet.doPost(httpServletRequest, httpServletResponse);
        writer.flush();
        String result = "<!DOCTYPE html>\n" +
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
                "    </tr><tr align='center'><td>1</td><td>dex</td><td>dexx</td><td>dex@mail.ru</td><td>10-11-2019</td>\n" +
                "        <td>\n" +
                "            <form action='edit' method='get'>\n" +
                "                <input type='hidden' name='id' value='1'/>\n" +
                "                <input  type='submit' value='edit'/>\n" +
                "            </form>\n" +
                "        </td>\n" +
                "        <td>\n" +
                "            <form action='' method='post'>\n" +
                "                <input type='hidden' name='action' value='delete'/>\n" +
                "                <input type='hidden' name='id' value='1'/>\n" +
                "                <input type='submit' value='del'/>\n" +
                "            </form>\n" +
                "        </td>\n" +
                "    </tr></table>\n" +
                "<form action='create' method='get'>\n" +
                "    <input type='submit' value='Add new user'/>\n" +
                "</form></body>\n" +
                "</html>";

        assertThat(stringWriter.toString(), is(result));


    }
}