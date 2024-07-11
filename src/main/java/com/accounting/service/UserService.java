package com.accounting.service;

import com.accounting.dto.UserDto;

import java.util.List;


public interface UserService {

    UserDto findUserById(Long id);

    List<UserDto> getFilteredUsers();

    Boolean checkIfOnlyAdminForCompany(UserDto userDto);

    UserDto findByUsername(String username);

    UserDto create(UserDto userDto);

    Boolean emailExist(UserDto userDto);

    UserDto update(UserDto userDto);

    void delete(Long userId);
}
