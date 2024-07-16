package com.accounting.service;

import com.accounting.dto.InvoiceProductDto;
import com.accounting.entity.Invoice;


import java.util.List;

public interface InvoiceProductService {

    List<InvoiceProductDto> findAllInvoiceProductsByProductId(Long id);

    List<InvoiceProductDto> getInvoiceProductsOfInvoice(Long id);

    void delete(Long id);

    InvoiceProductDto findInvoiceProductById(Long id);
}
