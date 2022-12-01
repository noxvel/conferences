package com.nextvoyager.conferences.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

@Data
public class Report {

    private Integer id;
    private String topic;
    private User speaker;
    private Report.Status status;
    private Event event;
    private String description;

    @Getter
    @ToString
    public enum Status{

        FREE(1,"Free"),
        OFFERED_BY_SPEAKER(2,"Offered by speaker"),
        PROPOSE_TO_SPEAKER(3,"Propose to speaker"),
        SUGGESTED_SPEAKER(4,"Suggested speaker"),
        CONFIRMED(5,"Confirmed");

        private final Integer id;
        private final String name;

        Status(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

    }
}
