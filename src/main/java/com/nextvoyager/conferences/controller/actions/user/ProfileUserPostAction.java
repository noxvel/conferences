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

//@WebServlet("/user/profile")
public class ProfileUserPostAction implements ControllerAction {

    private final UserService userService = AppContext.getInstance().getUserService();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstNameParam = req.getParameter("firstName");
        String lastNameParam = req.getParameter("lastName");
//        String emailParam = req.getParameter("email");

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        user.setFirstName(firstNameParam);
        user.setLastName(lastNameParam);
//        user.setEmail(emailParam);

        userService.update(user);
        return req.getContextPath() + "/pages/home";

    }
}
