package com.accounting.dto;

import com.accounting.enums.ClientVendorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientVendorDto {

    private Long id;
    private String clientVendorName;
    private String phone;
    private String website;
    private ClientVendorType clientVendorType;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private AddressDto address;
    @ManyToOne(fetch = FetchType.LAZY)
    private CompanyDto company;


}
