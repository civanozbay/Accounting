package com.accounting.service.implementation;

import com.accounting.dto.CompanyDto;
import com.accounting.entity.Company;
import com.accounting.mapper.MapperUtil;
import com.accounting.repository.CompanyRepository;
import com.accounting.service.CompanyService;

public class CompanyServiceImpl implements CompanyService {

    private final  CompanyRepository companyRepository;
    private final MapperUtil mapperUtil;
    public CompanyServiceImpl(CompanyRepository companyRepository,MapperUtil mapperUtil) {
        this.companyRepository = companyRepository;
        this.mapperUtil=mapperUtil;
    }

    @Override
    public CompanyDto findCompanyById(Long id) {
        return mapperUtil.convert(companyRepository.findById(id),new CompanyDto());
    }
}
