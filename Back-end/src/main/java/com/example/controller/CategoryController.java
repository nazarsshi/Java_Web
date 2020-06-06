package com.example.controller;

import com.example.dto.apiKey.ApiKeyDto;
import com.example.dto.category.CategoryDto;
import com.example.dto.category.MainCategoryDto;
import com.example.error.ApiError;
import com.example.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/Unity/categories")
@RequiredArgsConstructor
@Api(tags = "Category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create new Category")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New category created", response = CategoryDto.class),
            @ApiResponse(code = 400, message = "Validation error", response = ApiError.class)
    })
    public MainCategoryDto createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        return categoryService.createCategory(categoryDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete category by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Category successfully deleted"),
            @ApiResponse(code = 404, message = "Non-existing category id", response = ApiError.class)
    })
    public void deleteCategory(@PathVariable int id, @RequestBody ApiKeyDto apiKeyDto) {
        categoryService.deleteCategory(id, apiKeyDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get category by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Category found", response = MainCategoryDto.class),
            @ApiResponse(code = 404, message = "Non-existing category id", response = ApiError.class)
    })
    public MainCategoryDto getCategoryById(@PathVariable int id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "View list of all the categories")
    @ApiResponse(code = 200, message = "List of all categories", response = MainCategoryDto.class)
    public List<MainCategoryDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

}