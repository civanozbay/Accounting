package com.accounting.service.implementation;

import com.accounting.dto.CompanyDto;
import com.accounting.entity.Company;
import com.accounting.enums.CompanyStatus;
import com.accounting.mapper.MapperUtil;
import com.accounting.repository.CompanyRepository;
import com.accounting.service.CompanyService;
import com.accounting.service.SecurityService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class CompanyServiceImpl implements CompanyService {

    private final  CompanyRepository companyRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;
    public CompanyServiceImpl(CompanyRepository companyRepository,MapperUtil mapperUtil, SecurityService securityService) {
        this.companyRepository = companyRepository;
        this.mapperUtil=mapperUtil;
        this.securityService =securityService;
    }

    @Override
    public CompanyDto findCompanyById(Long id) {
        return mapperUtil.convert(companyRepository.findById(id).get(),new CompanyDto());
    }

    @Override
    public List<CompanyDto> getAllCompanies() {
        return companyRepository.findAll().stream()
                .filter(company -> company.getId() != 1)
                .sorted(Comparator.comparing(Company::getCompanyStatus).thenComparing(Company::getTitle))
                .map(company -> mapperUtil.convert(company,new CompanyDto())).collect(Collectors.toList());
    }

    @Override
    public void activate(Long companyId) {
        Company company = companyRepository.findById(companyId).get();
        company.setCompanyStatus(CompanyStatus.ACTIVE);
        companyRepository.save(company);
        mapperUtil.convert(company,new CompanyDto());
    }

    @Override
    public void deactivate(Long companyId) {
        Company company = companyRepository.findById(companyId).get();
        company.setCompanyStatus(CompanyStatus.PASSIVE);
        companyRepository.save(company);
        mapperUtil.convert(company,new CompanyDto());
    }

    @Override
    public void create(CompanyDto companyDto) {
        companyDto.setCompanyStatus(CompanyStatus.PASSIVE);
        Company company = mapperUtil.convert(companyDto, new Company());
        companyRepository.save(company);
    }



    @Override
    public List<CompanyDto> getFilteredCompanyForCurrentUser() {
            return getAllCompanies()
                    .stream()
                    .filter(company -> {
                        if(securityService.getLoggedInUser().getRole().getDescription().equalsIgnoreCase("root user")){
                            return true;
                        }else{
                            return company.getTitle().equals(securityService.getLoggedInUser().getCompany().getTitle());
                        }
                    })
                    .collect(Collectors.toList());
    }
    @Override
    public CompanyDto getCompanyByLoggedInUser() {
        return securityService.getLoggedInUser().getCompany();
    }


}
