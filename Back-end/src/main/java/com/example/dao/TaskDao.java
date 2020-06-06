package com.example.dao;

import com.example.model.Task;

public interface TaskDao extends MainDao<Task> {

    Task getById(int id);

    Task update(Task task);

    void delete(Task task);

}
