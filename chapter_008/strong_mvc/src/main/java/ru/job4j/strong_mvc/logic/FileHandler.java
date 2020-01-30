package ru.job4j.strong_mvc.logic;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * This interface responsible for  Handle files, upload, delete...
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version 1.0
 * @since 30.01.2020
 */
public interface FileHandler {
    /**
     * Setup configuration, set path were could be save files
     *
     *
     * @param servletContext used for get init parameters (path), look at web.xml
     */
    void config(ServletContext servletContext);

    /**
     * Upload file, if the file is exist then it doesn't do anything
     *
     *
     * @param request servlet request, for example "multipart/form-data"
     * @return get file name
     */
    String upload(HttpServletRequest request);

    /**
     * Delete file from installed path
     *
     * @param file if file is delete get true
     */
    void delete(String file);

    /**
     * Get path
     *
     * @return path
     */
    String getPath();
}
