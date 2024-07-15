package com.accounting.service;

import com.accounting.dto.InvoiceProductDto;


import java.util.List;

public interface InvoiceProductService {

    List<InvoiceProductDto> findAllInvoiceProductsByProductId(Long id);
}
