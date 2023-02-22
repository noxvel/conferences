package com.nextvoyager.conferences.controller.actions.user;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShowMessageAction implements ControllerAction {
    private static final Logger LOG = LogManager.getLogger(ShowMessageAction.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String msgParam = req.getParameter("message");

        Message message = null;
        try {
            message = Message.valueOf(msgParam);
        } catch (IllegalArgumentException exception) {
            LOG.error("Invalid value to show user message - " + msgParam);
        }

        req.setAttribute("message", message);

        return USER_SHOW_MESSAGE;
    }

    @Getter
    public enum Message{

        ForgotPasswordSendLink("success", "forgot-password.message.send-message"),
        ResetPasswordSuccess("success", "reset-password.message.success"),
        ResetPasswordError("danger", "reset-password.message.error");

        private final String type;
        private final String msgKey;

        Message(String type, String msgKey) {
            this.type = type;
            this.msgKey = msgKey;
        }
    }
}
