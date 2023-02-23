package com.nextvoyager.conferences.controller.actions.user;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.UserService;
import com.nextvoyager.conferences.util.validation.ParameterValidator;
import com.nextvoyager.conferences.util.validation.ValidateObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.nextvoyager.conferences.controller.actions.ControllerActionConstants.PARAM_USER_NEW_PASSWORD;
import static com.nextvoyager.conferences.controller.actions.ControllerActionConstants.PARAM_USER_TOKEN;
import static com.nextvoyager.conferences.util.validation.ValidateRegExp.REGEXP_PASSWORD;

public class ResetUserPasswordPostAction implements ControllerAction {
    private static final ValidateObject[] validateObjects = {
            new ValidateObject(PARAM_USER_TOKEN),
            new ValidateObject(PARAM_USER_NEW_PASSWORD, REGEXP_PASSWORD),
    };

    private final UserService userService;

    public ResetUserPasswordPostAction(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ParameterValidator.validate(req,validateObjects);

        String tokenParam = req.getParameter(PARAM_USER_TOKEN);
        String newPasswordParam = req.getParameter(PARAM_USER_NEW_PASSWORD);

        User.PasswordResetToken token = userService.validatePasswordResetToken(tokenParam);

        ShowMessageAction.Message message;
        if (token.isValid()) {

            User user = token.getUser();

            user.setPassword(newPasswordParam);
            userService.changePassword(user);

            message = ShowMessageAction.Message.ResetPasswordSuccess;

        } else {
            message = ShowMessageAction.Message.ResetPasswordError;
        }

        return PREFIX_PATH + USER_SHOW_MESSAGE + "?message=" + message;
    }
}
