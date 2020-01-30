package ru.job4j.strong_mvc.controller;

import ru.job4j.strong_mvc.logic.FileHandler;
import ru.job4j.strong_mvc.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This servlet is responsible for update image
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version 1.0
 * @since 30.01.2020
 */
public class UpdateImageServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FileHandler fileHandler = (FileHandler) getServletContext().getAttribute("fileHandler");
        String photoId = fileHandler.upload(req);
        HttpSession session = req.getSession();
        synchronized (session) {
            User user = (User) session.getAttribute("user");
            user.setPhotoId(photoId);
            session.setAttribute("user", user);
        }
        req.getRequestDispatcher("/WEB-INF/views/update.jsp").forward(req, resp);
    }
}
