package com.accounting.repository;

import com.accounting.entity.Invoice;
import com.accounting.entity.InvoiceProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct, Long> {
    List<InvoiceProduct> findAllInvoiceProductsByProductId(Long id);
    List<InvoiceProduct> findAllByInvoice(Invoice invoice);

    InvoiceProduct findInvoiceProductById(Long id);
}
