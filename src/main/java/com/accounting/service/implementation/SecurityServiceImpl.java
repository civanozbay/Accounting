package com.accounting.service.implementation;

import com.accounting.dto.UserDto;
import com.accounting.entity.User;
import com.accounting.entity.common.UserPrincipal;
import com.accounting.repository.UserRepository;
import com.accounting.service.SecurityService;
import com.accounting.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;
    private final UserService userService;

    public SecurityServiceImpl(UserRepository userRepository,UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("This user does not exist");
        }
        return new UserPrincipal(user);
    }

    @Override
    public UserDto getLoggedInUser() {
        var currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findByUsername(currentUsername);
    }
}
