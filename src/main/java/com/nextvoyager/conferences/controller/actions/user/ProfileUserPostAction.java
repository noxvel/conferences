package com.nextvoyager.conferences.controller.actions.user;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//("/user/profile")
public class ProfileUserPostAction implements ControllerAction {

    private final UserService userService;

    public ProfileUserPostAction(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        String firstNameParam = req.getParameter("firstName");
        String lastNameParam = req.getParameter("lastName");
        String receiveNotifications = req.getParameter("receiveNotifications");
//        String emailParam = req.getParameter("email");

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        user.setFirstName(firstNameParam);
        user.setLastName(lastNameParam);
        user.setReceiveNotifications(receiveNotifications != null);
//        user.setEmail(emailParam);

        userService.update(user);
        return PREFIX_PATH + HOME;

    }
}
