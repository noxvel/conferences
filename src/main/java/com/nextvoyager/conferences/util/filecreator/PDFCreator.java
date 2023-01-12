package com.nextvoyager.conferences.util.filecreator;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfWriter;
import com.nextvoyager.conferences.model.entity.Event;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class PDFCreator implements FileCreator{

    @Override
    public ExportFileFormat getFileFormat() {
        return ExportFileFormat.PDF;
    }

    @Override
    public ByteArrayOutputStream generateStatisticsFile(List<Event> eventsList, String lang) {

        try (Document myPDFDoc = new Document(PageSize.A4)) {
            ResourceBundle rb = ResourceBundle.getBundle( "text", new Locale(lang));

            // Define a string as title
            String title = rb.getString("event-statistics.header.text");

            // Let's create a Table object
            Table myTable = new Table(4); // 4 columns
            myTable.setPadding(2f);
            myTable.setSpacing(1f);
            myTable.setWidth(100f);

            // Create the header of the table
            List<String> headerTable = new ArrayList<>();

            headerTable.add(rb.getString("event-statistics.table.event"));
            headerTable.add(rb.getString("event-statistics.table.reports-count"));
            headerTable.add(rb.getString("event-statistics.table.participants-count"));
            headerTable.add(rb.getString("event-statistics.table.participants-came"));

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
            Paragraph paragraph = new Paragraph(title, titleFont);

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

        } catch (DocumentException exception) {
            throw new RuntimeException(exception);
        }
    }

}
