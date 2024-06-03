package com.accounting.controller;

import com.accounting.dto.UserDto;
import com.accounting.service.RoleService;
import com.accounting.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService,RoleService roleService) {
        this.userService = userService;
        this.roleService =roleService;
    }

    @GetMapping("/list")
    public String navigateToUserList(Model model) {
        model.addAttribute("users",userService.getFilteredUsers());
        return "/user/user-list";
    }

    @GetMapping("/create")
    public String navigateUserCreate(Model model){
        model.addAttribute("newUser",new UserDto());
        return "/user/user-create";
    }

    @ModelAttribute
    public void commonAttributes(Model model){
        model.addAttribute("userRoles",roleService.getFilteredRolesForCurrentUser());
    }

}