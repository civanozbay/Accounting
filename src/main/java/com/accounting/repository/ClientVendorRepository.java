package com.accounting.repository;

import com.accounting.entity.ClientVendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientVendorRepository extends JpaRepository<ClientVendor,Long> {
}
