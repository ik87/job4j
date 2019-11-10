package ru.job4j.web_app_arhitecture.presentation;

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

        new UserServlet().doPost(httpServletRequest, httpServletResponse);
        writer.flush();

        assertThat(stringWriter.toString(), is("[1\tdex\tdexx\tdex@mail.ru\t10-11-2019]"));



    }
}