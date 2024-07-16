package com.accounting.service;

import com.accounting.dto.InvoiceDto;
import com.accounting.enums.InvoiceType;

import java.math.BigDecimal;
import java.util.List;

public interface InvoiceService {

    InvoiceDto findInvoiceById(Long id);

    List<InvoiceDto> getAllInvoicesOfCompany(InvoiceType invoiceType);

    void delete(Long id);

    BigDecimal getTotalPriceOfInvoice(Long id);
    BigDecimal getTotalTaxOfInvoice(Long id);
}
