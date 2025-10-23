package com.javmb.studentscli.service.converters;

import com.javmb.studentscli.exception.CsvToXmlException;
import com.javmb.studentscli.service.interfaces.StudentsConverter;
import com.javmb.studentscli.util.XmlUtils;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class CsvToXmlStudentsConverter implements StudentsConverter {

    @Override
    public void convert(String inputFilePath, String outputFilePath) {
        try (var reader = Files.newBufferedReader(Path.of(inputFilePath))) {
            String[] headers = readHeaders(reader);
            Document doc = XmlUtils.createDocument();
            Element root = doc.createElement("students");
            doc.appendChild(root);
            processLines(reader, headers, doc, root);
            XmlUtils.writeDocument(doc, outputFilePath);
        } catch (Exception e) {
            throw new CsvToXmlException("Error al convertir CSV a XML", e);
        }
    }

    private String[] readHeaders(BufferedReader reader) throws Exception {
        String headerLine = reader.readLine();
        if (headerLine == null) throw new IllegalArgumentException("CSV vacío");
        return headerLine.split(",");
    }

    private void processLines(BufferedReader reader, String[] headers, Document doc, Element root) throws Exception {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            Element student = doc.createElement("student");

            student.setAttribute("id", values[0].trim());

            for (int i = 1; i < headers.length; i++) {
                Element field = doc.createElement(headers[i].trim());
                field.appendChild(doc.createTextNode(values[i].trim()));
                student.appendChild(field);
            }
            root.appendChild(student);
        }
    }

}
