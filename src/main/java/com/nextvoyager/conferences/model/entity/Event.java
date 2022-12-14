package com.nextvoyager.conferences.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class Event {

    private Integer id;
    private String name;
    private String place;
    private Date beginDate;
    private Date endDate;
    private Integer reportsCount;
    private Integer participantsCount;
    private Integer participantsCame;
    private String description;

    private List<Report> reports;
    private Set<User> participants;

    public Event() {
    }

    public Event(Integer id) {
        this.id = id;
    }

    public Event(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
