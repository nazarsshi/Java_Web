package com.example.service.impl;

import com.example.dao.CategoryDao;
import com.example.dto.apiKey.ApiKeyDto;
import com.example.dto.category.CategoryDto;
import com.example.dto.category.MainCategoryDto;
import com.example.error.EntityNotFountException;
import com.example.model.Category;
import com.example.service.CategoryService;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;

    private final ModelMapper modelMapper;

    private final UserService userService;

    @Override
    public MainCategoryDto createCategory(CategoryDto categoryDto) {
        userService.getByApiKey(categoryDto.getApiKey());
        Category category = modelMapper.map(categoryDto, Category.class);
        return modelMapper.map(categoryDao.save(category), MainCategoryDto.class);
    }

    @Override
    public MainCategoryDto getCategoryById(int id) {
        return modelMapper.map(getById(id), MainCategoryDto.class);
    }

    @Override
    public List<MainCategoryDto> getAllCategories() {
        return categoryDao.getAll()
                .stream()
                .map(category -> modelMapper.map(category, MainCategoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(int id, ApiKeyDto apiKeyDto) {
        userService.getByApiKey(apiKeyDto.getApiKey());
        categoryDao.delete(getById(id));
    }

    private Category getById(int id) {
        Category category = categoryDao.getById(id);
        if (category == null) {
            throw new EntityNotFountException("Category is not found with id = " + id);
        }
        return category;
    }

}
