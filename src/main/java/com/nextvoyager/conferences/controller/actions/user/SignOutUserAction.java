package com.nextvoyager.conferences.controller.actions.user;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//("/user/sign-out")
public class SignOutUserAction implements ControllerAction {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {

        HttpSession httpSession = req.getSession(false);
        if (httpSession != null) {
            req.getSession().invalidate();
        }

        return PREFIX_PATH + HOME;
    }
}
