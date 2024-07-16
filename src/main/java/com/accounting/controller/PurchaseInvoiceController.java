package com.accounting.controller;

import com.accounting.enums.InvoiceType;
import com.accounting.service.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/purchaseInvoices")
public class PurchaseInvoiceController {
    private final InvoiceService invoiceService;

    public PurchaseInvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/list")
    public String getAllInvoicesOfCompany(Model model) {
        model.addAttribute("invoices",invoiceService.getAllInvoicesOfCompany(InvoiceType.PURCHASE));
        return "/invoice/purchase-invoice-list";
    }

    @GetMapping("/update/{purchaseId}")
    public String getUpdatePurchase(@PathVariable("purchaseId")Long id){

        return "/invoice/purchase-invoice-update";
    }

    @GetMapping("/delete/{purchaseId}")
    public String getDeletePurchase(@PathVariable("purchaseId")Long id){
        invoiceService.delete(id);
        return "redirect:/purchaseInvoices/list";
    }
    }