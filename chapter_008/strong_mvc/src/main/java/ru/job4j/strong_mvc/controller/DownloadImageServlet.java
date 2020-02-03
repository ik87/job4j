package ru.job4j.strong_mvc.controller;

import ru.job4j.strong_mvc.logic.FileHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * This servlet is responsible for download image
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version 1.0
 * @since 30.01.2020
 */
public class DownloadImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        resp.setContentType("image");
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");
        FileHandler fileHandler = (FileHandler) getServletContext().getAttribute("fileHandler");
        String path = fileHandler.getPath();
        File file = new File(path + File.separator + name);
        OutputStream out = resp.getOutputStream();
        FileInputStream in = new FileInputStream(file);

        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }
        in.close();
        out.flush();
    }
}
