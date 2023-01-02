package com.nextvoyager.conferences.controller.frontcontroller;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

public class ActionFactory {

    private static final Map<String, Action> actions = new HashMap<>();

    static{
//        actions.put("POST/register", new RegisterAction());
//        actions.put("POST/login", new LoginAction());
//        actions.put("GET/logout", new LogoutAction());
    }

    public static Action getAction(HttpServletRequest request) {
        return actions.get(request.getMethod() + request.getPathInfo());
    }
}
