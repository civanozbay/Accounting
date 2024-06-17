package com.accounting.entity;

import com.accounting.enums.CompanyStatus;
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
@Table(name = "companies")
@Where(clause = "is_deleted=false")
public class Company extends BaseEntity{

    @Column(unique = true)
    public String title;

    public String phone;
    public String website;
    @Enumerated(EnumType.STRING)
    private CompanyStatus companyStatus;

    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private Address address;
}
