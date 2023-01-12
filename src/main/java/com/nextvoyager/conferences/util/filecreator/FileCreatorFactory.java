package com.nextvoyager.conferences.util.filecreator;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class FileCreatorFactory {
    public static Optional<FileCreator> getFileCreator(ExportFileFormat format) {
        switch (format) {
            case XML: return Optional.of(new XMLCreator());
            case PDF: return Optional.of(new PDFCreator());
            case CSV: return Optional.of(new CSVCreator());
            default: return Optional.empty();
        }
    }

}
