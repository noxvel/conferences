package com.nextvoyager.conferences.controller.actions.user;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Change user password.
 * Path "/user/change-password".
 * GET Method.
 *
 * @author Stanislav Bozhevskyi
 */
public class ChangeUserPasswordGetAction implements ControllerAction {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        return USER_CHANGE_PASSWORD;
    }

}
