package ru.job4j.strong_mvc.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class DeleteImageServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Boolean result = false;
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        String fileName = req.getParameter("name");
        String path = getServletContext().getRealPath("") + UploadImageServlet.UPLOAD_DIRECTORY;
        File file = new File(path + File.separator + fileName);
        result = file.delete();
        resp.setContentType("text/plain");
        writer.append(result.toString());
        writer.flush();
    }
}
