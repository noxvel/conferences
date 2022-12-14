package com.nextvoyager.conferences.service.impl;

import com.nextvoyager.conferences.model.dao.DAOFactory;
import com.nextvoyager.conferences.model.dao.user.UserDAO;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    DAOFactory javabase = DAOFactory.getInstance();
    UserDAO userDAO = javabase.getUserDAO();

    public static UserServiceImpl getInstance() {
        return new UserServiceImpl();
    }

    @Override
    public void create(User user) {
        userDAO.create(user);
    }

    @Override
    public User find(String emailParam, String passwordParam) {
        return userDAO.find(emailParam, passwordParam);
    }

    @Override
    public boolean existEmail(String emailParam) {
        return userDAO.existEmail(emailParam);
    }

    @Override
    public List<User> listWithOneRole(User.Role role) {
        return userDAO.listWithOneRole(role);
    }

}
