package com.accounting.controller;

import com.accounting.service.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/list")
    public String navigateToCompanyList(Model model){
    model.addAttribute("companies",companyService.getAllCompanies());
    return "/company/company-list";
    }

    @GetMapping("/update/{companyId}")
    public String navigateUpdateCompany(@PathVariable("companyId")Long companyId , Model model ){
        model.addAttribute("company",companyService.findCompanyById(companyId));
        return "/company/company-update";
    }

    @GetMapping("/activate/{companyId}")
    public String activateCompany(@PathVariable("companyId")Long companyId ){
        companyService.activate(companyId);
        return "redirect:/companies/list";
    }

    @GetMapping("/deactivate/{companyId}")
    public String deactivateCompany(@PathVariable("companyId")Long companyId  ){
        companyService.deactivate(companyId);
        return "redirect:/companies/list";
    }


}
