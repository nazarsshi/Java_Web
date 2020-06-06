package com.example.dao;

import com.example.model.UserTask;

public interface UserTaskDao extends MainDao<UserTask> {

    UserTask update(UserTask userTask);

    void delete(UserTask userTask);

    UserTask getByUserAndTask(int userId, int taskId);

}
