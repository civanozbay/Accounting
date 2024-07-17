package com.accounting.service;

import com.accounting.dto.CompanyDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CompanyService {

    CompanyDto findCompanyById(Long id);

    List<CompanyDto> getAllCompanies();

    void activate(Long companyId);

    void deactivate(Long companyId);

    void create(CompanyDto companyDto);

    List<CompanyDto> getFilteredCompanyForCurrentUser();
    CompanyDto getCompanyByLoggedInUser();

}
