package com.javmb.studentscli.service.converters;

import com.javmb.studentscli.exception.CsvToXmlException;
import com.javmb.studentscli.service.interfaces.StudentsConverter;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class CsvToXmlStudentsConverter implements StudentsConverter {


    @Override
    public void convert(String inputFilePath, String outputFilePath) {
        try (var reader = Files.newBufferedReader(Path.of(inputFilePath))) {
            String[] headers = readHeaders(reader);
            Document doc = createDocument();
            Element root = doc.createElement("students");
            doc.appendChild(root);
            processLines(reader, headers, doc, root);
            writeXml(doc, outputFilePath);
        } catch (Exception e) {
            throw new CsvToXmlException("Error al convertir CSV a XML", e);
        }
    }

    private String[] readHeaders(BufferedReader reader) throws Exception {
        String headerLine = reader.readLine();
        if (headerLine == null) throw new IllegalArgumentException("CSV vacío");
        return headerLine.split(",");
    }

    private Document createDocument() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.newDocument();
    }

    // java
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


    private void writeXml(Document doc, String outputFilePath) throws Exception {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(doc), new StreamResult(new File(outputFilePath)));
    }

}
