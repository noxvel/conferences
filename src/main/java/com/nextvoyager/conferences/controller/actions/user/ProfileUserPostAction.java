package com.nextvoyager.conferences.controller.actions.user;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.UserService;
import com.nextvoyager.conferences.util.validation.ParameterValidator;
import com.nextvoyager.conferences.util.validation.ValidateObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import static com.nextvoyager.conferences.controller.actions.ControllerActionConstants.*;
import static com.nextvoyager.conferences.util.validation.ValidateRegExp.*;
import static com.nextvoyager.conferences.util.validation.ValidateRegExp.REGEXP_USER_ROLE;

//("/user/profile")
public class ProfileUserPostAction implements ControllerAction {

    private static final ValidateObject[] validateObjects = {
            new ValidateObject(PARAM_USER_FIRST_NAME, REGEXP_USER_NAME),
            new ValidateObject(PARAM_USER_LAST_NAME, REGEXP_USER_NAME),
//            new ValidateObject(PARAM_USER_EMAIL, REGEXP_EMAIL),
    };

    private final UserService userService;

    public ProfileUserPostAction(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        ParameterValidator.validate(req,validateObjects);

        String firstNameParam = req.getParameter(PARAM_USER_FIRST_NAME);
        String lastNameParam = req.getParameter(PARAM_USER_LAST_NAME);
        String receiveNotifications = req.getParameter(PARAM_USER_RECEIVE_NOTIFICATIONS);
//        String emailParam = req.getParameter(PARAM_USER_EMAIL);

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        user.setFirstName(firstNameParam);
        user.setLastName(lastNameParam);
        user.setReceiveNotifications(receiveNotifications != null);
//        user.setEmail(emailParam);

        userService.update(user);
        return PREFIX_PATH + HOME;

    }
}
