package com.nextvoyager.conferences.util.filecreator;

import com.nextvoyager.conferences.model.entity.Event;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;

public class CSVCreator implements FileCreator{
    @Override
    public ByteArrayOutputStream generateStatisticsFile(List<Event> eventsList, String lang) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             OutputStreamWriter outWriter = new OutputStreamWriter(out);
             ICSVWriter writer = new CSVWriterBuilder(outWriter).withSeparator(';').build()){

            ResourceBundle rb = ResourceBundle.getBundle( "text", new Locale(lang));

            // adding header to csv
            writer.writeNext(new String[]{
                rb.getString("event-statistics.table.event"),
                rb.getString("event-statistics.table.reports-count"),
                rb.getString("event-statistics.table.participants-count"),
                rb.getString("event-statistics.table.participants-came")
            });

            // add data to csv
            eventsList.forEach((event -> {
                writer.writeNext(new String[]{
                        event.getName(),
                        event.getReportsCount().toString(),
                        event.getParticipantsCount().toString(),
                        event.getParticipantsCame().toString()
                });
            }));

            outWriter.flush();
            return out;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ExportFileFormat getFileFormat() {
        return ExportFileFormat.CSV;
    }
}
