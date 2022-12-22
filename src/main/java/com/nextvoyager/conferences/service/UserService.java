package com.nextvoyager.conferences.service;

import com.nextvoyager.conferences.model.entity.User;

import java.util.List;

public interface UserService {
    List<User> listWithOneRole(User.Role speaker);

    void create(User user);

    User find(String emailParam, String passwordParam);

    boolean existEmail(String emailParam);

    void update(User user);
}
