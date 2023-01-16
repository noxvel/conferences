package com.nextvoyager.conferences.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.nextvoyager.conferences.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.nextvoyager.conferences.controller.frontcontroller.ControllerAction.*;

@WebFilter(urlPatterns = {"/pages/*"})
public class AuthorizationFilter implements Filter {

    private static final Logger LOGGER = LogManager.getLogger(AuthorizationFilter.class);

    private final static List<String> allUserAccess = new ArrayList<>();
    private final static List<String> ordinaryUserAccess = new ArrayList<>();
    private final static List<String> speakerAccess = new ArrayList<>();
    private final static List<String> moderatorAccess = new ArrayList<>();

    public static List<String> getAllUserAccess(){
        return Collections.unmodifiableList(allUserAccess);
    }
    public static List<String> getOrdinaryUserAccess(){
        return Collections.unmodifiableList(ordinaryUserAccess);
    }
    public static List<String> getSpeakerAccess(){
        return Collections.unmodifiableList(speakerAccess);
    }
    public static List<String> getModeratorAccess(){
        return Collections.unmodifiableList(moderatorAccess);
    }

    static{
        allUserAccess.add(HOME);
        allUserAccess.add(CHANGE_LANGUAGE);
        allUserAccess.add(USER_LOGIN);
        allUserAccess.add(USER_REGISTRATION);
        allUserAccess.add(EVENT_VIEW);
        allUserAccess.add(EVENT_LIST_FILTER);
        allUserAccess.add(EVENT_LIST_SORT);
        allUserAccess.add(REPORT_VIEW);

        ordinaryUserAccess.addAll(allUserAccess);
        ordinaryUserAccess.add(USER_PROFILE);
        ordinaryUserAccess.add(USER_CHANGE_PASSWORD);
        ordinaryUserAccess.add(USER_SIGN_OUT);
        ordinaryUserAccess.add(EVENT_REGISTER);

        speakerAccess.addAll(allUserAccess);
        speakerAccess.add(USER_PROFILE);
        speakerAccess.add(USER_CHANGE_PASSWORD);
        speakerAccess.add(USER_SIGN_OUT);
        speakerAccess.add(SPEAKER_REPORT_APPROVAL);
        speakerAccess.add(EVENT_REGISTER);
        speakerAccess.add(REPORT_LIST);
        speakerAccess.add(REPORT_LIST_FILTER);
        speakerAccess.add(REPORT_CREATE);
        speakerAccess.add(REPORT_EDIT);
        speakerAccess.add(REPORT_EDIT);
        speakerAccess.add(REPORT_EDIT);

        moderatorAccess.addAll(allUserAccess);
        moderatorAccess.add(USER_PROFILE);
        moderatorAccess.add(USER_CHANGE_PASSWORD);
        moderatorAccess.add(USER_SIGN_OUT);
        moderatorAccess.add(MODERATOR_REPORT_APPROVAL);
        moderatorAccess.add(EVENT_CREATE);
        moderatorAccess.add(EVENT_DELETE);
        moderatorAccess.add(EVENT_EDIT);
        moderatorAccess.add(EVENT_REGISTER);
        moderatorAccess.add(EVENT_STATISTICS);
        moderatorAccess.add(EVENT_SAVE_STATISTICS);
        moderatorAccess.add(REPORT_CREATE);
        moderatorAccess.add(REPORT_DELETE);
        moderatorAccess.add(REPORT_EDIT);
        moderatorAccess.add(REPORT_LIST);
        moderatorAccess.add(REPORT_LIST_FILTER);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        User.Role userRole = (User.Role) session.getAttribute("userRole");
        String path = request.getPathInfo().substring(1);

        if (checkAccess(path,userRole)) {
            filterChain.doFilter(servletRequest, servletResponse); // Logged-in user found, so just continue request.
        } else {
            LOGGER.warn("Unauthorized access by path - " + path);
            response.sendRedirect(request.getContextPath() + "/pages/user/login"); // No logged-in user found, so redirect to login page.
        }

    }

    boolean checkAccess(String path, User.Role userRole) {
        Optional<String> result = Optional.empty();

        if (userRole == null) {
            result = allUserAccess.stream().filter(path::equals).findAny();
        } else {
            switch (userRole) {
                case ORDINARY_USER:
                    result = ordinaryUserAccess.stream().filter(path::equals).findAny(); break;
                case SPEAKER:
                    result = speakerAccess.stream().filter(path::equals).findAny(); break;
                case MODERATOR:
                    result = moderatorAccess.stream().filter(path::equals).findAny(); break;
            }
        }

        return result.isPresent();
    }

}
