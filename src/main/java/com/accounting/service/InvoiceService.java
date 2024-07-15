package com.accounting.service;

import com.accounting.dto.InvoiceDto;

public interface InvoiceService {

    InvoiceDto findInvoiceById(Long id);
}
