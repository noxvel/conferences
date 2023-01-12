package com.nextvoyager.conferences.service;

import com.nextvoyager.conferences.model.dao.user.UserDAO;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@Disabled
public class UserServiceTest {

    @Mock
    UserDAO dao;

    private User testUser = new User();
    List<User> testList = new ArrayList<>();

    @InjectMocks
    UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        testUser.setId(1);
        testUser.setEmail("user@mail.com");
        testUser.setFirstName("Ivan");
        testUser.setLastName("Garmata");
        testUser.setPassword("123");
        testUser.setRole(User.Role.ORDINARY_USER);
        testList.add(testUser);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAll() {
        Mockito.when(dao.list()).thenReturn(testList);
        assertEquals(testList, userService.list());
    }

    @Test
    public void find() {
        Mockito.when(dao.find("user@mail.com","123")).thenReturn(testUser);
        assertEquals(testUser, userService.find("user@mail.com","123"));
    }

    @Test
    public void save() {
        userService.create(testUser);
        Mockito.verify(dao, Mockito.times(1)).create(testUser);
    }

    @Test
    public void update() {
        userService.update(testUser);
        Mockito.verify(dao, Mockito.times(1)).update(testUser);
    }

    @Test
    public void remove() {
        dao.delete(testUser);
        Mockito.verify(dao, Mockito.times(1)).delete(testUser);
    }

//    @Test
//    void userCreateTest() {
//
//        // Create user.
//        User user = new User();
//        user.setEmail("foo@bar.com");
//        user.setPassword("password");
//        user.setRole(User.Role.SPEAKER);
//        userDAO.create(user);
//        System.out.println("User successfully created: " + user);
//
//        // Create another user.
//        User anotherUser = new User();
//        anotherUser.setEmail("bar@foo.com");
//        anotherUser.setPassword("anotherPassword");
//        anotherUser.setFirstName("Bar");
//        anotherUser.setLastName("Foo");
//        anotherUser.setRole(User.Role.USER);
//        userDAO.create(anotherUser);
//        System.out.println("Another user successfully created: " + anotherUser);
//
//        // Update user.
//        user.setFirstName("Foo");
//        user.setLastName("Bar");
//        userDAO.update(user);
//        System.out.println("User successfully updated: " + user);
//
//        // Update user.
//        user.setFirstName("Foo");
//        user.setLastName("Bar");
//        userDAO.update(user);
//        System.out.println("User successfully updated: " + user);
//
//        // List all users.
//        List<User> users = userDAO.list();
//        System.out.println("List of users successfully queried: " + users);
//        System.out.println("Thus, amount of users in database is: " + users.size());
//
//        // Delete user.
//        userDAO.delete(user);
//        System.out.println("User successfully deleted: " + user);
//
//        // Check if email exists.
//        boolean exist = userDAO.existEmail("foo@bar.com");
//        System.out.println("This email should not exist anymore, so this should print false: " + exist);
//
//        // Change password.
//        anotherUser.setPassword("newAnotherPassword");
//        userDAO.changePassword(anotherUser);
//        System.out.println("Another user's password successfully changed: " + anotherUser);
//
//        // Get another user by email and password.
//        User foundAnotherUser = userDAO.find("bar@foo.com", "newAnotherPassword");
//        System.out.println("Another user successfully queried with new password: " + foundAnotherUser);
//
//        // Delete another user.
//        userDAO.delete(foundAnotherUser);
//        System.out.println("Another user successfully deleted: " + foundAnotherUser);
//
//        // List all users again.
//        users = userDAO.list();
//        System.out.println("List of users successfully queried: " + users);
//        System.out.println("Thus, amount of users in database is: " + users.size());
//
//        resp.getWriter().println("Hi new User!!!");
//    }

}
