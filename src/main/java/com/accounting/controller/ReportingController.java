package com.accounting.controller;

import com.accounting.service.InvoiceProductService;
import com.accounting.service.ReportingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reports")
public class ReportingController {

    private final ReportingService reportingService;

    public ReportingController(ReportingService reportingService) {
        this.reportingService = reportingService;
    }

    @GetMapping("/stockData")
    public String getStockReport(Model model){
        model.addAttribute("invoiceProducts",reportingService.getStockData());
        return "/report/stock-report";
    }
    @GetMapping("/profitLossData")
    public String getProfitLossData(Model model){
        model.addAttribute("monthlyProfitLossDataMap",reportingService.getMonthlyProfitLossData());
        return "/report/profit-loss-report";
    }
}
