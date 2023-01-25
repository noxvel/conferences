package com.nextvoyager.conferences.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Report entity class
 *
 * @author Stanislav Bozhevskyi
 */
@Getter
@Setter
public class Report {

    private Integer id;
    private String topic;
    private User speaker;
    private Report.Status status;
    private Event event;
    private String description;

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", speaker=" + speaker +
                ", status=" + status +
                ", event=" + event +
                '}';
    }

    /**
     * The report ID is unique for each Report. So this should compare Report by ID only.
     */
    @Override
    public boolean equals(Object other) {
        return (other instanceof Report) && (id != null)
                ? id.equals(((Report) other).id)
                : (other == this);
    }

    /**
     * The report ID is unique for each Report. So Report with same ID should return same hashcode.
     */
    @Override
    public int hashCode() {
        return (id != null)
                ? (this.getClass().hashCode() + id.hashCode())
                : super.hashCode();
    }

    @Getter
    @ToString
    public enum Status{

        FREE(1,"Free"),
        OFFERED_BY_SPEAKER(2,"Offered by speaker"),
        PROPOSE_TO_SPEAKER(3,"Propose to speaker"),
        SUGGESTED_SPEAKER(4,"Suggested speaker"),
        CONFIRMED(5,"Confirmed"),
        CANCELED(6,"Canceled");

        private final Integer id;
        private final String name;

        Status(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

    }
}
