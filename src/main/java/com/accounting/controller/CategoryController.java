package com.accounting.controller;

import com.accounting.dto.CategoryDto;
import com.accounting.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/update/{categoryId}")
    public String getUpdateCategory(@PathVariable("categoryId")Long id,Model model){
        model.addAttribute("category",categoryService.findCategoryById(id));
        return "category/category-update";
    }

    @PostMapping("/update/{categoryId}")
    public String updateCategory(@Valid @ModelAttribute("category") CategoryDto categoryDto, BindingResult bindingResult, @PathVariable("categoryId") Long categoryId) {
        categoryDto.setId(categoryId);
        categoryService.update(categoryId, categoryDto);
        return "redirect:/categories/list";
    }

    @GetMapping("/delete/{categoryId}")
    public String getDeleteCategory(@PathVariable("categoryId")Long id){
        categoryService.delete(id);
        return "redirect:/categories/list";
    }
}
