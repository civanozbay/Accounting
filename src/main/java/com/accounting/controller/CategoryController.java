package com.accounting.controller;

import com.accounting.dto.CategoryDto;
import com.accounting.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public String getCategories(Model model){
        model.addAttribute("categories",categoryService.getAllCategories());
        return "category/category-list";
    }

    @GetMapping("/create")
    public String getCreateCategories(Model model){
        model.addAttribute("newCategory",new CategoryDto());
        return "category/category-create";
    }

    @PostMapping("/create")
    public String createCategory(@Valid @ModelAttribute("newCategory")CategoryDto categoryDto){
        categoryService.create(categoryDto);
        return "redirect:/categories/list";
    }
}
