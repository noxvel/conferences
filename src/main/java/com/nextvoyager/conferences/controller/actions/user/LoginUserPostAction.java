package com.nextvoyager.conferences.controller.actions.user;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.UserService;
import com.nextvoyager.conferences.util.validation.ParameterValidator;
import com.nextvoyager.conferences.util.validation.ValidateObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


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
    private static final Logger logger = LogManager.getLogger(LoginUserPostAction.class);

    public LoginUserPostAction(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        ParameterValidator.validate(req,validateObjects);

        String emailParam = req.getParameter(PARAM_USER_EMAIL);
        String passwordParam = req.getParameter(PARAM_USER_PASSWORD);

        // Obtain DAOFactory.
        User user = userService.find(emailParam,passwordParam);

        if (user == null) {
            req.setAttribute("message", "Unknown username/password. Please retry");
            logger.warn("Bad login for user with email: " + emailParam);
            return USER_LOGIN;
        } else {
            req.getSession().setAttribute("user", user);
            req.getSession().setAttribute("userRole", user.getRole());
            logger.info("user is login - " + user.getEmail());
            return PREFIX_PATH + HOME;
        }
    }

}
