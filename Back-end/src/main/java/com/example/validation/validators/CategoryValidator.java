package com.example.validation.validators;

import com.example.dao.CategoryDao;
import com.example.dto.category.MainCategoryDto;
import com.example.model.Category;
import com.example.validation.CategoryType;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

@RequiredArgsConstructor
public class CategoryValidator implements ConstraintValidator<CategoryType, MainCategoryDto> {

    private final CategoryDao categoryDao;

    private final ModelMapper mapper;

    @Override
    public boolean isValid(MainCategoryDto categoryDto, ConstraintValidatorContext constraintValidatorContext) {
        if (categoryDto != null) {
            List<Category> allCategory = categoryDao.getAll();
            Category searchCategory = mapper.map(categoryDto, Category.class);
            return allCategory
                    .stream()
                    .anyMatch(category -> isCategory(searchCategory, category));
        }
        return true;
    }

    private boolean isCategory(Category searchCategory, Category category) {
        return searchCategory.getName().equalsIgnoreCase(category.getName())
                && searchCategory.getDescription().equalsIgnoreCase(category.getDescription())
                && searchCategory.getId() == category.getId();
    }

}
