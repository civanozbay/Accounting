package com.accounting.controller;

import com.accounting.service.ClientVendorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clientVendors")
public class ClientVendorController {

    private final ClientVendorService clientVendorService;

    public ClientVendorController(ClientVendorService clientVendorService) {
        this.clientVendorService = clientVendorService;
    }

    @GetMapping("/list")
    public String getClientVendor(Model model){
        model.addAttribute("clientVendors",clientVendorService.getAllClientsForCurrentUser());
        return "clientVendor/clientVendor-list";
    }

    @GetMapping("/update/{clientId}")
    public String updateClientVendor(@PathVariable("clientId") Long id,Model model){
        model.addAttribute("clientVendor",clientVendorService.findClientVendorById(id));
        return "clientVendor/clientVendor-update";
    }
    @GetMapping("/delete/{clientId}")
    public String deleteClientVendor(@PathVariable("clientId") Long id){
        clientVendorService.delete(id);
        return "redirect:/clientVendors/list";
    }
}
