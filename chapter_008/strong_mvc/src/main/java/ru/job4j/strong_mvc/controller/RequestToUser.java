package ru.job4j.strong_mvc.controller;

import ru.job4j.strong_mvc.model.User;

import javax.servlet.http.HttpServletRequest;

class RequestToUser {
    static User getUserParameters(HttpServletRequest req) {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String created = req.getParameter("created");
        return new User(Integer.valueOf(id), name, login, email, created);
    }
}
