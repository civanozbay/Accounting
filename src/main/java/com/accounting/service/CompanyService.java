package com.accounting.service;

import com.accounting.dto.CompanyDto;
import com.accounting.entity.Company;
import org.springframework.stereotype.Service;

@Service
public interface CompanyService {

    CompanyDto findCompanyById(Long id);

}
