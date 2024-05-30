package com.accounting.converter;

import com.accounting.dto.UserDto;
import com.accounting.service.UserService;
import org.springframework.core.convert.converter.Converter;

public class UserDtoConverter implements Converter<String , UserDto> {

    private final UserService userService;

    public UserDtoConverter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDto convert(String id) {
        return userService.findUserById(Long.parseLong(id));
    }
}
