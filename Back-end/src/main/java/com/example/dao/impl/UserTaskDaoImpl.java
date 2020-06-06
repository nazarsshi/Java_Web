package com.example.dao.impl;

import com.example.dao.UserTaskDao;
import com.example.model.UserTask;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserTaskDaoImpl implements UserTaskDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public UserTask update(UserTask userTask) {
        entityManager.merge(userTask);
        return userTask;
    }

    @Transactional
    @Override
    public void delete(UserTask userTask) {
        entityManager.remove(userTask);
    }

    @Transactional
    @Override
    public UserTask save(UserTask userTask) {
        entityManager.persist(userTask);
        return userTask;
    }

    @Override
    public List<UserTask> getAll() {
        return entityManager
                .createQuery("SELECT u FROM UserTask u", UserTask.class)
                .getResultList();
    }

    @Override
    public UserTask getByUserAndTask(int userId, int taskId) {
        return entityManager
                .createQuery("SELECT u FROM UserTask u WHERE u.user.id = :userId AND u.task.id = :taskId", UserTask.class)
                .setParameter("userId", userId)
                .setParameter("taskId", taskId)
                .getSingleResult();
    }

}