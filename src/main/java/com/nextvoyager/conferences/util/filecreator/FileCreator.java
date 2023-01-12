package com.nextvoyager.conferences.util.filecreator;

import com.nextvoyager.conferences.model.entity.Event;

import java.io.ByteArrayOutputStream;
import java.util.List;

public interface FileCreator {
    ByteArrayOutputStream generateStatisticsFile(List<Event> eventsList, String lang);
    ExportFileFormat getFileFormat();
}