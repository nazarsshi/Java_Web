package com.example.dao.impl;

import com.example.dao.UserDao;
import com.example.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public User update(User user) {
        entityManager.merge(user);
        return user;
    }

    @Transactional
    @Override
    public void delete(User user) {
        entityManager.remove(user);
    }

    @Override
    public User getByEmail(String email) {
        return entityManager
                .createQuery("SELECT u FROM User u WHERE u.email=:email", User.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    @Override
    public User getById(int id) {
        return entityManager.find(User.class, id);
    }

    @Transactional
    @Override
    public User save(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public List<User> getAll() {
        return entityManager
                .createQuery("SELECT u FROM User u", User.class)
                .getResultList();
    }

    @Override
    public User getByApiKey(String apiKey) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.apiKey = :apiKey", User.class)
                .setParameter("apiKey", apiKey)
                .getSingleResult();
    }

}
