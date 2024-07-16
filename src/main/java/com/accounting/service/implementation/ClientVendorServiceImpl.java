package com.accounting.service.implementation;

import com.accounting.dto.ClientVendorDto;
import com.accounting.dto.CompanyDto;
import com.accounting.entity.ClientVendor;
import com.accounting.entity.Company;
import com.accounting.enums.ClientVendorType;
import com.accounting.mapper.MapperUtil;
import com.accounting.repository.ClientVendorRepository;
import com.accounting.repository.CompanyRepository;
import com.accounting.service.ClientVendorService;
import com.accounting.service.SecurityService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientVendorServiceImpl implements ClientVendorService {
    private final ClientVendorRepository clientVendorRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;
    private final CompanyRepository companyRepository;

    @Override
    public void update(Long id,ClientVendorDto clientVendorDto) {
        ClientVendor savedClientVendor = clientVendorRepository.findById(id).get();
        clientVendorDto.getAddress().setId(savedClientVendor.getAddress().getId());
        clientVendorDto.setCompany(securityService.getLoggedInUser().getCompany());
        ClientVendor updatedClientVendor = mapperUtil.convert(clientVendorDto, new ClientVendor());
        clientVendorRepository.save(updatedClientVendor);
    }

    public ClientVendorServiceImpl(ClientVendorRepository clientVendorRepository, MapperUtil mapperUtil, SecurityService securityService,
                                   CompanyRepository companyRepository) {
        this.clientVendorRepository = clientVendorRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
        this.companyRepository = companyRepository;
    }

    @Override
    public List<ClientVendorDto> getAllClientsForCurrentUser() {
        Company company = mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company());
        return clientVendorRepository.findClientVendorsByCompany(company)
                .stream()
                .sorted(Comparator.comparing(ClientVendor::getClientVendorType).reversed()
                        .thenComparing(ClientVendor::getClientVendorName))
                .map(each -> mapperUtil.convert(each,new ClientVendorDto()))
                .collect(Collectors.toList());
    }

    @Override
    public ClientVendorDto findClientVendorById(Long id) {
        return mapperUtil.convert(clientVendorRepository.findClientVendorById(id),new ClientVendorDto());
    }

    @Override
    public void delete(Long id) {
        ClientVendor clientVendor = clientVendorRepository.findClientVendorById(id);
        clientVendor.setIsDeleted(true);
        clientVendorRepository.save(clientVendor);
    }

    @Override
    public void save(ClientVendorDto clientVendorDto) {
        clientVendorDto.setCompany(securityService.getLoggedInUser().getCompany());
        ClientVendor clientVendor = mapperUtil.convert(clientVendorDto, new ClientVendor());
        clientVendorRepository.save(clientVendor);
    }

    @Override
    public List<ClientVendorDto> getAllClientVendorsOfCompany(ClientVendorType clientVendorType) {
        Company company = mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company());
        return clientVendorRepository.findClientVendorsByCompany(company)
                .stream()
                .filter(clientVendor -> clientVendor.getClientVendorType()==clientVendorType)
                .map(clientVendor -> mapperUtil.convert(clientVendor,new ClientVendorDto()))
                .collect(Collectors.toList());
    }
}
