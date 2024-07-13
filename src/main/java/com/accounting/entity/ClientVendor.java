package com.accounting.entity;

import com.accounting.enums.ClientVendorType;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients_vendors")
@Where(clause = "is_deleted=false")
public class ClientVendor extends BaseEntity{

    private String clientVendorName;
    private String phone;
    private String website;

    @Enumerated(EnumType.STRING)
    ClientVendorType clientVendorType;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    Address address;
    @ManyToOne(fetch = FetchType.LAZY)
    Company company;
}
