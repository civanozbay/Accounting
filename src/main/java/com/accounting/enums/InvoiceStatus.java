package com.accounting.enums;

import lombok.Getter;

@Getter
public enum InvoiceStatus {

    AWAITING_APPROVAL("Awaiting Approval"),
    APROVED("Aproved");

    private final String value;

    InvoiceStatus(String value){
        this.value= value;
    }
}
