package com.accounting.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
