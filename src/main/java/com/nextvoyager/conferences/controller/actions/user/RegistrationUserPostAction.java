package com.nextvoyager.conferences.controller.actions.user;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.UserService;
import com.nextvoyager.conferences.util.validation.ParameterValidator;
import com.nextvoyager.conferences.util.validation.ValidateObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.nextvoyager.conferences.controller.actions.ControllerActionConstants.*;
import static com.nextvoyager.conferences.util.validation.ValidateRegExp.*;

/**
 * Registration for the new user.
 * Path "/user/registration".
 * POST Method.
 *
 * @author Stanislav Bozhevskyi
 */
public class RegistrationUserPostAction implements ControllerAction {

    private static final ValidateObject[] validateObjects = {
            new ValidateObject(PARAM_USER_FIRST_NAME, REGEXP_USER_NAME),
            new ValidateObject(PARAM_USER_LAST_NAME, REGEXP_USER_NAME),
            new ValidateObject(PARAM_USER_EMAIL, REGEXP_EMAIL),
            new ValidateObject(PARAM_USER_PASSWORD, REGEXP_PASSWORD),
            new ValidateObject(PARAM_USER_ROLE, REGEXP_USER_ROLE)
    };

    private final UserService userService;

    public RegistrationUserPostAction(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        ParameterValidator.validate(req,validateObjects);

        String firstNameParam = req.getParameter(PARAM_USER_FIRST_NAME);
        String lastNameParam = req.getParameter(PARAM_USER_LAST_NAME);
        String emailParam = req.getParameter(PARAM_USER_EMAIL);
        String passwordParam = req.getParameter(PARAM_USER_PASSWORD);
        User.Role userRoleParam = User.Role.valueOf(req.getParameter(PARAM_USER_ROLE));

        boolean exist = userService.existEmail(emailParam);

        if (exist) {
            req.setAttribute("message", "The email you entered already exists. Please enter a different email.");
            return USER_REGISTRATION;
        } else {
            User user = new User();
            user.setFirstName(firstNameParam);
            user.setLastName(lastNameParam);
            user.setEmail(emailParam);
            user.setPassword(passwordParam);
            user.setRole(userRoleParam);

            userService.create(user);

            req.getSession().setAttribute("user", user);
            req.getSession().setAttribute("userRole", user.getRole());
            return PREFIX_PATH + HOME;
        }
    }

}
