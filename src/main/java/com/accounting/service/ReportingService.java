package com.accounting.service;

import com.accounting.dto.InvoiceProductDto;

import java.util.List;


public interface ReportingService {
    List<InvoiceProductDto> getStockData();
}
