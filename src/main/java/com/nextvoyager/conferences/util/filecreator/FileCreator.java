package com.nextvoyager.conferences.util.filecreator;

import com.nextvoyager.conferences.model.entity.Event;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * File creators interface.
 *
 * @author Stanislav Bozhevskyi
 */
public interface FileCreator {
    ByteArrayOutputStream generateStatisticsFile(List<Event> eventsList, String lang);
    ExportFileFormat getFileFormat();
}