package com.nextvoyager.conferences.util.filecreator;

import com.nextvoyager.conferences.model.entity.Event;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ExcelCreator implements FileCreator{
    @Override
    public ByteArrayOutputStream generateStatisticsFile(List<Event> eventsList, String lang) {

        ResourceBundle rb = ResourceBundle.getBundle( "text", new Locale(lang));

        try(XSSFWorkbook workbook = new XSSFWorkbook()){

            Sheet sheet = workbook.createSheet();

            //Write header
            Row header = sheet.createRow(0);

            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            XSSFFont font = workbook.createFont();
            font.setFontName("Arial");
            font.setFontHeightInPoints((short) 14);
            font.setBold(true);
            headerStyle.setFont(font);

            Cell cell = header.createCell(1);
            cell.setCellValue(rb.getString("event-statistics.table.event"));
            cell.setCellStyle(headerStyle);

            cell = header.createCell(2);
            cell.setCellValue(rb.getString("event-statistics.table.reports-count"));
            cell.setCellStyle(headerStyle);

            cell = header.createCell(3);
            cell.setCellValue(rb.getString("event-statistics.table.participants-count"));
            cell.setCellStyle(headerStyle);

            cell = header.createCell(4);
            cell.setCellValue(rb.getString("event-statistics.table.participants-came"));
            cell.setCellStyle(headerStyle);

            //Write events
            int rowCount = 0;
            for (Event event : eventsList) {
                Row row = sheet.createRow(++rowCount);
                writeEvent(event, row);
            }

            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            workbook.write(byteArray);

            return byteArray;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ExportFileFormat getFileFormat() {
        return ExportFileFormat.Excel;
    }

    private void writeEvent(Event event, Row row) {
        Cell cell = row.createCell(1);
        cell.setCellValue(event.getName());

        cell = row.createCell(2);
        cell.setCellValue(event.getReportsCount());

        cell = row.createCell(3);
        cell.setCellValue(event.getParticipantsCount());

        cell = row.createCell(4);
        cell.setCellValue(event.getParticipantsCame());
    }
}
