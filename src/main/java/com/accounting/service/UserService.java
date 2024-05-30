package com.accounting.service;

import com.accounting.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserDto findUserById(Long id);
}
