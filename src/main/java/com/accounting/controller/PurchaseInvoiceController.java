package com.accounting.controller;

import com.accounting.dto.InvoiceDto;
import com.accounting.dto.InvoiceProductDto;
import com.accounting.enums.ClientVendorType;
import com.accounting.enums.InvoiceType;
import com.accounting.service.ClientVendorService;
import com.accounting.service.InvoiceProductService;
import com.accounting.service.InvoiceService;
import com.accounting.service.ProductService;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/purchaseInvoices")
public class PurchaseInvoiceController {
    private final InvoiceService invoiceService;
    private final ClientVendorService clientVendorService;
    private final ProductService productService;
    private final InvoiceProductService invoiceProductService;


    public PurchaseInvoiceController(InvoiceService invoiceService,ClientVendorService clientVendorService,ProductService productService,InvoiceProductService invoiceProductService) {
        this.invoiceService = invoiceService;
        this.clientVendorService = clientVendorService;
        this.productService = productService;
        this.invoiceProductService = invoiceProductService;
    }

    @GetMapping("/list")
    public String getAllInvoicesOfCompany(Model model) {
        model.addAttribute("invoices",invoiceService.getAllInvoicesOfCompany(InvoiceType.PURCHASE));
        return "/invoice/purchase-invoice-list";
    }

    @GetMapping("/create")
    public String getCreatePurchase(Model model){
        InvoiceDto invoiceDto = invoiceService.getNewInvoice(new InvoiceDto(), InvoiceType.PURCHASE);
        model.addAttribute("newPurchaseInvoice",invoiceDto);
        return "/invoice/purchase-invoice-create";
    }
    @PostMapping("/create")
    public String createPurchase(@ModelAttribute("newPurchaseInvoice")InvoiceDto invoiceDto){
        InvoiceDto invoice = invoiceService.create(invoiceDto, InvoiceType.PURCHASE);
        return "redirect:/purchaseInvoices/update/"+invoice.getId();
    }

    @GetMapping("/update/{invoiceId}")
    public String getUpdatePurchase(@PathVariable("invoiceId")Long id,Model model){
        model.addAttribute("invoice",invoiceService.findInvoiceById(id));
        model.addAttribute("newInvoiceProduct",new InvoiceProductDto());
        model.addAttribute("invoiceProducts",invoiceProductService.getInvoiceProductsOfInvoice(id));
        return "/invoice/purchase-invoice-update";
    }
    @PostMapping("/update/{invoiceId}")
    public String updatePurchase(@PathVariable("invoiceId")Long id){
        invoiceService.update(id);
        return "redirect:/purchaseInvoices/list";
    }

    @GetMapping("/approve/{invoiceId}")
    public String getApprove(@PathVariable("invoiceId")Long invoiceId){
        invoiceService.approve(invoiceId);
        return "redirect:/purchaseInvoices/list";
    }
    @PostMapping("/addInvoiceProduct/{invoiceId}")
    public String addInvoiceProduct(@Valid @ModelAttribute("newInvoiceProduct")InvoiceProductDto invoiceProductDto,@PathVariable("invoiceId")Long invoiceId){
        invoiceProductService.save(invoiceProductDto,invoiceId);
        return "redirect:/purchaseInvoices/update/" + invoiceId;
    }
    @GetMapping("/removeInvoiceProduct/{invoiceId}/{invoiceProductId}")
    public String removeInvoiceProduct(@PathVariable("invoiceId")Long invoiceId, @PathVariable("invoiceProductId") Long invoiceProductId){
        invoiceProductService.delete(invoiceProductId);
        return "redirect:/purchaseInvoices/update/" + invoiceId;
    }
    @GetMapping("/delete/{purchaseId}")
    public String getDeletePurchase(@PathVariable("purchaseId")Long id){
        invoiceService.delete(id);
        return "redirect:/purchaseInvoices/list";
    }


    @ModelAttribute
    public void commonAttributes(Model model){
        model.addAttribute("vendors",clientVendorService.getAllClientVendorsOfCompany(ClientVendorType.VENDOR));
        model.addAttribute("products",productService.getAllProducts());
    }
    }