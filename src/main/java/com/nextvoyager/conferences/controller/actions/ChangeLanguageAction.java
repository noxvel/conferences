package com.nextvoyager.conferences.controller.actions;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

//("/change-language")
public class ChangeLanguageAction implements ControllerAction {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lang = req.getParameter("lang");
        String redirectPath = req.getParameter("redirectPath");

        HttpSession session = req.getSession();
        session.setAttribute("lang", lang);
        if (redirectPath != null) {
            return redirectPath;
        } else {
            return req.getContextPath() + "/pages/home";
        }
    }
}
