package com.accounting.service.implementation;

import com.accounting.dto.CompanyDto;
import com.accounting.dto.CurrencyApiResponse;
import com.accounting.dto.CurrencyDto;
import com.accounting.dto.InvoiceDto;
import com.accounting.entity.Company;
import com.accounting.enums.InvoiceStatus;
import com.accounting.enums.InvoiceType;
import com.accounting.mapper.MapperUtil;
import com.accounting.repository.InvoiceProductRepository;
import com.accounting.service.DashboardService;
import com.accounting.service.InvoiceService;
import com.accounting.service.ProductService;
import com.accounting.service.SecurityService;
import com.accounting.service.feignClients.CurrencyExchangeClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
public class DashboardServiceImpl implements DashboardService {
    private final InvoiceProductRepository invoiceProductRepository;
    private final MapperUtil mapperUtil;
    private final InvoiceService invoiceService;
    private final SecurityService securityService;
    private final CurrencyExchangeClient client;


    public DashboardServiceImpl(InvoiceProductRepository invoiceProductRepository, MapperUtil mapperUtil, @Lazy InvoiceService invoiceService,
                                SecurityService securityService, CurrencyExchangeClient currencyExchangeClient) {
        this.invoiceProductRepository = invoiceProductRepository;
        this.mapperUtil = mapperUtil;
        this.invoiceService = invoiceService;
        this.securityService = securityService;
        this.client = currencyExchangeClient;
    }

    @Override
    public Map<String, BigDecimal> getAllSummaryNumbers() {
        Map<String, BigDecimal> summaryNumbersMap = new HashMap<>();
        BigDecimal totalCost = BigDecimal.ZERO;
        BigDecimal totalSales = BigDecimal.ZERO;
        BigDecimal profitLoss = BigDecimal.ZERO;
        List<InvoiceDto> allApprovedInvoicesOfCompany = invoiceService.getAllInvoicesByInvoiceStatus(InvoiceStatus.APPROVED);
        for (InvoiceDto invoice : allApprovedInvoicesOfCompany) {
            if (invoice.getInvoiceType() == InvoiceType.PURCHASE) {
                totalCost = totalCost.add(invoiceService.getTotalPriceOfInvoice(invoice.getId())).add(invoiceService.getTotalTaxOfInvoice(invoice.getId()));
            } else {
                totalSales = totalSales.add(invoiceService.getTotalPriceOfInvoice(invoice.getId())).add(invoiceService.getTotalTaxOfInvoice(invoice.getId()));
                profitLoss = profitLoss.add(invoiceService.getProfitLossOfInvoice(invoice.getId()));
            }
        }
        summaryNumbersMap.put("totalCost", totalCost);
        summaryNumbersMap.put("totalSales", totalSales);
        summaryNumbersMap.put("profitLoss", profitLoss);
        return summaryNumbersMap;
    }

    @Override
    public CurrencyDto getExchangeRates() {
        CurrencyApiResponse currency = client.getUsdBasedCurrencies();
        CurrencyDto currencyDto= CurrencyDto.builder()
                .euro(currency.getUsd().getEur())
                .britishPound(currency.getUsd().getGbp())
                .indianRupee(currency.getUsd().getInr())
                .japaneseYen(currency.getUsd().getJpy())
                .canadianDollar(currency.getUsd().getCad())
                .build();

        log.info("Currencies are fetched for the date : "+ currency.getDate());

        return currencyDto;

    }
}
