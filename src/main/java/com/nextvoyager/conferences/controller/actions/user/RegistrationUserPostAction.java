package com.nextvoyager.conferences.controller.actions.user;

import com.nextvoyager.conferences.AppContext;
import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//@WebServlet("/user/registration")
public class RegistrationUserPostAction implements ControllerAction {

    private final UserService userService = AppContext.getInstance().getUserService();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstNameParam = req.getParameter("firstName");
        String lastNameParam = req.getParameter("lastName");
        String emailParam = req.getParameter("email");
        String passwordParam = req.getParameter("password");
        String userRoleParam = req.getParameter("userRole");

        validate(firstNameParam,lastNameParam,emailParam,passwordParam);

        User user = new User();
        user.setFirstName(firstNameParam);
        user.setLastName(lastNameParam);
        user.setEmail(emailParam);
        user.setPassword(passwordParam);
        user.setRole(User.Role.valueOf(userRoleParam));

        boolean exist = userService.existEmail(emailParam);

        if (exist) {
            req.setAttribute("message", "The email you entered already exists. Please enter a different email.");
            return USER_REGISTRATION;
        } else {
            userService.create(user);

            req.getSession().setAttribute("user", user);
            req.getSession().setAttribute("userRole", user.getRole());
            return req.getContextPath() + "/pages/home";
        }
    }

    private void validate(String firstName, String lastName, String email, String password) throws ServletException{

        List<String> errorMessages = new ArrayList<>();

        // Get and validate first name
        if (firstName == null || firstName.trim().isEmpty()) {
            errorMessages.add("Please enter first name");
        } else if (!firstName.matches("[A-Za-zА-Яа-яёЁЇїІіЄєҐґ\\s'-]{1,60}")) {
            errorMessages.add("Please enter alphabetical characters from 1 to 60 for first name.");
        }

        // Get and validate last name
        if (lastName == null || lastName.trim().isEmpty()) {
            errorMessages.add("Please enter last name");
        } else if (!lastName.matches("[A-Za-zА-Яа-яёЁЇїІіЄєҐґ\\s'-]{1,60}")) {
            errorMessages.add("Please enter alphabetical characters from 1 to 60 for last name.");
        }

        // Get and validate email
        if (email == null || email.trim().isEmpty()) {
            errorMessages.add("Please enter email");
        } else if (!email.matches("^[\\w\\-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            errorMessages.add("Please enter correct email address");
        }

        // Get and validate password.
        if (password == null || password.trim().isEmpty()) {
            errorMessages.add("Please enter password");
        } else if (!password.matches(".{3,60}")) {
            errorMessages.add("Please enter from 3 to 60 symbols");
        }

        if (!errorMessages.isEmpty()) {
            throw new ServletException("Invalid input values: " + String.join(",", errorMessages));
        }

    }
}
