package com.nextvoyager.conferences.controller.actions.event;

import com.nextvoyager.conferences.AppContext;
import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.dao.event.EventDAO;
import com.nextvoyager.conferences.service.EventService;
import com.nextvoyager.conferences.util.PDFCreator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

//@WebServlet("/event/save-statistics")
public class EventStatisticsSaveAction implements ControllerAction {
    private final EventService eventService = AppContext.getInstance().getEventService();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageParam = req.getParameter("page");

        // Default values for list of events
        int page = 1;
        int limit = 10;

        if (pageParam != null) {
            page = Integer.parseInt(pageParam);
        }

        HttpSession currentSession = req.getSession();
        String lang = (String) currentSession.getAttribute("lang");

        EventDAO.SortType eventListSortType = Optional.ofNullable((EventDAO.SortType) currentSession
                .getAttribute("eventListSortType")).orElse(EventDAO.SortType.Date);

        EventDAO.SortDirection eventListSortDirection = Optional.ofNullable((EventDAO.SortDirection) currentSession
                .getAttribute("eventListSortDirection")).orElse(EventDAO.SortDirection.Ascending);

        EventDAO.TimeFilter eventTimeFilter = Optional.ofNullable((EventDAO.TimeFilter) currentSession
                .getAttribute("eventTimeFilter")).orElse(EventDAO.TimeFilter.AllTime);

        EventDAO.ListWithCountResult countAndList = eventService.listWithPagination(page, limit, eventListSortType,
                eventListSortDirection, eventTimeFilter);

        int numOfPages = (int)Math.ceil((double)countAndList.getCount()/limit);

        ByteArrayOutputStream pdfByteArray = PDFCreator.generateStatisticsFile(countAndList.getList(),lang);

        // setting some response headers
        resp.setHeader("Expires", "0");
        resp.setHeader("Cache-Control",
                "must-revalidate, post-check=0, pre-check=0");
        resp.setHeader("Pragma", "public");
        // setting the content type
        resp.setContentType("application/pdf");
        // the contentlength
        resp.setContentLength(pdfByteArray.size());
        // write ByteArrayOutputStream to the ServletOutputStream
        OutputStream os = resp.getOutputStream();
        pdfByteArray.writeTo(os);
        os.flush();
        os.close();

        return req.getContextPath() + "/pages/event/statistics";

    }

}
