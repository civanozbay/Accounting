package com.accounting.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "addresses")
public class Address extends BaseEntity {

    public String addressLine1;
    public String addressLine2;
    public String city;
    public String state;
    public String country;
    public String zipCode;

}
