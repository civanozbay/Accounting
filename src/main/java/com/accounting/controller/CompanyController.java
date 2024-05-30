package com.accounting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("companies")
public class CompanyController {



    @GetMapping("/list")
    public String navigateToCompanyList(Model model){
//        model.addAttribute()
    return "/company/company-list";
    }
}
