package com.accounting.controller;

import com.accounting.service.CompanyService;
import com.accounting.service.DashboardService;
import com.accounting.service.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    private final DashboardService dashboardService;
    private final InvoiceService invoiceService;
    private final CompanyService companyService;

    public DashboardController(DashboardService dashboardService, InvoiceService invoiceService, CompanyService companyService) {
        this.dashboardService = dashboardService;
        this.invoiceService = invoiceService;
        this.companyService = companyService;
    }

    @GetMapping("/dashboard")
    public String navigateDashboard(Model model){
        model.addAttribute("companyTitle", companyService.getCompanyByLoggedInUser().getTitle());
        model.addAttribute("summaryNumbers", dashboardService.getAllSummaryNumbers());
        model.addAttribute("invoices", invoiceService.getLastThreeInvoices());
        return "dashboard_final";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

}
