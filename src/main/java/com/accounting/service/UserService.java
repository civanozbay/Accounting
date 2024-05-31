package com.accounting.service;

import com.accounting.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {

    UserDto findUserById(Long id);

    List<UserDto> getFilteredUsers();

    Boolean checkIfOnlyAdminForCompany(UserDto userDto);

    UserDto findByUsername(String username);
}
