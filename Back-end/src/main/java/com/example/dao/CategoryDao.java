package com.example.dao;

import com.example.model.Category;

public interface CategoryDao extends MainDao<Category> {

    Category getById(int id);

    void delete(Category category);

}
