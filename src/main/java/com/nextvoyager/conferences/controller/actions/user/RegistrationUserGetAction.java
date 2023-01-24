package com.nextvoyager.conferences.controller.actions.user;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Go to the user registration page.
 * Path "/user/registration".
 * GET Method.
 *
 * @author Stanislav Bozhevskyi
 */
public class RegistrationUserGetAction implements ControllerAction {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        return USER_REGISTRATION;
    }

}
