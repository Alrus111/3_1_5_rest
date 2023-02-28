package com.example.demo.service;

import com.example.demo.model.User;

import java.util.List;

public interface UserService {
    void saveUser(User user);

    List<User> getAllUsers();

    User getUserById(Integer id);

    User getUserByUsername(String username);

    boolean updateUser(User user);

    void removeById(Integer id);
}
