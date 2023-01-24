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
import static com.nextvoyager.conferences.util.validation.ValidateRegExp.REGEXP_PASSWORD;

/**
 * Change user password.
 * Path "/user/change-password".
 * POST Method.
 *
 * @author Stanislav Bozhevskyi
 */
public class ChangeUserPasswordPostAction implements ControllerAction {

    private static final ValidateObject[] validateObjects = {
            new ValidateObject(PARAM_USER_NEW_PASSWORD, REGEXP_PASSWORD),
            new ValidateObject(PARAM_USER_CURRENT_PASSWORD, REGEXP_PASSWORD)
    };

    private final UserService userService;

    public ChangeUserPasswordPostAction(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        ParameterValidator.validate(req,validateObjects);

        String newPasswordParam = req.getParameter(PARAM_USER_NEW_PASSWORD);
        String currentPasswordParam = req.getParameter(PARAM_USER_CURRENT_PASSWORD);

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        user.setPassword(currentPasswordParam);

        if (!userService.checkPassword(user)) {
            req.setAttribute("message", "Please type your current password.");
            return USER_CHANGE_PASSWORD;
        } else {
            user.setPassword(newPasswordParam);
            userService.changePassword(user);
            return PREFIX_PATH + USER_PROFILE;
        }
    }

}
