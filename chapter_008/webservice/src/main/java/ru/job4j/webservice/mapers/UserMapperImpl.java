package ru.job4j.webservice.mapers;

import ru.job4j.webservice.dto.UserDto;
import ru.job4j.webservice.models.User;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

public class UserMapperImpl implements UserMapper {

    private final static String DATA_FORMAT = "dd-MM-yyyy HH:mm";


    @Override
    public UserDto toDto(User user) throws UnsupportedEncodingException {
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getId());
        userDto.setLogin(user.getLogin());
        userDto.setEmail(user.getEmail());
        userDto.setPhoto(bytesToString(user.getPhoto()));
        userDto.setRole(user.getRole() != null ? user.getRole().getRole() : null);
        userDto.setRoleId(user.getRole() != null ? user.getRole().getId() : null);
        userDto.setCreated(millisecondToStringDate(user.getCreated()));
        userDto.setPassword(user.getPassword());
        return userDto;
    }

    @Override
    public List<UserDto> toDto(Collection<User> users) throws UnsupportedEncodingException {
        List<UserDto> usersDto = new ArrayList<>();
        for (User user : users) {
            usersDto.add(this.toDto(user));
        }
        return usersDto;
    }

    private String bytesToString(byte[] bytes) throws UnsupportedEncodingException {

        return bytes != null
                ? new String(Base64.getEncoder().encode(bytes), "UTF-8")
                : null;
    }

    /**
     * Convert millisecond to data string
     *
     * @param millisecond mls
     * @return string data. Format as DATA_FORMAT
     */
    public static String millisecondToStringDate(Long millisecond) {
        SimpleDateFormat f = new SimpleDateFormat(DATA_FORMAT);
        Date result = new Date(millisecond);
        return f.format(result).toString();
    }
}
