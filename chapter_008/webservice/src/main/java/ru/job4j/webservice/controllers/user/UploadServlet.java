package ru.job4j.webservice.controllers.user;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import ru.job4j.webservice.models.User;
import ru.job4j.webservice.service.Utils;
import ru.job4j.webservice.service.Validate;
import ru.job4j.webservice.service.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UploadServlet extends HttpServlet {
    private final Validate validate = ValidateService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<FileItem> fileItems = null;
        try {
            fileItems = Utils.upload(req);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        User authUser = Utils.getObjectFromSession(req, "user");
        if (fileItems != null) {
            byte[] bytes = fileItems.get(1).get();
            if (bytes.length != 0) {
                authUser.setPhoto(bytes);
                validate.update(authUser);
            }
        }
        resp.sendRedirect(req.getContextPath() + "/");
    }

}

