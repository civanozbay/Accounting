package com.accounting.service.implementation;

import com.accounting.dto.UserDto;
import com.accounting.entity.User;
import com.accounting.mapper.MapperUtil;
import com.accounting.repository.UserRepository;
import com.accounting.service.SecurityService;
import com.accounting.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;

    private final SecurityService securityService;

    public UserServiceImpl(UserRepository userRepository, MapperUtil mapperUtil,@Lazy SecurityService securityService) {
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
    }

    @Override
    public UserDto findUserById(Long id) {
        return mapperUtil.convert(userRepository.findUserById(id),new UserDto());
    }

    @Override
    public List<UserDto> getFilteredUsers() {
        List<User> userList;
        if(isCurrentUserRootUser()){
            userList = userRepository.findAllByRole_Description("Admin");
        }else{
            userList = userRepository.findAllByCompany_Title(getCurrentUserCompanyTitle());
        }
        return userList.stream()
                .sorted(Comparator.comparing((User u)  -> u.getCompany().getTitle()).thenComparing(u -> u.getRole().getDescription()))
                .map(user -> {
                    UserDto userDto = mapperUtil.convert(user, new UserDto());
                    userDto.setIsOnlyAdmin(checkIfOnlyAdminForCompany(userDto));
                    return userDto;
                }).collect(Collectors.toList());
    }

    public boolean isCurrentUserRootUser() {
         return securityService.getLoggedInUser().getRole().getDescription().equalsIgnoreCase("root user");
    }

    private String getCurrentUserCompanyTitle() {
        String currentUserName = securityService.getLoggedInUser().getUsername();
        return userRepository.findByUsername(currentUserName).getCompany().getTitle();
    }

    @Override
    public Boolean checkIfOnlyAdminForCompany(UserDto user) {
        if(user.getRole().getDescription().equalsIgnoreCase("Admin")){
            List<User> usersAdmin = userRepository.findAllByCompany_TitleAndRole_Description(user.getCompany().getTitle(), "Admin");
            return usersAdmin.size() == 1;
        }
        return false;
    }

    @Override
    public UserDto findByUsername(String username) {
        return mapperUtil.convert(userRepository.findByUsername(username),new UserDto());
    }


}
