package com.nextvoyager.conferences.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

/**
 * User entity class
 *
 * @author Stanislav Bozhevskyi
 */
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

    /**
     * The user ID is unique for each User. So this should compare User by ID only.
     */
    @Override
    public boolean equals(Object other) {
        return (other instanceof User) && (id != null)
                ? id.equals(((User) other).id)
                : (other == this);
    }

    /**
     * The user ID is unique for each User. So User with same ID should return same hashcode.
     */
    @Override
    public int hashCode() {
        return (id != null)
                ? (this.getClass().hashCode() + id.hashCode())
                : super.hashCode();
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
        ORDINARY_USER(3,"Ordinary user");

        private static final long serialVersionUID = 1L;

        private final Integer id;
        private final String name;

        Role(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

    }
}
