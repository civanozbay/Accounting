package com.accounting.service.implementation;

import com.accounting.entity.User;
import com.accounting.entity.common.UserPrincipal;
import com.accounting.repository.UserRepository;
import com.accounting.service.SecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;

    public SecurityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return new UserPrincipal(user);
    }
}