package ru.job4j.strong_mvc.logic;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version 1.0
 * @since 30.01.2020
 */
public class FileHandlerService implements FileHandler {
    /**
     * For every new photo will be generate new id number
     */
    private final AtomicLong prefix = new AtomicLong(1);
    private DiskFileItemFactory factory = new DiskFileItemFactory();
    private File folder;
    private String path;

    @Override
    public void config(ServletContext servletContext) {
        // Configure a repository (to ensure a secure temp location is used)
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");

        this.factory.setRepository(repository);
        this.path = servletContext.getRealPath("") + servletContext.getInitParameter("path");
        this.folder = new File(path);

        if (!this.folder.exists()) {
            this.folder.mkdirs();
        }
    }

    @Override
    public String upload(HttpServletRequest req) {

        String newPhotoId = "";
        ServletFileUpload upload = new ServletFileUpload(factory);

        try {
            List<FileItem> items = upload.parseRequest(req);
            String oldPhotoId = items.get(0).getString();
            String oldPrefix = oldPhotoId.split("_")[0];
            if (!items.get(1).getName().isEmpty()) {
                if (fileExist(oldPrefix + "_" + items.get(1).getName())) {
                    newPhotoId = oldPhotoId;
                } else {
                    if (!oldPhotoId.isEmpty()) {
                        delete(oldPhotoId);
                    }
                    newPhotoId = prefix.getAndIncrement() + "_" + items.get(1).getName();
                    File file = new File(folder + File.separator + newPhotoId);
                    try (FileOutputStream out = new FileOutputStream(file)) {
                        out.write(items.get(1).getInputStream().readAllBytes());
                        out.flush();
                    }
                }
            }


        } catch (FileUploadException | IOException e) {
            e.printStackTrace();
        }
        return newPhotoId;
    }

    @Override
    public void delete(String filename) {
        File file = new File(path + File.separator + filename);
        file.delete();
    }

    /**
     * seek photo
     *
     * @param photoId
     * @return
     */
    private boolean fileExist(String photoId) {
        boolean exist = false;
        for (File file : folder.listFiles()) {
            if (photoId.equals(file.getName())) {
                exist = true;
                break;
            }
        }
        return exist;
    }

    @Override
    public String getPath() {
        return path;
    }
}
