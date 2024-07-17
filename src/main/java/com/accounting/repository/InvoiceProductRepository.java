package com.accounting.repository;

import com.accounting.entity.Company;
import com.accounting.entity.Invoice;
import com.accounting.entity.InvoiceProduct;
import com.accounting.enums.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct, Long> {
    List<InvoiceProduct> findAllInvoiceProductsByProductId(Long id);
    List<InvoiceProduct> findAllByInvoice(Invoice invoice);

    List<InvoiceProduct> findAllByInvoice_Id(Long id);
    InvoiceProduct findInvoiceProductById(Long id);
    List<InvoiceProduct> findAllByInvoice_InvoiceStatusAndInvoice_Company(InvoiceStatus invoiceStatus, Company company);
}
