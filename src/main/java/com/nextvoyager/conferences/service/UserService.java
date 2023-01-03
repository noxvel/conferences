package com.nextvoyager.conferences.service;

import com.nextvoyager.conferences.model.entity.User;

import java.util.List;

public interface UserService {
    List<User> listWithOneRole(User.Role speaker);

    List<User> list();

    void create(User user);

    User find(String emailParam, String passwordParam);

    User find(Integer id);

    boolean existEmail(String emailParam);

    void update(User user);

    void changePassword(User user);

    boolean checkPassword(User user);

}
