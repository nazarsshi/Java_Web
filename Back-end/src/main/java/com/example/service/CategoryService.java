package com.example.service;

import com.example.dto.apiKey.ApiKeyDto;
import com.example.dto.category.CategoryDto;
import com.example.dto.category.MainCategoryDto;

import java.util.List;

public interface CategoryService {

    MainCategoryDto createCategory(CategoryDto categoryDto);

    MainCategoryDto getCategoryById(int id);

    List<MainCategoryDto> getAllCategories();

    void deleteCategory(int id, ApiKeyDto apiKeyDto);

}
