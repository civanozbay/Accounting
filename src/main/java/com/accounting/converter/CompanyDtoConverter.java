package com.accounting.converter;

import com.accounting.dto.CompanyDto;
import com.accounting.service.CompanyService;
import org.springframework.core.convert.converter.Converter;

public class CompanyDtoConverter implements Converter<String, CompanyDto> {

    private final CompanyService companyService;

    public CompanyDtoConverter(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    public CompanyDto convert(String id) {

        return companyService.findCompanyById(Long.parseLong(id));
    }
}
