package com.nextvoyager.conferences.controller.user;

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

@WebServlet("/change-password")
public class ChangeUserPasswordController extends HttpServlet {

    UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/user/change-password.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentPasswordParam = req.getParameter("currentPassword");
        String newPasswordParam = req.getParameter("newPassword");

        // TODO: 01.12.2022 create validation to input data

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        user.setPassword(newPasswordParam);

        userService.update(user);
        resp.sendRedirect("profile");
    }


}
