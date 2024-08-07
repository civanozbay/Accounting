package com.accounting.service;

import com.accounting.dto.ClientVendorDto;
import com.accounting.entity.ClientVendor;
import com.accounting.enums.ClientVendorType;

import java.util.List;

public interface ClientVendorService {
    List<ClientVendorDto> getAllClientsForCurrentUser();

    ClientVendorDto findClientVendorById(Long id);

    void delete(Long id);

    void save(ClientVendorDto clientVendorDto);

    void update(Long id,ClientVendorDto clientVendorDto);

    List<ClientVendorDto> getAllClientVendorsOfCompany(ClientVendorType clientVendorType);
}
