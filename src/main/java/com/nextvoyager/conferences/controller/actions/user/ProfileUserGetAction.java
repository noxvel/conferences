package com.nextvoyager.conferences.controller.actions.user;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Go to profile user page.
 * Path "/user/profile".
 * GET Method.
 *
 * @author Stanislav Bozhevskyi
 */
public class ProfileUserGetAction implements ControllerAction {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {

        HttpSession session = req.getSession();

        if (session != null && session.getAttribute("user") != null) {
            return USER_PROFILE;
        } else {
            return PREFIX_PATH + USER_LOGIN;
        }

    }

}
