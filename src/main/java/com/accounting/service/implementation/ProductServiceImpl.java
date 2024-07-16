package com.accounting.service.implementation;

import com.accounting.dto.CompanyDto;
import com.accounting.dto.InvoiceProductDto;
import com.accounting.dto.ProductDto;
import com.accounting.entity.Category;
import com.accounting.entity.Company;
import com.accounting.entity.InvoiceProduct;
import com.accounting.entity.Product;
import com.accounting.mapper.MapperUtil;
import com.accounting.repository.CompanyRepository;
import com.accounting.repository.InvoiceProductRepository;
import com.accounting.repository.ProductRepository;
import com.accounting.service.InvoiceProductService;
import com.accounting.service.ProductService;
import com.accounting.service.SecurityService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;
    private final CompanyRepository companyRepository;
    private final InvoiceProductService invoiceProductService;

    public ProductServiceImpl(ProductRepository productRepository, MapperUtil mapperUtil, SecurityService securityService,
                              CompanyRepository companyRepository,InvoiceProductService invoiceProductService) {
        this.productRepository = productRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
        this.companyRepository = companyRepository;
        this.invoiceProductService = invoiceProductService;
    }

    @Override
    public ProductDto findProductById(Long id) {
        return mapperUtil.convert(productRepository.findById(id).get(),new ProductDto());
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

    @Override
    public void create(ProductDto productDto) {
        Product product = mapperUtil.convert(productDto, new Product());
        product.setQuantityInStock(0);
        productRepository.save(product);
    }

    @Override
    public void update(Long id, ProductDto productDto) {
        productDto.setId(id);
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("Product " + productDto.getName() + "not found"));
        final int quantityInStock = productDto.getQuantityInStock() == null ? product.getQuantityInStock() : productDto.getQuantityInStock();
        productDto.setQuantityInStock(quantityInStock);
        productRepository.save(mapperUtil.convert(productDto,new Product()));
    }

    @Override
    public void delete(Long id) {
        Product product = productRepository.findById(id).get();
        List<InvoiceProductDto> allInvoiceProducts = invoiceProductService.findAllInvoiceProductsByProductId(id);
        if (allInvoiceProducts.isEmpty() && product.getQuantityInStock() == 0) {
            product.setIsDeleted(true);
        } else {
            System.out.println("You can't delete this product");
        }
        productRepository.save(product);
    }
}
