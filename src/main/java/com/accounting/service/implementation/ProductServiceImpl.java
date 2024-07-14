package com.accounting.service.implementation;

import com.accounting.dto.CompanyDto;
import com.accounting.dto.ProductDto;
import com.accounting.entity.Company;
import com.accounting.entity.Product;
import com.accounting.mapper.MapperUtil;
import com.accounting.repository.CompanyRepository;
import com.accounting.repository.ProductRepository;
import com.accounting.service.ProductService;
import com.accounting.service.SecurityService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;
    private final CompanyRepository companyRepository;

    public ProductServiceImpl(ProductRepository productRepository, MapperUtil mapperUtil, SecurityService securityService,
                              CompanyRepository companyRepository) {
        this.productRepository = productRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
        this.companyRepository = companyRepository;
    }

    @Override
    public ProductDto findProductById(Long id) {
        return mapperUtil.convert(productRepository.findById(id),new ProductDto());
    }

    @Override
    public List<ProductDto> getAllProducts() {
        Company company = mapperUtil.convert(securityService.getLoggedInUser().getCompany(),new Company());
        return productRepository.findAllByCategoryCompany(company)
                .stream()
                .sorted(Comparator.comparing((Product product) -> product.getCategory().getDescription())
                        .thenComparing(Product::getName))
                .map(each -> mapperUtil.convert(each, new ProductDto()))
                .collect(Collectors.toList());
    }
}
