package com.nextvoyager.conferences.service.impl;

import com.nextvoyager.conferences.model.dao.DAOFactory;
import com.nextvoyager.conferences.model.dao.user.UserDAO;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    public static UserServiceImpl getInstance(UserDAO userDAO) {
        return new UserServiceImpl(userDAO);
    }

    private UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void create(User user) {
        userDAO.create(user);
    }

    @Override
    public User find(String emailParam, String passwordParam) {
        return userDAO.find(emailParam, passwordParam);
    }

    public User find(Integer userID) {
        return userDAO.find(userID);
    }

    @Override
    public boolean checkPassword(User user) {
        return userDAO.checkPassword(user);
    }

    @Override
    public void changePassword(User user) {
        userDAO.changePassword(user);
    }

    @Override
    public void update(User user) {
        userDAO.update(user);
    }

    @Override
    public boolean existEmail(String emailParam) {
        return userDAO.existEmail(emailParam);
    }

    @Override
    public List<User> listWithOneRole(User.Role role) {
        return userDAO.listWithOneRole(role);
    }

    @Override
    public List<User> list() {
        return userDAO.list();
    }

}
