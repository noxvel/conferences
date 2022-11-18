package com.nextvoyager.conferences.model;

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
    private Integer participantsCame;

    private List<Report> reports;
    private Set<User> participants;

    public Event(Integer id, String name, String place, Date beginDate, Date endDate, Integer participantsCame, List<Report> reports, Set<User> participants) {
        this.id = id;
        this.name = name;
        this.place = place;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.participantsCame = participantsCame;
        this.reports = reports;
        this.participants = participants;
    }
}
