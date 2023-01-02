package com.nextvoyager.conferences.controller.frontcontroller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ControllerAction {
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
