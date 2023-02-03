package com.nextvoyager.conferences.util.filecreator;

import lombok.Getter;

/**
 * Enumeration for export file formats.
 *
 * @author Stanislav Bozhevskyi
 */
@Getter
public enum ExportFileFormat {
    PDF("application/pdf"),
    XML("text/xml"),
    CSV("text/csv"),
    Excel("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

    private final String contentType;
    ExportFileFormat(String contentType) {
        this.contentType = contentType;
    }
}
