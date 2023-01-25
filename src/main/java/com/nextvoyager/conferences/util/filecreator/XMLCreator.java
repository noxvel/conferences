package com.nextvoyager.conferences.util.filecreator;

import com.nextvoyager.conferences.model.entity.Event;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Create files in XML format.
 *
 * @author Stanislav Bozhevskyi
 */
public class XMLCreator implements FileCreator{

    @Override
    public ExportFileFormat getFileFormat() {
        return ExportFileFormat.XML;
    }

    @Override
    public ByteArrayOutputStream generateStatisticsFile(List<Event> eventsList, String lang) {

        // use STAX stream writer
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            XMLOutputFactory factory = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = factory.createXMLStreamWriter(out);

            writer.writeStartDocument("utf-8", "1.0");

            // start root element
            writer.writeStartElement("events");

            for (Event event: eventsList) {
                writer.writeStartElement("event");

                writer.writeStartElement("name");
                writer.writeCharacters(event.getName());
                writer.writeEndElement();

                writer.writeStartElement("reports-count");
                writer.writeCharacters(event.getReportsCount().toString());
                writer.writeEndElement();

                writer.writeStartElement("participants-count");
                writer.writeCharacters(event.getParticipantsCount().toString());
                writer.writeEndElement();

                writer.writeStartElement("participants-came");
                writer.writeCharacters(event.getParticipantsCame().toString());
                writer.writeEndElement();

                writer.writeEndElement();
            }

            // end root element
            writer.writeEndDocument();

            writer.flush();
            writer.close();

            return out;

        } catch (IOException | XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }

}
