package com.nextvoyager.conferences.controller;

import com.nextvoyager.conferences.model.dao.event.EventDAO;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.EventService;
import com.nextvoyager.conferences.service.impl.EventServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/home")
public class HomePageController extends HttpServlet {

    EventService eventService = EventServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageParam = req.getParameter("page");

        // Default values for list of events
        int page = 1;
        int limit = 6;

        if (pageParam != null) {
            page = Integer.parseInt(pageParam);
        }

//        List<Event> events = eventDAO.list(orderType);
        EventDAO.ListWithCountResult countAndList;


        HttpSession currentSession = req.getSession();

        EventDAO.SortType eventListSortType = Optional.ofNullable((EventDAO.SortType) currentSession
                        .getAttribute("eventListSortType")).orElse(EventDAO.SortType.Date);

        EventDAO.SortDirection eventListSortDirection = Optional.ofNullable((EventDAO.SortDirection) currentSession
                        .getAttribute("eventListSortDirection")).orElse(EventDAO.SortDirection.Ascending);

        Boolean showSpeakerEventParticipated = Optional.ofNullable((Boolean) currentSession
                        .getAttribute("filterBySpeakerParticipated")).orElse(Boolean.FALSE);

//        Boolean showSpeakerEventParticipated = (Boolean) currentSession.getAttribute("filterBySpeakerParticipated");
        User user = (User) currentSession.getAttribute("user");
        if (showSpeakerEventParticipated && user != null) {
            countAndList = eventService.listWithPaginationSpeakerParticipated(page, limit, eventListSortType, eventListSortDirection, user);
        } else {
            countAndList = eventService.listWithPagination(page, limit, eventListSortType, eventListSortDirection);
        }
        //------------------------------------------------------------------------------------------------

        int numOfPages = (int)Math.ceil((double)countAndList.getCount()/limit);

        req.setAttribute("events", countAndList.getList());
        req.setAttribute("page", page);
        req.setAttribute("sortType", eventListSortType);
        req.setAttribute("sortDirection", eventListSortDirection);
        req.setAttribute("showSpeakerEventParticipated", showSpeakerEventParticipated);
        req.setAttribute("numOfPages", numOfPages);
        req.getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(req,resp);

//
//        // Obtain DAOFactory.
//        DAOFactory javabase = DAOFactory.getInstance("javabase.jdbc");
//        System.out.println("DAOFactory successfully obtained: " + javabase);
//
//        // Obtain UserDAO.
//        UserDAO userDAO = javabase.getUserDAO();
//        System.out.println("UserDAO successfully obtained: " + userDAO);
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
    }

}
