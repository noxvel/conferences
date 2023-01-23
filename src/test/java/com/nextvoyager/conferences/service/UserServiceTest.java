package com.nextvoyager.conferences.service;

import com.nextvoyager.conferences.model.dao.ListWithCount;
import com.nextvoyager.conferences.model.dao.user.UserDAO;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserDAO dao;

    private final User testUser = new User();
    List<User> testList = new ArrayList<>();
    ListWithCount<User> testListWithCount = new ListWithCount<>();

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
        testListWithCount.setCount(1);
        testListWithCount.setList(testList);
    }

    @Test
    public void find() {
        Mockito.when(dao.find("user@mail.com","123")).thenReturn(testUser);
        assertEquals(testUser, userService.find("user@mail.com","123"));

        Mockito.when(dao.find(1)).thenReturn(testUser);
        assertEquals(testUser, userService.find(1));
    }

    @Test
    public void create() {
        userService.create(testUser);
        verify(dao, Mockito.times(1)).create(testUser);
    }

    @Test
    public void update() {
        userService.update(testUser);
        verify(dao, Mockito.times(1)).update(testUser);
    }

    @Test
    public void delete() {
        userService.delete(testUser);
        verify(dao, Mockito.times(1)).delete(testUser);
    }

    @Test
    public void list() {
        Mockito.when(dao.list(anyInt(), anyInt())).thenReturn(testListWithCount);
        userService.list(1, 6);
        verify(dao).list(1, 6);
    }

    @Test
    public void listWithOneRole() {
        Mockito.when(dao.listWithOneRole(any(User.Role.class))).thenReturn(testList);
        userService.listWithOneRole(User.Role.SPEAKER);
        verify(dao).listWithOneRole(any(User.Role.class));
    }

    @Test
    public void listOfEventParticipants() {
        Mockito.when(dao.listOfEventParticipants(anyInt())).thenReturn(testList);
        userService.listOfEventParticipants(new Event(1));
        verify(dao).listOfEventParticipants(1);
    }

    @Test
    public void checkPassword() {
        Mockito.when(dao.checkPassword(testUser)).thenReturn(false);
        assertFalse(userService.checkPassword(testUser));
    }

    @Test
    public void changePassword() {
        dao.changePassword(testUser);
        verify(dao,Mockito.times(1)).changePassword(testUser);
    }

    @Test
    public void existEmail() {
        Mockito.when(dao.existEmail(anyString())).thenReturn(true);
        assertTrue(userService.existEmail("123"));
    }

}
