package com.accounting.controller;

import com.accounting.dto.UserDto;
import com.accounting.service.CompanyService;
import com.accounting.service.RoleService;
import com.accounting.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    private final CompanyService companyService;

    public UserController(UserService userService,RoleService roleService,CompanyService companyService) {
        this.userService = userService;
        this.roleService =roleService;
        this.companyService=companyService;
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

    @PostMapping("/create")
    public String createUser(@Valid @ModelAttribute("newUser")UserDto userDto, BindingResult result){
        boolean emailExist = userService.emailExist(userDto);
        if (result.hasErrors() || emailExist){
            if (emailExist) {
                result.rejectValue("username", " ", "A user with this email already exists. Please try with different email.");
            }
            return "/user/user-create";
        }
        userService.create(userDto);
        return "redirect:/user/user-list";
    }

    @ModelAttribute
    public void commonAttributes(Model model){
        model.addAttribute("userRoles",roleService.getFilteredRolesForCurrentUser());
        model.addAttribute("companies",companyService.getFilteredCompanyForCurrentUser());
    }

}