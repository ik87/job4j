package ru.job4j.strong_mvc.model;

import java.util.Objects;

/**
 * Define models data
 *
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $id
 * @since 0.1
 */
public class User {
    private String photoid;
    private Integer id;
    private String name;
    private String email;
    private String createDate;
    private String login;
    private String password;
    private Role role;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id.equals(user.id)
                && name.equals(user.name)
                && login.equals(user.login)
                && email.equals(user.email)
                && createDate.equals(user.createDate)
                && photoid.equals(user.photoid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, login, email, createDate, photoid);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPhotoid() {
        return photoid;
    }

    public void setPhotoid(String photoid) {
        this.photoid = photoid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format("%s\t%s\t%s\t%s\t%s", id, name, login, email, createDate);
    }
}
