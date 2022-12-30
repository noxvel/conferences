package com.nextvoyager.conferences.controller.user;

import com.nextvoyager.conferences.AppContext;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.UserService;
import com.nextvoyager.conferences.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/profile")
public class ProfileUserController extends HttpServlet {

    private final UserService userService = AppContext.getInstance().getUserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        if (session != null && session.getAttribute("user") != null) {
            User currentUser = (User) session.getAttribute("user");
            req.setAttribute("currentUser", currentUser);
            req.getRequestDispatcher("/WEB-INF/jsp/user/profile.jsp").forward(req,resp);
        } else {
            resp.sendRedirect("login");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstNameParam = req.getParameter("firstName");
        String lastNameParam = req.getParameter("lastName");
//        String emailParam = req.getParameter("email");

        // TODO: 01.12.2022 create validation to input data

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        user.setFirstName(firstNameParam);
        user.setLastName(lastNameParam);
//        user.setEmail(emailParam);

        userService.update(user);
        resp.sendRedirect("profile");

    }
}
