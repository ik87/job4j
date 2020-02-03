package ru.job4j.strong_mvc.controller;

import ru.job4j.strong_mvc.logic.FileHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * This servlet is responsible for upload image
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version 1.0
 * @since 30.01.2020
 */
public class UploadImageServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FileHandler fileHandler = (FileHandler) getServletContext().getAttribute("fileHandler");
        String photoId = fileHandler.upload(req);
        req.setAttribute("photoId", photoId);
        req.getRequestDispatcher("/WEB-INF/views/create.jsp").forward(req, resp);
    }
}
