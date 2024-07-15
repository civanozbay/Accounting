package com.accounting.converter;


import com.accounting.dto.InvoiceDto;
import com.accounting.service.InvoiceService;
import lombok.SneakyThrows;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

@Component
@ConfigurationPropertiesBinding
public class InvoiceDtoConverter implements Converter<String, InvoiceDto> {

    private final InvoiceService invoiceService;

    public InvoiceDtoConverter(@Lazy InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @SneakyThrows
    @Override
    public InvoiceDto convert(String id){
        return invoiceService.findInvoiceById(Long.parseLong(id));
    }

}
