package com.accounting.controller;

import com.accounting.dto.ClientVendorDto;
import com.accounting.enums.ClientVendorType;
import com.accounting.service.AddressService;
import com.accounting.service.ClientVendorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

@Controller
@RequestMapping("/clientVendors")
public class ClientVendorController {

    private final ClientVendorService clientVendorService;
    private final AddressService addressService;

    public ClientVendorController(ClientVendorService clientVendorService,AddressService addressService) {
        this.clientVendorService = clientVendorService;
        this.addressService = addressService;
    }

    @GetMapping("/list")
    public String getClientVendors(Model model){
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

    @GetMapping("/create")
    public String getCreateClientVendor(Model model){
        model.addAttribute("newClientVendor",new ClientVendorDto());
        return "clientVendor/clientVendor-create";
    }

    @PostMapping("/create")
    public String createClientVendor(@Valid @ModelAttribute("newClientVendor")ClientVendorDto clientVendorDto){
        clientVendorService.save(clientVendorDto);
        return "redirect:/clientVendors/list";
    }

    @ModelAttribute
    public void commonAttributes(Model model){
        model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));
        model.addAttribute("countries",addressService.getCountryList());
    }
}
