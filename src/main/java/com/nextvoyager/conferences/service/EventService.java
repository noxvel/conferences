package com.nextvoyager.conferences.service;

import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.User;

public interface EventService {

    Event find(Integer eventID);

    void update(Event event);

    boolean isUserRegisterEvent(Event event, User user);

    void create(Event event);

    void registerUser(Integer eventID, User user, boolean register);
}
