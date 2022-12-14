package com.nextvoyager.conferences.service.impl;

import com.nextvoyager.conferences.model.dao.DAOFactory;
import com.nextvoyager.conferences.model.dao.event.EventDAO;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.EventService;

public class EventServiceImpl implements EventService {

    DAOFactory javabase = DAOFactory.getInstance();
    EventDAO eventDAO = javabase.getEventDAO();

    public static EventServiceImpl getInstance() {
        return new EventServiceImpl();
    }

    public Event find(Integer id) {
        return eventDAO.find(id);
    }

    @Override
    public void update(Event event) {
        eventDAO.update(event);
    }

    @Override
    public void create(Event event) {
        eventDAO.create(event);
    }

    @Override
    public boolean isUserRegisterEvent(Event event, User user) {
        return eventDAO.isUserRegisterEvent(event, user);
    }

    @Override
    public void registerUser(Integer eventID, User user, boolean register) {
        eventDAO.registerUser(eventID,user,register);
    }
}
