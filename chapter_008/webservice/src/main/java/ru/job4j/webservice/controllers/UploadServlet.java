package ru.job4j.webservice.controllers;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import ru.job4j.webservice.models.User;
import ru.job4j.webservice.service.Utils;
import ru.job4j.webservice.service.Validate;
import ru.job4j.webservice.service.ValidateService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UploadServlet extends HttpServlet {
    private final Validate validate = ValidateService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();
        byte[] bytes = new byte[0];
        String id = "";
        String fieldName = "";

        // Configure a repository (to ensure a secure temp location is used)
        ServletContext servletContext = req.getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // Parse the request
        try {
            List<FileItem> items = upload.parseRequest(req);


            // Process the uploaded items
            //Iterator<FileItem> iter = items.iterator();
            id = items.get(0).getString();
            fieldName = items.get(1).getString();
            bytes = items.get(2).get();

        } catch (FileUploadException e) {
            e.printStackTrace();
        }

        req.setAttribute("bytes", bytes);
        req.setAttribute("id", id);
        req.setAttribute("fieldName",fieldName);

        User user = (User) Utils.getObjectFromSession(req, "user");
        String role = user.getRole().getRole();
        req.getRequestDispatcher(role + "?action=upload").forward(req, resp);
    }

}
