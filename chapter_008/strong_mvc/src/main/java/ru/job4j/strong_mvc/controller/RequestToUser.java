package ru.job4j.strong_mvc.controller;

import ru.job4j.strong_mvc.model.User;

import javax.servlet.http.HttpServletRequest;
/**
 * This is helper class
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version 1.0
 * @since 30.01.2020
 */
class RequestToUser {
    static User getUserParameters(HttpServletRequest req) {
        User user = new User();
        user.setId(Integer.valueOf(req.getParameter("id")));
        user.setName(req.getParameter("name"));
        user.setLogin(req.getParameter("login"));
        user.setEmail(req.getParameter("email"));
        user.setCreateDate(req.getParameter("created"));
        user.setPhotoid(req.getParameter("photoid"));
        return user;
    }
}
