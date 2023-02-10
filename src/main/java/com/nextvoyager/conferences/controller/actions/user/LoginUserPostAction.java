package com.nextvoyager.conferences.controller.actions.user;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.UserService;
import com.nextvoyager.conferences.util.recaptcha.RecaptchaUtil;
import com.nextvoyager.conferences.util.validation.ParameterValidator;
import com.nextvoyager.conferences.util.validation.ValidateObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.nextvoyager.conferences.controller.actions.ControllerActionConstants.*;
import static com.nextvoyager.conferences.util.validation.ValidateRegExp.*;

/**
 * Login user.
 * Path "/user/login".
 * POST Method.
 *
 * @author Stanislav Bozhevskyi
 */
public class LoginUserPostAction implements ControllerAction {

    private static final ValidateObject[] validateObjects = {
            new ValidateObject(PARAM_USER_EMAIL, REGEXP_EMAIL),
    };

    private final UserService userService;
    private static final Logger LOG = LogManager.getLogger(LoginUserPostAction.class);

    public LoginUserPostAction(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        ParameterValidator.validate(req,validateObjects);

        HttpSession currentSession = req.getSession();

        Integer loginAttempts = Optional.ofNullable((Integer) currentSession
                .getAttribute("loginAttempts")).orElse(0);

        currentSession.setAttribute("loginAttempts", ++loginAttempts);
        LOG.debug("Login attempt - {}", loginAttempts);

        // If there are too many attempts, then use reCAPTCHA check
        if (loginAttempts > 3) {
            String gRecaptchaParam = req.getParameter(PARAM_USER_RECAPTCHA);
            boolean recaptchaVerify = RecaptchaUtil.verify(gRecaptchaParam);
            if (!recaptchaVerify) {
                req.setAttribute("recaptchaSiteKey", RecaptchaUtil.SITE_KEY);
                req.setAttribute("message", "login.error.missed-captcha");
                LOG.warn("Missed reCAPTCHA by user login");
                return USER_LOGIN;
            }
        }

        String emailParam = req.getParameter(PARAM_USER_EMAIL);
        String passwordParam = req.getParameter(PARAM_USER_PASSWORD);

        // Obtain DAOFactory.
        User user = userService.find(emailParam,passwordParam);

        if (user == null) {
            if (loginAttempts > 2)
                req.setAttribute("recaptchaSiteKey", RecaptchaUtil.SITE_KEY);
            req.setAttribute("message", "login.error.email-password-unknown");
            LOG.warn("Bad login for user with email: {}", emailParam);
            return USER_LOGIN;
        } else {
            req.getSession().setAttribute("user", user);
            req.getSession().setAttribute("userRole", user.getRole());
            LOG.info("user is login - " + user.getEmail());
            return PREFIX_PATH + HOME;
        }
    }

}
