package com.example.dao.impl;

import com.example.dao.TaskDao;
import com.example.model.Task;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class TaskDaoImpl implements TaskDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public Task update(Task task) {
        entityManager.merge(task);
        return task;
    }

    @Transactional
    @Override
    public void delete(Task task) {
        entityManager.remove(task);
    }

    @Override
    public Task getById(int id) {
        return entityManager.find(Task.class, id);
    }

    @Transactional
    @Override
    public Task save(Task task) {
        entityManager.persist(task);
        return task;
    }

    @Override
    public List<Task> getAll() {
        return entityManager
                .createQuery("SELECT t FROM Task t", Task.class)
                .getResultList();
    }

}