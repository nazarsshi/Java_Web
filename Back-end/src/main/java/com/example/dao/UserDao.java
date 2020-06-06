package com.example.dao;

import com.example.model.User;

public interface UserDao extends MainDao<User> {

    User getById(int id);

    User update(User user);

    void delete(User user);

    User getByEmail(String email);

    User getByApiKey(String apiKey);

}
