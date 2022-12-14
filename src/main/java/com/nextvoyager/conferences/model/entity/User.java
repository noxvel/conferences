package com.nextvoyager.conferences.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String email;
    private String password;
    private User.Role role;
    private String firstName;
    private String lastName;
    private Boolean receiveNotifications;

    private Set<Event> events;

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }

    public User(Integer id, Role role) {
        this.id = id;
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    @Getter
    @ToString
    public enum Role implements Serializable{

        MODERATOR(1,"Moderator"),
        SPEAKER(2,"Speaker"),
        ORDINARY_USER(3,"OrdinaryUser");

        private static final long serialVersionUID = 1L;

        private final Integer id;
        private final String name;

        Role(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

    }
}
