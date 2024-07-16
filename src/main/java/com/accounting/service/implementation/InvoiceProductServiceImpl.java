package com.accounting.service.implementation;

import com.accounting.dto.InvoiceProductDto;
import com.accounting.entity.Invoice;
import com.accounting.entity.InvoiceProduct;
import com.accounting.mapper.MapperUtil;
import com.accounting.repository.InvoiceProductRepository;
import com.accounting.service.InvoiceProductService;
import com.accounting.service.InvoiceService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceProductServiceImpl implements InvoiceProductService {

    private final InvoiceProductRepository invoiceProductRepository;
    private final MapperUtil mapperUtil;
    private final InvoiceService invoiceService;

    public InvoiceProductServiceImpl(InvoiceProductRepository invoiceProductRepository,MapperUtil mapperUtil,@Lazy InvoiceService invoiceService) {
        this.invoiceProductRepository = invoiceProductRepository;
        this.mapperUtil = mapperUtil;
        this.invoiceService = invoiceService;
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
        InvoiceProduct invoiceProduct = invoiceProductRepository.findById(id).get();
        invoiceProduct.setIsDeleted(true);
        invoiceProductRepository.save(invoiceProduct);
    }

    @Override
    public InvoiceProductDto findInvoiceProductById(Long id) {
        return mapperUtil.convert(invoiceProductRepository.findInvoiceProductById(id), new InvoiceProductDto());
    }
}
