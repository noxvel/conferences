package com.nextvoyager.conferences.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.*;

import com.nextvoyager.conferences.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.nextvoyager.conferences.controller.frontcontroller.ControllerAction.*;

/**
 * Authorization filter for requests
 *
 * @author Stanislav Bozhevskyi
 */
@WebFilter(urlPatterns = {"/pages/*"})
public class AuthorizationFilter implements Filter {

    private static final Logger LOG = LogManager.getLogger(AuthorizationFilter.class);

    private final static List<String> allUserAccess = new ArrayList<>();
    private final static List<String> authorizedUserAccess = new ArrayList<>();
    private final static List<String> ordinaryUserAccess = new ArrayList<>();
    private final static List<String> speakerAccess = new ArrayList<>();
    private final static List<String> moderatorAccess = new ArrayList<>();

    private final static Set<String> allPaths = new HashSet<>();

    public static List<String> getAllUserAccess(){ return Collections.unmodifiableList(allUserAccess); }
    public static List<String> getAuthorizedUserAccess(){ return Collections.unmodifiableList(authorizedUserAccess); }
    public static List<String> getOrdinaryUserAccess(){ return Collections.unmodifiableList(ordinaryUserAccess); }
    public static List<String> getSpeakerAccess(){ return Collections.unmodifiableList(speakerAccess); }
    public static List<String> getModeratorAccess(){ return Collections.unmodifiableList(moderatorAccess); }

    static{
        allUserAccess.add(HOME);
        allUserAccess.add(CHANGE_LANGUAGE);
        allUserAccess.add(USER_LOGIN);
        allUserAccess.add(USER_REGISTRATION);
        allUserAccess.add(EVENT_VIEW);
        allUserAccess.add(EVENT_LIST_FILTER);
        allUserAccess.add(EVENT_LIST_SORT);
        allUserAccess.add(EVENT_LIST_VIEW_FORM);
        allUserAccess.add(REPORT_VIEW);
        allUserAccess.add(USER_RESET_PASSWORD);
        allUserAccess.add(USER_FORGOT_PASSWORD);

        authorizedUserAccess.add(USER_PROFILE);
        authorizedUserAccess.add(USER_CHANGE_PASSWORD);
        authorizedUserAccess.add(USER_SIGN_OUT);

        ordinaryUserAccess.addAll(allUserAccess);
        ordinaryUserAccess.addAll(authorizedUserAccess);
        ordinaryUserAccess.add(EVENT_REGISTER);

        speakerAccess.addAll(allUserAccess);
        speakerAccess.addAll(authorizedUserAccess);
        speakerAccess.add(SPEAKER_REPORT_APPROVAL);
        speakerAccess.add(REPORT_LIST);
        speakerAccess.add(REPORT_LIST_FILTER);
        speakerAccess.add(REPORT_CREATE);
        speakerAccess.add(REPORT_EDIT);
        speakerAccess.add(REPORT_EDIT);
        speakerAccess.add(REPORT_EDIT);

        moderatorAccess.addAll(allUserAccess);
        moderatorAccess.addAll(authorizedUserAccess);
        moderatorAccess.add(USER_PROFILE);
        moderatorAccess.add(USER_CHANGE_PASSWORD);
        moderatorAccess.add(USER_SIGN_OUT);
        moderatorAccess.add(USER_LIST);
        moderatorAccess.add(MODERATOR_REPORT_APPROVAL);
        moderatorAccess.add(EVENT_CREATE);
        moderatorAccess.add(EVENT_DELETE);
        moderatorAccess.add(EVENT_EDIT);
        moderatorAccess.add(EVENT_STATISTICS);
        moderatorAccess.add(EVENT_SAVE_STATISTICS);
        moderatorAccess.add(REPORT_CREATE);
        moderatorAccess.add(REPORT_DELETE);
        moderatorAccess.add(REPORT_EDIT);
        moderatorAccess.add(REPORT_LIST);
        moderatorAccess.add(REPORT_LIST_FILTER);

        allPaths.addAll(ordinaryUserAccess);
        allPaths.addAll(speakerAccess);
        allPaths.addAll(moderatorAccess);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        String path = request.getPathInfo();
        if (!allPaths.contains(path)) {
            LOG.warn("Not found path - " + path);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                if (checkUnauthorizedUserAccess(path)) {
                    filterChain.doFilter(servletRequest, servletResponse); // Logged-in user found, so just continue request.
                } else {
                    LOG.warn("Unauthorized access by path - " + path);
                    response.sendRedirect(request.getContextPath() + PREFIX_PATH + USER_LOGIN); // No logged-in user found, so redirect to login page.
                }
            }else{
                if (checkAccess(path,user.getRole())) {
                    filterChain.doFilter(servletRequest, servletResponse); // there is access for path, so just continue request.
                } else {
                    LOG.warn("Access violation by path - " + path +", for user - " + user.getEmail());
                    response.sendError(HttpServletResponse.SC_FORBIDDEN); // No access for current user.
                }
            }
        }
    }

    boolean checkUnauthorizedUserAccess(String path) {
        return allUserAccess.contains(path);
    }

    boolean checkAccess(String path, User.Role userRole) {

        switch (userRole) {
            case ORDINARY_USER:
                return ordinaryUserAccess.contains(path);
            case SPEAKER:
                return speakerAccess.contains(path);
            case MODERATOR:
                return moderatorAccess.contains(path);
            default:
                return false;
        }

    }

}
