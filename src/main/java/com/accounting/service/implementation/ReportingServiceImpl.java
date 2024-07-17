package com.accounting.service.implementation;

import com.accounting.dto.InvoiceProductDto;
import com.accounting.entity.Company;
import com.accounting.enums.InvoiceStatus;
import com.accounting.mapper.MapperUtil;
import com.accounting.repository.InvoiceProductRepository;
import com.accounting.service.ReportingService;
import com.accounting.service.SecurityService;
import org.springframework.stereotype.Service;
import com.accounting.entity.InvoiceProduct;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportingServiceImpl implements ReportingService {
    private final SecurityService securityService;
    private final InvoiceProductRepository invoiceProductRepository;
    private final MapperUtil mapperUtil;

    public ReportingServiceImpl(SecurityService securityService,InvoiceProductRepository invoiceProductRepository,MapperUtil mapperUtil) {
        this.securityService = securityService;
        this.invoiceProductRepository=invoiceProductRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<InvoiceProductDto> getStockData() {
        Company company = mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company());
        return invoiceProductRepository.findAllByInvoice_InvoiceStatusAndInvoice_Company(InvoiceStatus.APPROVED, company)
                .stream()
                .sorted(Comparator.comparing(InvoiceProduct::getId).reversed())
                .map(each -> mapperUtil.convert(each, new InvoiceProductDto()))
                .collect(Collectors.toList());
    }
}
