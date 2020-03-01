package ru.job4j.webservice.service;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import ru.job4j.webservice.models.Role;
import ru.job4j.webservice.models.User;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Utils {

    private final static String DATA_FORMAT = "dd-MM-yyyy HH:mm";

    public static List<FileItem> upload(HttpServletRequest req) throws FileUploadException {
        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // Configure a repository (to ensure a secure temp location is used)
        ServletContext servletContext = req.getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        // Parse the request
        return upload.parseRequest(req);
    }

    public static <T> T getObjectFromSession(HttpServletRequest req, String name) {
        HttpSession session = req.getSession();
        T user;
        synchronized (session) {
            user = (T) session.getAttribute(name);
        }
        return user;
    }

    public static User propertiesToUser(HttpServletRequest req) {
        User user = new User();
        Role role = new Role();
        user.setId(stringToInt(req.getParameter("id")));
        user.setLogin(req.getParameter("login"));
        user.setEmail(req.getParameter("email"));
        user.setPassword(req.getParameter("password"));
        role.setRole(req.getParameter("role"));
        role.setId(stringToInt(req.getParameter("role_id")));
        user.setRole(role);
        user.setCreated(dateStringToMillisecond(req.getParameter("created")));

        return user;
    }

    private static Integer stringToInt(String str) {
        return str != null ? Integer.valueOf(str) : null;
    }

    /**
     * Convert date string to millisecond
     *
     * @param date string data. Format as DATA_FORMAT
     * @return millisecond
     */
    private static Long dateStringToMillisecond(String date) {
        SimpleDateFormat f = new SimpleDateFormat(DATA_FORMAT);
        Long milliseconds = null;
        if (date != null) {
            try {
                Date d = f.parse(date);
                milliseconds = d.getTime();
            } catch (ParseException e) {

            }
        }
        return milliseconds;
    }
}
