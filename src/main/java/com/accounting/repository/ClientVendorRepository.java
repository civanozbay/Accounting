package com.accounting.repository;

import com.accounting.entity.ClientVendor;
import com.accounting.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientVendorRepository extends JpaRepository<ClientVendor,Long> {

    ClientVendor findClientVendorById(Long id);

    List<ClientVendor> findClientVendorsByCompany(Company company);
}
