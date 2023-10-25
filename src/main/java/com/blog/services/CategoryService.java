package com.blog.services;

import com.blog.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto , int categoryId);
    void deleteCategory(int categoryId);
    CategoryDto getCategory(int categoryId);
    List<CategoryDto> getAllCategories();

}
