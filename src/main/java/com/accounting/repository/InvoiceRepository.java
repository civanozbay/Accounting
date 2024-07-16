package com.accounting.repository;

import com.accounting.entity.Company;
import com.accounting.entity.Invoice;
import com.accounting.enums.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Long> {

    Invoice findInvoiceById(Long id);
    List<Invoice> findAllByCompany(Company company);

    List<Invoice> findInvoicesByCompanyAndInvoiceType(Company company,InvoiceType invoiceType);
}
