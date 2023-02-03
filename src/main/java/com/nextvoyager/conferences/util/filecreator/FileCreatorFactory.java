package com.nextvoyager.conferences.util.filecreator;

import java.util.Optional;

/**
 * File creator factory method class
 *
 * @author Stanislav Bozhevskyi
 */
public class FileCreatorFactory {
    public static Optional<FileCreator> getFileCreator(ExportFileFormat format) {
        switch (format) {
            case XML: return Optional.of(new XMLCreator());
            case PDF: return Optional.of(new PDFCreator());
            case CSV: return Optional.of(new CSVCreator());
            case Excel: return Optional.of(new ExcelCreator());
            default: return Optional.empty();
        }
    }

}
