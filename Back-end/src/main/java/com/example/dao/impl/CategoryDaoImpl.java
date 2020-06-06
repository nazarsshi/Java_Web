package com.example.dao.impl;

import com.example.dao.CategoryDao;
import com.example.model.Category;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class CategoryDaoImpl implements CategoryDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void delete(Category category) {
        entityManager.remove(category);
    }

    @Override
    public Category getById(int id) {
        return entityManager.find(Category.class, id);
    }

    @Transactional
    @Override
    public Category save(Category category) {
        entityManager.persist(category);
        return category;
    }

    @Override
    public List<Category> getAll() {
        return entityManager
                .createQuery("SELECT c FROM Category c", Category.class)
                .getResultList();
    }

}