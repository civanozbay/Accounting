package com.accounting.service.implementation;

import com.accounting.dto.CategoryDto;
import com.accounting.dto.CompanyDto;
import com.accounting.entity.Category;
import com.accounting.entity.Company;
import com.accounting.mapper.MapperUtil;
import com.accounting.repository.CategoryRepository;
import com.accounting.service.CategoryService;
import com.accounting.service.SecurityService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;


    public CategoryServiceImpl(CategoryRepository categoryRepository, MapperUtil mapperUtil,SecurityService securityService) {
        this.categoryRepository = categoryRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
    }

    @Override
    public CategoryDto findCategoryById(Long id) {
        return mapperUtil.convert(categoryRepository.findById(id).get(),new CategoryDto());
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        Company company = mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company());
        return categoryRepository.findAllByCompany(company)
                .stream()
                .sorted(Comparator.comparing(Category::getDescription))
                .map(each ->mapperUtil.convert(each, new CategoryDto())).collect(Collectors.toList());
    }

    @Override
    public void create(CategoryDto categoryDto) {
        Category category = mapperUtil.convert(categoryDto, new Category());
        Company company = mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company());
        category.setCompany(company);
        categoryRepository.save(category);
    }

    @Override
    public void update(Long id,CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id).get();
        category.setDescription(categoryDto.getDescription());
        categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        Category category = categoryRepository.findById(id).get();
        category.setDeleted(true);
        category.setDescription(category.getDescription()+ " - "+category.getId());
        categoryRepository.save(category);
    }
}
