package com.nextvoyager.conferences.controller.actions.user;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//("/user/login")
public class LoginUserPostAction implements ControllerAction {

    private final UserService userService;
    private static final Logger logger = LogManager.getLogger(LoginUserPostAction.class);

    public LoginUserPostAction(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String emailParam = req.getParameter("email");
        String passwordParam = req.getParameter("password");

        validate(emailParam, passwordParam);

        // Obtain DAOFactory.
        User user = userService.find(emailParam,passwordParam);

        if (user == null) {
            req.setAttribute("message", "Unknown username/password. Please retry");
            logger.warn("Bad login for user with email: " + emailParam);
            return USER_LOGIN;
        } else {
            req.getSession().setAttribute("user", user);
            req.getSession().setAttribute("userRole", user.getRole());
            logger.info("user is login - " + user.getEmail());
            return req.getContextPath() + "/pages/home";
        }
    }

    private void validate(String email, String password) throws ServletException{

        List<String> errorMessages = new ArrayList<>();

        // Get and validate email
        if (email == null || email.trim().isEmpty()) {
            errorMessages.add("Please enter email");
        } else if (!email.matches("^[\\w\\-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            errorMessages.add("Please enter correct email address");
        }

        // Get and validate password.
        if (password == null || password.trim().isEmpty()) {
            errorMessages.add("Please enter password");
        }

        if (!errorMessages.isEmpty()) {
            throw new ServletException("Invalid input values: " + String.join(",", errorMessages));
        }

    }
}
