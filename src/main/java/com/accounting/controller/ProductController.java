package com.accounting.controller;

import com.accounting.dto.ProductDto;
import com.accounting.enums.ProductUnit;
import com.accounting.service.CategoryService;
import com.accounting.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService,CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public String getProducts(Model model){
        model.addAttribute("products",productService.getAllProducts());
        return "/product/product-list";
    }

    @GetMapping("/create")
    public String getCreateProduct(Model model){
        model.addAttribute("newProduct",new ProductDto());
        return "/product/product-create";
    }

    @PostMapping("/create")
    public String createProduct(@Valid @ModelAttribute("newProduct")ProductDto productDto){
        productService.create(productDto);
        return "redirect:/products/list";
    }

    @GetMapping("/update/{productId}")
    public String getUpdateProduct(@PathVariable("productId")Long id,Model model){
        model.addAttribute("product",productService.findProductById(id));
        return "/product/product-update";
    }

    @PostMapping("/update/{productId}")
    public String updateProduct(@PathVariable("productId")Long id,@Valid @ModelAttribute("product")ProductDto productDto){
        productDto.setId(id);
        productService.update(id,productDto);
        return "redirect:/products/list";
    }

    @GetMapping("/delete/{productId}")
    public String deleteProduct(@PathVariable("productId")Long id){
        productService.delete(id);
        return "redirect:/products/list";
    }
    @ModelAttribute
    public void commonAttributes(Model model){
        model.addAttribute("categories",categoryService.getAllCategories());
        model.addAttribute("productUnits", Arrays.asList(ProductUnit.values()));
    }
}
