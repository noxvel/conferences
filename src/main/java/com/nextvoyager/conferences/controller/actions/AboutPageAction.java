package com.nextvoyager.conferences.controller.actions;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AboutPageAction implements ControllerAction {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return ABOUT;
    }
}
