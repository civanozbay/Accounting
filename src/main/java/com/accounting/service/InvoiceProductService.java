package com.accounting.service;

import com.accounting.dto.InvoiceProductDto;
import com.accounting.entity.Invoice;
import com.accounting.enums.InvoiceType;


import java.util.List;

public interface InvoiceProductService {

    List<InvoiceProductDto> findAllInvoiceProductsByProductId(Long id);

    List<InvoiceProductDto> getInvoiceProductsOfInvoice(Long id);

    void delete(Long id);

    InvoiceProductDto findInvoiceProductById(Long id);

    void save(InvoiceProductDto invoiceProductDto, Long invoiceId);

    void completeApprovalProcedures(Long invoiceId, InvoiceType invoiceType);

}
