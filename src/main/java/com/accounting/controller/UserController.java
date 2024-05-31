package com.accounting.controller;

import com.accounting.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public String navigateToUserList(Model model) {
        model.addAttribute("users",userService.getFilteredUsers());
        return "/user/user-list";
    }


}
