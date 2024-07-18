package com.accounting.service.implementation;

import com.accounting.dto.InvoiceDto;
import com.accounting.dto.InvoiceProductDto;
import com.accounting.entity.Company;
import com.accounting.entity.Invoice;
import com.accounting.enums.InvoiceStatus;
import com.accounting.enums.InvoiceType;
import com.accounting.mapper.MapperUtil;
import com.accounting.repository.InvoiceRepository;
import com.accounting.service.InvoiceProductService;
import com.accounting.service.InvoiceService;
import com.accounting.service.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InvoiceServiceImpl implements InvoiceService {

    private  final  InvoiceRepository invoiceRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;
    private final InvoiceProductService invoiceProductService;

    public InvoiceServiceImpl(SecurityService securityService,InvoiceRepository invoiceRepository,MapperUtil mapperUtil,InvoiceProductService invoiceProductService) {
        this.invoiceRepository = invoiceRepository;
        this.mapperUtil = mapperUtil;
        this.securityService =securityService;
        this.invoiceProductService = invoiceProductService;
    }

    @Override
    public InvoiceDto findInvoiceById(Long id) {
        return mapperUtil.convert(invoiceRepository.findInvoiceById(id), new InvoiceDto());
    }

    @Override
    public List<InvoiceDto> getAllInvoicesOfCompany(InvoiceType invoiceType) {
        Company company = mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company());
        return invoiceRepository.findInvoicesByCompanyAndInvoiceType(company, invoiceType)
                .stream()
                .sorted(Comparator.comparing(Invoice::getInvoiceNo))
                .map(each -> mapperUtil.convert(each, new InvoiceDto()))
                .peek(this::calculateInvoiceDetails)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        Invoice invoice = invoiceRepository.findById(id).get();
        invoiceProductService.getInvoiceProductsOfInvoice(invoice.getId())
                .forEach(invoiceProductDto -> invoiceProductService.delete(invoiceProductDto.getId()));
        invoice.setIsDeleted(true);
        invoiceRepository.save(invoice);
    }
    private void calculateInvoiceDetails(InvoiceDto invoiceDto) {
        invoiceDto.setPrice(getTotalPriceOfInvoice(invoiceDto.getId()));
        invoiceDto.setTax(getTotalTaxOfInvoice(invoiceDto.getId()));
        invoiceDto.setTotal(getTotalPriceOfInvoice(invoiceDto.getId()).add(getTotalTaxOfInvoice(invoiceDto.getId())));
    }

    @Override
    public BigDecimal getTotalPriceOfInvoice(Long id){
        Invoice invoice = invoiceRepository.findInvoiceById(id);
        List<InvoiceProductDto> invoiceProductsOfInvoice = invoiceProductService.getInvoiceProductsOfInvoice(invoice.getId());
        return invoiceProductsOfInvoice.stream()
                .map(p -> p.getPrice()
                        .multiply(BigDecimal.valueOf((long) p.getQuantity())))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    @Override
    public BigDecimal getTotalTaxOfInvoice(Long id){
        Invoice invoice = invoiceRepository.findInvoiceById(id);
        List<InvoiceProductDto> invoiceProductsOfInvoice = invoiceProductService.getInvoiceProductsOfInvoice(invoice.getId());
        return invoiceProductsOfInvoice.stream()
                .map(p -> p.getPrice()
                        .multiply(BigDecimal.valueOf(p.getQuantity() * p.getTax() /100d))
                        .setScale(2, RoundingMode.HALF_UP))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    @Override
    public InvoiceDto getNewInvoice(InvoiceDto invoiceDto,InvoiceType invoiceType) {
        invoiceDto.setDate(LocalDate.now());
        Company company = mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company());
        List<Invoice> collect = invoiceRepository.findInvoicesByCompanyAndInvoiceType(company,invoiceType)
                .stream()
                .sorted(Comparator.comparing(Invoice::getInvoiceNo).reversed())
                .collect(Collectors.toList());
        if(collect.isEmpty()){
            invoiceDto.setInvoiceNo(invoiceType.name().charAt(0)+"-001");
        }else{
            Invoice lastInvoice = collect.get(0);
            int invoiceNo = Integer.parseInt(lastInvoice.getInvoiceNo().substring(2)) + 1;
            invoiceDto.setInvoiceNo(invoiceType.name().charAt(0)+"-"+String.format("%03d", invoiceNo));
        }
        return invoiceDto;
    }

    @Override
    public List<InvoiceDto> getAllInvoicesByInvoiceStatus(InvoiceStatus invoiceStatus) {
        Company company = mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company());
        List<Invoice> invoices = invoiceRepository.findInvoicesByCompanyAndInvoiceStatus(company, InvoiceStatus.APPROVED);
        return invoices
                .stream()
                .map(invoice -> mapperUtil.convert(invoice, new InvoiceDto()))
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal getProfitLossOfInvoice(Long id) {
        Invoice invoice = invoiceRepository.findInvoiceById(id);
        List<InvoiceProductDto> invoiceProductsOfInvoice = invoiceProductService.getInvoiceProductsOfInvoice(invoice.getId());
        return invoiceProductsOfInvoice.stream()
                .map(InvoiceProductDto::getProfitLoss)
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    @Override
    public List<InvoiceDto> getLastThreeInvoices() {
        Company company = mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company());
        return invoiceRepository.findInvoicesByCompanyAndInvoiceStatusOrderByDateDesc(company, InvoiceStatus.APPROVED)
                .stream()
                .limit(3)
                .map(each -> mapperUtil.convert(each, new InvoiceDto()))
                .peek(this::calculateInvoiceDetails)
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceDto create(InvoiceDto invoiceDto,InvoiceType invoiceType) {
        invoiceDto.setCompany(securityService.getLoggedInUser().getCompany());
        invoiceDto.setInvoiceStatus(InvoiceStatus.AWAITING_APPROVAL);
        invoiceDto.setInvoiceType(invoiceType);
        Invoice invoice = invoiceRepository.save(mapperUtil.convert(invoiceDto, new Invoice()));
        return mapperUtil.convert(invoice,new InvoiceDto());
   }

    @Override
    public void update(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId).get();
        invoiceRepository.save(invoice);
    }

    @Override
    public void approve(Long invoiceId) {
        Invoice invoice = invoiceRepository.findInvoiceById(invoiceId);
        invoiceProductService.completeApprovalProcedures(invoiceId, invoice.getInvoiceType());
        invoice.setInvoiceStatus(InvoiceStatus.APPROVED);
        invoice.setDate(LocalDate.now());
        invoiceRepository.save(invoice);
    }

    @Override
    public InvoiceDto printInvoice(Long id) {
        InvoiceDto invoiceDto = mapperUtil.convert(invoiceRepository.findInvoiceById(id), new InvoiceDto());
        calculateInvoiceDetails(invoiceDto);
        return invoiceDto;
    }
}
