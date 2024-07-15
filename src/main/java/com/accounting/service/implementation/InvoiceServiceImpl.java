package com.accounting.service.implementation;

import com.accounting.dto.InvoiceDto;
import com.accounting.mapper.MapperUtil;
import com.accounting.repository.InvoiceRepository;
import com.accounting.service.InvoiceService;
import org.springframework.stereotype.Service;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private  final  InvoiceRepository invoiceRepository;
    private final MapperUtil mapperUtil;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository,MapperUtil mapperUtil) {
        this.invoiceRepository = invoiceRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public InvoiceDto findInvoiceById(Long id) {
        return mapperUtil.convert(invoiceRepository.findById(id).get(), new InvoiceDto());
    }
}
