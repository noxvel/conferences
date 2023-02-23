package com.nextvoyager.conferences.controller.actions.user;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.util.recaptcha.RecaptchaUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

/**
 * Login user.
 * Path "/user/login".
 * GET Method.
 *
 * @author Stanislav Bozhevskyi
 */
public class LoginUserGetAction implements ControllerAction {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        Integer loginAttempts = Optional.ofNullable((Integer) req.getSession()
                .getAttribute("loginAttempts")).orElse(0);

        if (loginAttempts > 2)
            req.setAttribute("recaptchaSiteKey", RecaptchaUtil.SITE_KEY);

        return USER_LOGIN;
    }

}
