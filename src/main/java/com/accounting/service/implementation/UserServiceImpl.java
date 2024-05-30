package com.accounting.service.implementation;

import com.accounting.dto.UserDto;
import com.accounting.mapper.MapperUtil;
import com.accounting.repository.UserRepository;
import com.accounting.service.UserService;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;

    public UserServiceImpl(UserRepository userRepository, MapperUtil mapperUtil) {
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public UserDto findUserById(Long id) {
        return mapperUtil.convert(userRepository.findUserById(id),new UserDto());
    }
}
