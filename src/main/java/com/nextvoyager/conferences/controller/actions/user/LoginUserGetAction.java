package com.nextvoyager.conferences.controller.actions.user;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Login user.
 * Path "/user/login".
 * GET Method.
 *
 * @author Stanislav Bozhevskyi
 */
public class LoginUserGetAction implements ControllerAction {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        return USER_LOGIN;
    }

}
