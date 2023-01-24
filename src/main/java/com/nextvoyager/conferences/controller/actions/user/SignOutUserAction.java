package com.nextvoyager.conferences.controller.actions.user;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Sign out current user from the app.
 * Path "/user/sign-out".
 *
 * @author Stanislav Bozhevskyi
 */
public class SignOutUserAction implements ControllerAction {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {

        HttpSession httpSession = req.getSession();
        if (httpSession != null) {
            req.getSession().invalidate();
        }

        return PREFIX_PATH + HOME;
    }
}
