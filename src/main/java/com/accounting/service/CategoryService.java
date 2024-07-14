package com.accounting.service;

import com.accounting.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto findCategoryById(Long id);

    List<CategoryDto> getAllCategories();

    void create(CategoryDto categoryDto);

    void update(Long id,CategoryDto categoryDto);


    void delete(Long id);
}
