package com.accounting.controller;

import com.accounting.dto.InvoiceDto;
import com.accounting.dto.InvoiceProductDto;
import com.accounting.enums.InvoiceType;
import com.accounting.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/salesInvoices")
public class SalesInvoiceController {
    private final CompanyService companyService;
    private final ProductService productService;
    private final InvoiceService invoiceService;
    private final InvoiceProductService invoiceProductService;
    private final ClientVendorService clientVendorService;
    public SalesInvoiceController(InvoiceService invoiceService,ClientVendorService clientVendorService,InvoiceProductService invoiceProductService,ProductService productService,CompanyService companyService) {
        this.invoiceService = invoiceService;
        this.clientVendorService = clientVendorService;
        this.invoiceProductService = invoiceProductService;
        this.productService= productService;
        this.companyService =companyService;
    }

    @GetMapping("/list")
    public String getSalesInvoices(Model model){
        model.addAttribute("invoices",invoiceService.getAllInvoicesOfCompany(InvoiceType.SALES));
        return "/invoice/sales-invoice-list";
    }
    @GetMapping("/create")
    public String getCreateInvoice(Model model){
        model.addAttribute("newSalesInvoice",invoiceService.getNewInvoice(new InvoiceDto(),InvoiceType.SALES));
        return "/invoice/sales-invoice-create";
    }

    @PostMapping("/create")
    public String createInvoice(@ModelAttribute("newSalesInvoice")InvoiceDto invoiceDto){
        invoiceService.create(invoiceDto,InvoiceType.SALES);
        return "redirect:/salesInvoices/list";
    }
    @GetMapping("/update/{invoiceId}")
    public String getUpdateSalesInvoice(@PathVariable("invoiceId")Long id,Model model){
        model.addAttribute("invoice",invoiceService.findInvoiceById(id));
        model.addAttribute("newInvoiceProduct",new InvoiceProductDto());
        model.addAttribute("invoiceProducts",invoiceProductService.getInvoiceProductsOfInvoice(id));
        return "/invoice/sales-invoice-update";
    }

    @PostMapping("/addInvoiceProduct/{invoiceId}")
    public String addSaleProduct(@Valid @ModelAttribute("newInvoiceProduct")InvoiceProductDto invoiceProductDto, @PathVariable("invoiceId")Long invoiceId){
        invoiceProductService.save(invoiceProductDto,invoiceId);
        return "redirect:/salesInvoices/update/"+invoiceId;
    }

    @GetMapping("/removeInvoiceProduct/{invoiceId}/{invoiceProductId}")
    public String removeInvoice(@PathVariable("invoiceId")Long invoiceId){
        invoiceProductService.delete(invoiceId);
        return "redirect:/salesInvoices/update/"+invoiceId;
    }

    @GetMapping("/delete/{invoiceId}")
    public String deleteSalesInvoice(@PathVariable("invoiceId")Long invoiceId){
        invoiceService.delete(invoiceId);
        return "redirect:/salesInvoices/list";
    }

    @GetMapping("/approve/{invoiceId}")
    public String approveSalesInvoice(@PathVariable("invoiceId")Long invoiceId){
        invoiceService.approve(invoiceId);
        return "redirect:/salesInvoices/list";
    }

    @GetMapping(value = "/print/{invoiceId}")
    public String print(@PathVariable("invoiceId") Long id, Model model)  {
        model.addAttribute("invoice", invoiceService.printInvoice(id));
        model.addAttribute("invoiceProducts",invoiceProductService.getInvoiceProductsOfInvoice(id));
        return "invoice/invoice_print";
    }

    @ModelAttribute
    public void commonAttributes(Model model){
        model.addAttribute("clients",clientVendorService.getAllClientsForCurrentUser());
        model.addAttribute("products",productService.getAllProducts());
        model.addAttribute("company", companyService.getCompanyByLoggedInUser());
    }

}

