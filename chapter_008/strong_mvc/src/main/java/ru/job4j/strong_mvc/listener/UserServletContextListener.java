package ru.job4j.strong_mvc.listener;

import ru.job4j.strong_mvc.logic.FileHandler;
import ru.job4j.strong_mvc.logic.FileHandlerService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * This is listener class. It creates and configures the {@link FileHandler}
 * then puts it in the context
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version 1.0
 * @since 30.01.2020
 */
public class UserServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        FileHandler fileHandler = new FileHandlerService();
        fileHandler.config(sce.getServletContext());
        sce.getServletContext().setAttribute("fileHandler", fileHandler);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
