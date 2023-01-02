package com.nextvoyager.conferences.controller.event;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;
import com.nextvoyager.conferences.AppContext;
import com.nextvoyager.conferences.model.dao.event.EventDAO;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.service.EventService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.List;

@WebServlet("/event/save-statistics")
public class EventStatisticsSave extends HttpServlet {
    private final EventService eventService = AppContext.getInstance().getEventService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageParam = req.getParameter("page");

        // Default values for list of events
        int page = 1;
        int limit = 10;

        if (pageParam != null) {
            page = Integer.parseInt(pageParam);
        }

        HttpSession currentSession = req.getSession();

        EventDAO.SortType eventListSortType = Optional.ofNullable((EventDAO.SortType) currentSession
                .getAttribute("eventListSortType")).orElse(EventDAO.SortType.Date);

        EventDAO.SortDirection eventListSortDirection = Optional.ofNullable((EventDAO.SortDirection) currentSession
                .getAttribute("eventListSortDirection")).orElse(EventDAO.SortDirection.Ascending);

        EventDAO.TimeFilter eventTimeFilter = Optional.ofNullable((EventDAO.TimeFilter) currentSession
                .getAttribute("eventTimeFilter")).orElse(EventDAO.TimeFilter.AllTime);

        EventDAO.ListWithCountResult countAndList = eventService.listWithPagination(page, limit, eventListSortType,
                eventListSortDirection, eventTimeFilter);

        int numOfPages = (int)Math.ceil((double)countAndList.getCount()/limit);

        ByteArrayOutputStream pdfByteArray = generatePDFFile(countAndList.getList());

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

        resp.sendRedirect("event/statistics");
//        req.setAttribute("events", countAndList.getList());
//        req.setAttribute("page", page);
//        req.setAttribute("sortType", eventListSortType);
//        req.setAttribute("sortDirection", eventListSortDirection);
//        req.setAttribute("eventTimeFilter", eventTimeFilter);
//        req.setAttribute("numOfPages", numOfPages);
//        req.getRequestDispatcher("/WEB-INF/jsp/event/event-statistics.jsp").forward(req,resp);

    }

    private ByteArrayOutputStream generatePDFFile(List<Event> eventsList) {

        Document myPDFDoc = new Document(PageSize.A4);

        // Define a string as title
        String title = "Event statistics";

        // Let's create a Table object
        Table myTable = new Table(4); // 4 columns
        myTable.setPadding(2f);
        myTable.setSpacing(1f);
        myTable.setWidth(100f);

        // Create the header of the table
        List<String> headerTable = new ArrayList<>();
        headerTable.add("Event");
        headerTable.add("Reports count");
        headerTable.add("Participants count");
        headerTable.add("Participants came");

        headerTable.forEach(e -> {
            Cell current = new Cell(new Phrase(e));
            current.setHeader(true);
            current.setBackgroundColor(Color.LIGHT_GRAY);
            myTable.addCell(current);
        });

        eventsList.forEach((el) -> {
            myTable.addCell(new Cell(new Phrase(el.getName())));
            myTable.addCell(new Cell(new Phrase(el.getReportsCount().toString())));
            myTable.addCell(new Cell(new Phrase(el.getParticipantsCount().toString())));
            myTable.addCell(new Cell(new Phrase(el.getParticipantsCame().toString())));
        });

        ByteArrayOutputStream pdfByteArray = new ByteArrayOutputStream();

        final PdfWriter pdfWriter = PdfWriter.getInstance(myPDFDoc, pdfByteArray);

        //1) Create a pdf object with using the class
        myPDFDoc.open();  // Open the Document

        /* Here we add some metadata to the generated pdf */
        myPDFDoc.addTitle("Event statistics");
        myPDFDoc.addSubject("This is a event statistics file");
        myPDFDoc.addKeywords("Statistics, Events");
        myPDFDoc.addCreator("Auto generate");
        /* End of the adding metadata section */

        // Create a Font object
        Font titleFont = new Font(Font.COURIER, 20f, Font.BOLDITALIC, Color.BLACK);

        // Create a paragraph with the new font
        Paragraph paragraph = new Paragraph(title,titleFont);

        // Element class provides properties to align
        // Content elements within the document
        paragraph.setAlignment(Element.ALIGN_CENTER);

        myPDFDoc.add(paragraph);

        // Adding an empty line
        myPDFDoc.add(new Paragraph(Chunk.NEWLINE));

        // Include the table to the document
        myPDFDoc.add(myTable);

        myPDFDoc.close();
        pdfWriter.close();

        return pdfByteArray;
    }
}
