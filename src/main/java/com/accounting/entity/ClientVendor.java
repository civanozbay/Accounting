package com.accounting.entity;

import com.accounting.enums.ClientVendorType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients_vendors")
public class ClientVendor extends BaseEntity{

    private String clientVendorName;
    private String phone;
    private String website;

    @Enumerated(EnumType.STRING)
    ClientVendorType clientVendorType;

    @OneToOne
    Address address;
    @ManyToOne
    Company company;
}
