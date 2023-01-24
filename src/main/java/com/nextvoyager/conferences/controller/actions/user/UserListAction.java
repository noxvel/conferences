package com.nextvoyager.conferences.controller.actions.user;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.dao.ListWithCount;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.UserService;
import com.nextvoyager.conferences.util.PaginationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Get list of users.
 * Path "/user/list".
 *
 * @author Stanislav Bozhevskyi
 */
public class UserListAction implements ControllerAction {

    private final UserService userService;

    public UserListAction(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        int page = PaginationUtil.handlePaginationPageParameter(req);
        int limit = PaginationUtil.handlePaginationLimitParameter(req, 12);

        HttpSession currentSession = req.getSession();

        ListWithCount<User> countAndList = userService.list(page, limit);

        int numOfPages = PaginationUtil.getNumOfPages(countAndList.getCount(),limit);

        req.setAttribute("page", page);
        req.setAttribute("limit", limit);
        req.setAttribute("users", countAndList.getList());
        req.setAttribute("numOfPages", numOfPages);
        return USER_LIST;

    }

}
