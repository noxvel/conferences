package com.nextvoyager.conferences.controller.actions.user;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.UserService;
import com.nextvoyager.conferences.util.validation.ParameterValidator;
import com.nextvoyager.conferences.util.validation.ValidateObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.nextvoyager.conferences.controller.actions.ControllerActionConstants.PARAM_USER_TOKEN;

public class ResetUserPasswordGetAction implements ControllerAction {
    private static final ValidateObject[] validateObjects = {
            new ValidateObject(PARAM_USER_TOKEN),
    };

    private final UserService userService;

    public ResetUserPasswordGetAction(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ParameterValidator.validate(req,validateObjects);

        String tokenParam = req.getParameter(PARAM_USER_TOKEN);

        User.PasswordResetToken token = userService.validatePasswordResetToken(tokenParam);

        if(token.isValid()) {
            req.setAttribute("token", tokenParam);
            return USER_RESET_PASSWORD;
        } else {
            return PREFIX_PATH + USER_SHOW_MESSAGE + "?message=" + ShowMessageAction.Message.ResetPasswordError;
        }
    }
}
