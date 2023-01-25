package com.nextvoyager.conferences.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Event entity class
 *
 * @author Stanislav Bozhevskyi
 */
@Getter
@Setter
public class Event {

    private Integer id;
    private String name;
    private String place;
    private LocalDateTime beginDate;
    private LocalDateTime endDate;
    private Integer reportsCount;
    private Integer participantsCount;
    private Integer participantsCame;
    private String description;

    private List<Report> reports;
    private List<User> participants;

    public Event() {
    }

    public Event(Integer id) {
        this.id = id;
    }

    public Event(int id, String name) {
        this.id = id;
        this.name = name;
    }
    /**
     * The event ID is unique for each Event. So this should compare Event by ID only.
     */
    @Override
    public boolean equals(Object other) {
        return (other instanceof Event) && (id != null)
                ? id.equals(((Event) other).id)
                : (other == this);
    }

    /**
     * The event ID is unique for each Event. So Event with same ID should return same hashcode.
     */
    @Override
    public int hashCode() {
        return (id != null)
                ? (this.getClass().hashCode() + id.hashCode())
                : super.hashCode();
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static class EventBuilder{

        private Integer id;
        private String name;
        private String place;
        private LocalDateTime beginDate;
        private LocalDateTime endDate;
        private Integer reportsCount;
        private Integer participantsCount;
        private Integer participantsCame;
        private String description;

        public EventBuilder(){}

        public EventBuilder setId(Integer id) {
            this.id = id;
            return this;
        }

        public EventBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public EventBuilder setPlace(String place) {
            this.place = place;
            return this;
        }

        public EventBuilder setBeginDate(LocalDateTime beginDate) {
            this.beginDate = beginDate;
            return this;
        }

        public EventBuilder setEndDate(LocalDateTime endDate) {
            this.endDate = endDate;
            return this;
        }

        public EventBuilder setReportsCount(Integer reportsCount) {
            this.reportsCount = reportsCount;
            return this;
        }

        public EventBuilder setParticipantsCount(Integer participantsCount) {
            this.participantsCount = participantsCount;
            return this;
        }

        public EventBuilder setParticipantsCame(Integer participantsCame) {
            this.participantsCame = participantsCame;
            return this;
        }

        public EventBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Event build(){
            Event event = new Event();
            event.setId(id);
            event.setName(name);
            event.setPlace(place);
            event.setBeginDate(beginDate);
            event.setEndDate(endDate);
            event.setDescription(description);
            event.setReportsCount(reportsCount);
            event.setParticipantsCount(participantsCount);
            event.setParticipantsCame(participantsCame);
            return event;
        }
    }
}
