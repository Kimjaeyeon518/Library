package com.example.service;

import com.example.domain.User;

import java.util.List;

public interface UserService {

    User save(User user);
    User findOne(Long id);
    List<User> findAll();
    User update(User user);
    boolean delete(Long id);
}
