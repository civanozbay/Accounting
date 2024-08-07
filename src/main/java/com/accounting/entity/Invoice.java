package com.accounting.entity;

import com.accounting.enums.InvoiceStatus;
import com.accounting.enums.InvoiceType;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoices")
@Where(clause = "is_deleted=false")
public class Invoice extends BaseEntity{

    private String invoiceNo;
    @Enumerated(EnumType.STRING)
    private InvoiceStatus invoiceStatus;
    @Enumerated(EnumType.STRING)
    private InvoiceType invoiceType;

    private LocalDate date;

    @ManyToOne
    ClientVendor clientVendor;
    @ManyToOne
    Company company;
    @OneToMany(mappedBy = "invoice")
    private List<InvoiceProduct> invoiceProducts;
}
