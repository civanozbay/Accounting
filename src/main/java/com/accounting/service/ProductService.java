package com.accounting.service;

import com.accounting.dto.ProductDto;
import com.accounting.entity.Product;

import java.util.List;

public interface ProductService {
    ProductDto findProductById(Long id);

    List<ProductDto> getAllProducts();
}
