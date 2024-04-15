package com.accounting.entity;

import com.accounting.enums.CompanyStatus;
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
@Table(name = "companies")
public class Company extends BaseEntity{

    @Column(unique = true)
    public String title;

    public String phone;
    public String website;
    @Enumerated(EnumType.STRING)
    private CompanyStatus companyStatus;

    @OneToOne
    private Address address;
}
