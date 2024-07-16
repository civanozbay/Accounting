package com.accounting.controller;

import com.accounting.dto.InvoiceDto;
import com.accounting.entity.Invoice;
import com.accounting.enums.ClientVendorType;
import com.accounting.enums.InvoiceType;
import com.accounting.service.ClientVendorService;
import com.accounting.service.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/purchaseInvoices")
public class PurchaseInvoiceController {
    private final InvoiceService invoiceService;
    private final ClientVendorService clientVendorService;

    public PurchaseInvoiceController(InvoiceService invoiceService,ClientVendorService clientVendorService) {
        this.invoiceService = invoiceService;
        this.clientVendorService = clientVendorService;
    }

    @GetMapping("/list")
    public String getAllInvoicesOfCompany(Model model) {
        model.addAttribute("invoices",invoiceService.getAllInvoicesOfCompany(InvoiceType.PURCHASE));
        return "/invoice/purchase-invoice-list";
    }

    @GetMapping("/create")
    public String getCreate(Model model){
        InvoiceDto invoiceDto = invoiceService.getNewInvoice(new InvoiceDto(), InvoiceType.PURCHASE);
        model.addAttribute("newPurchaseInvoice",invoiceDto);
        return "/invoice/purchase-invoice-create";
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

    @ModelAttribute
    public void commonAttributes(Model model){
        model.addAttribute("vendors",clientVendorService.getAllClientVendorsOfCompany(ClientVendorType.VENDOR));
    }
    }