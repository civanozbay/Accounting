package com.accounting.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "invoice_products")
public class InvoiceProduct extends BaseEntity{

    private int quantity;
    private BigDecimal price;
    private BigDecimal profitLoss;
    private int tax;
    private int remainingQty;

    @ManyToOne(fetch = FetchType.LAZY)
    Invoice invoice;
    @ManyToOne(fetch = FetchType.LAZY)
    Product product;
}
