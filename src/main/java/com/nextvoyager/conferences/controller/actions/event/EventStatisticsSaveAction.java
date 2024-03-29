package com.nextvoyager.conferences.controller.actions.event;

import com.nextvoyager.conferences.controller.frontcontroller.ControllerAction;
import com.nextvoyager.conferences.model.dao.ListWithCount;
import com.nextvoyager.conferences.model.dao.event.EventDAO;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.service.EventService;
import com.nextvoyager.conferences.util.PaginationUtil;
import com.nextvoyager.conferences.util.filecreator.ExportFileFormat;
import com.nextvoyager.conferences.util.filecreator.FileCreator;
import com.nextvoyager.conferences.util.filecreator.FileCreatorFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

/**
 * Save event statistics to file in different formats.
 * Path "/event/save-statistics".
 *
 * @author Stanislav Bozhevskyi
 */
public class EventStatisticsSaveAction implements ControllerAction {

    private final EventService eventService;

    public EventStatisticsSaveAction(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int page = PaginationUtil.handlePaginationPageParameter(req);
        int limit = PaginationUtil.handlePaginationLimitParameter(req, 12);

        ExportFileFormat fileFormatParam = ExportFileFormat.valueOf(req.getParameter("fileFormat"));

        HttpSession currentSession = req.getSession();
        String lang = (String) currentSession.getAttribute("lang");

        EventDAO.SortType eventListSortType = Optional.ofNullable((EventDAO.SortType) currentSession
                .getAttribute("eventListSortType")).orElse(EventDAO.SortType.Date);

        EventDAO.SortDirection eventListSortDirection = Optional.ofNullable((EventDAO.SortDirection) currentSession
                .getAttribute("eventListSortDirection")).orElse(EventDAO.SortDirection.Ascending);

        EventDAO.TimeFilter eventTimeFilter = Optional.ofNullable((EventDAO.TimeFilter) currentSession
                .getAttribute("eventTimeFilter")).orElse(EventDAO.TimeFilter.AllTime);

        ListWithCount<Event> countAndList = eventService.listWithPaginationCommon(page, limit, eventListSortType,
                eventListSortDirection, eventTimeFilter);

        Optional<FileCreator> fileCreator = FileCreatorFactory.getFileCreator(fileFormatParam);
        fileCreator.ifPresent((fc -> {
            try (ByteArrayOutputStream fileByteArray = fc.generateStatisticsFile(countAndList.getList(), lang);
                 OutputStream os = resp.getOutputStream()) {

                // setting some response headers
                resp.setHeader("Expires", "0");
                resp.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
                resp.setHeader("Pragma", "public");
//                resp.setHeader("Content-disposition", "attachment");
                // setting the content type
                resp.setContentType(fc.getFileFormat().getContentType());
                // the content length
                resp.setContentLength(fileByteArray.size());

                // write ByteArrayOutputStream to the ServletOutputStream
                fileByteArray.writeTo(os);
                os.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));

        return "";

    }

}
