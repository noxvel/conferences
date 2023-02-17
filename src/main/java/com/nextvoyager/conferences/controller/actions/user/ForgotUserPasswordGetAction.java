package com.nextvoyager.conferences.controller.actions.user;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ForgotUserPasswordGetAction implements ControllerAction {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        return USER_FORGOT_PASSWORD;
    }
}
