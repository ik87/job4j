package ru.job4j.webservice.mapers;

import ru.job4j.webservice.dto.UserDto;
import ru.job4j.webservice.models.User;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;

public interface UserMapper {
    UserDto toDto(User user) throws UnsupportedEncodingException;
    List<UserDto> toDto(Collection<User> user) throws UnsupportedEncodingException;
}
