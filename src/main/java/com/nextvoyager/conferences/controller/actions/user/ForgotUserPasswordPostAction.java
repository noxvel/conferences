package com.nextvoyager.conferences.controller.actions.user;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.UserService;
import com.nextvoyager.conferences.util.emailcreator.EmailCreator;
import com.nextvoyager.conferences.util.validation.ParameterValidator;
import com.nextvoyager.conferences.util.validation.ValidateObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;

import static com.nextvoyager.conferences.controller.actions.ControllerActionConstants.PARAM_USER_EMAIL;
import static com.nextvoyager.conferences.controller.actions.ControllerActionConstants.PARAM_USER_TOKEN;
import static com.nextvoyager.conferences.util.validation.ValidateRegExp.REGEXP_EMAIL;

public class ForgotUserPasswordPostAction implements ControllerAction {
    private static final ValidateObject[] validateObjects = {
            new ValidateObject(PARAM_USER_EMAIL, REGEXP_EMAIL),
    };

    private final UserService userService;

    public ForgotUserPasswordPostAction(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ParameterValidator.validate(req,validateObjects);

        String emailParam = req.getParameter(PARAM_USER_EMAIL);

        // Lookup user in database by e-mail
        User user = userService.findUserByEmail(emailParam);

        if (user != null) {

            // Generate random 36-character string token for reset password
            String token = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user, token);

            StringBuilder tokenUrl = new StringBuilder();
            tokenUrl.append(req.getScheme())
                    .append("://")
                    .append(req.getServerName())
                    .append(":")
                    .append(req.getServerPort())
                    .append(req.getContextPath())
                    .append(PREFIX_PATH)
                    .append(USER_RESET_PASSWORD)
                    .append("?")
                    .append(PARAM_USER_TOKEN)
                    .append("=")
                    .append(token);

            // Email message
            EmailCreator.send(user.getEmail(), "Password Reset Request",
                    "To reset your password, click the link below:\n" + tokenUrl);
        }

        // Show message to view
        return PREFIX_PATH + USER_SHOW_MESSAGE + "?message=" + ShowMessageAction.Message.ForgotPasswordSendLink;
    }
}
