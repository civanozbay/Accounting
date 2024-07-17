package com.accounting.service.implementation;

import com.accounting.dto.InvoiceProductDto;
import com.accounting.entity.Invoice;
import com.accounting.entity.InvoiceProduct;
import com.accounting.mapper.MapperUtil;
import com.accounting.repository.InvoiceProductRepository;
import com.accounting.repository.InvoiceRepository;
import com.accounting.service.InvoiceProductService;
import com.accounting.service.InvoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InvoiceProductServiceImpl implements InvoiceProductService {

    private final InvoiceProductRepository invoiceProductRepository;
    private final MapperUtil mapperUtil;
    private final InvoiceService invoiceService;
    private final InvoiceRepository invoiceRepository;

    public InvoiceProductServiceImpl(InvoiceProductRepository invoiceProductRepository,MapperUtil mapperUtil,@Lazy InvoiceService invoiceService,
                                     InvoiceRepository invoiceRepository) {
        this.invoiceProductRepository = invoiceProductRepository;
        this.mapperUtil = mapperUtil;
        this.invoiceService = invoiceService;
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public List<InvoiceProductDto> findAllInvoiceProductsByProductId(Long id) {
        return invoiceProductRepository.findAllInvoiceProductsByProductId(id)
                .stream()
                .map(each -> mapperUtil.convert(each,new InvoiceProductDto()))
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceProductDto> getInvoiceProductsOfInvoice(Long id) {
        Invoice invoice = mapperUtil.convert(invoiceService.findInvoiceById(id), new Invoice());
        return invoiceProductRepository.findAllByInvoice(invoice)
                .stream()
                .sorted(Comparator.comparing((InvoiceProduct each)-> each.getInvoice().getInvoiceNo()).reversed())
                .map(each -> {
                    InvoiceProductDto dto = mapperUtil.convert(each, new InvoiceProductDto());
                    dto.setTotal(each.getPrice().multiply(BigDecimal.valueOf(each.getQuantity() * (each.getTax()+100)/100d)));
                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        InvoiceProduct invoiceProduct = invoiceProductRepository.findInvoiceProductById(id);
        invoiceProduct.setIsDeleted(true);
        log.info(invoiceProduct.getIsDeleted().toString());
        invoiceProductRepository.save(invoiceProduct);
    }

    @Override
    public InvoiceProductDto findInvoiceProductById(Long id) {
        return mapperUtil.convert(invoiceProductRepository.findInvoiceProductById(id), new InvoiceProductDto());
    }

    @Override
    public void save(InvoiceProductDto invoiceProductDto, Long invoiceId) {
        Invoice invoice = invoiceRepository.findInvoiceById(invoiceId);
        InvoiceProduct invoiceProduct = mapperUtil.convert(invoiceProductDto, new InvoiceProduct());
        invoiceProduct.setInvoice(invoice);
        invoiceProduct.setProfitLoss(BigDecimal.ZERO);
        invoiceProductRepository.save(invoiceProduct);
    }
}
