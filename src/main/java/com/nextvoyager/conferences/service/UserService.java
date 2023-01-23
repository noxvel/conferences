package com.nextvoyager.conferences.service;

import com.nextvoyager.conferences.model.dao.ListWithCount;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.User;

import java.util.List;

public interface UserService {

    User find(String emailParam, String passwordParam);

    User find(Integer id);
    void create(User user);
    void update(User user);

    void delete(User user);

    ListWithCount<User> list(int page, int limit);
    List<User> listWithOneRole(User.Role speaker);

    List<User> listOfEventParticipants(Event event);

    boolean existEmail(String emailParam);

    void changePassword(User user);

    boolean checkPassword(User user);

}
