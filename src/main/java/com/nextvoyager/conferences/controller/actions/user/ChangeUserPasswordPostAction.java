package com.nextvoyager.conferences.controller.actions.user;

import com.nextvoyager.conferences.AppContext;
import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

//@WebServlet("/user/change-password")
public class ChangeUserPasswordPostAction implements ControllerAction {

    private final UserService userService = AppContext.getInstance().getUserService();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentPasswordParam = req.getParameter("currentPassword");
        String newPasswordParam = req.getParameter("newPassword");

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        user.setPassword(currentPasswordParam);

        if (!userService.checkPassword(user)) {
            req.setAttribute("message", "Please type your current password.");
            return USER_CHANGE_PASSWORD;
        } else {
            user.setPassword(newPasswordParam);
            userService.changePassword(user);
            return req.getContextPath() + "/pages/user/profile";
        }
    }


}
