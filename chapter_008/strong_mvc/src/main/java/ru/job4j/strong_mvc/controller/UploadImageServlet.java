package ru.job4j.strong_mvc.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class UploadImageServlet extends HttpServlet {

    static final String UPLOAD_DIRECTORY = "bin" + File.separator + "images";
    static final AtomicLong PREFIX = new AtomicLong(1);


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/plain");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        String fileName = "";
        // Create a factory for disk-based filename items
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // Configure a repository (to ensure a secure temp location is used)
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);

        // Create a new filename upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);


        String path = getServletContext().getRealPath("") + UPLOAD_DIRECTORY;

        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        try {
            List<FileItem> items = upload.parseRequest(req);
            FileItem item = items.get(0);
            if (!item.isFormField()) {
                fileName = PREFIX.getAndIncrement() + "_" + item.getName();
                File file = new File(folder + File.separator + fileName);
                try (FileOutputStream out = new FileOutputStream(file)) {
                    out.write(item.getInputStream().readAllBytes());
                    out.flush();
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }

        writer.append(fileName);
        writer.flush();
    }
}
