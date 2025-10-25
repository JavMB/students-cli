package com.javmb.studentscli.service.converters;

import com.javmb.studentscli.exception.CsvToXmlException;
import com.javmb.studentscli.service.interfaces.StudentsConverter;
import com.javmb.studentscli.util.XmlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Slf4j
public class CsvToXmlStudentsConverter implements StudentsConverter {

    /**
     * Convierte un archivo CSV de estudiantes a formato XML.
     *
     * @param inputFilePath  ruta del archivo CSV de entrada
     * @param outputFilePath ruta del archivo XML de salida
     * @throws CsvToXmlException si ocurre un error durante la conversión
     */
    @Override
    public void convert(String inputFilePath, String outputFilePath) {
        try (var reader = Files.newBufferedReader(Path.of(inputFilePath))) {
            String[] headers = readHeaders(reader);
            Document doc = XmlUtils.createDocument();
            Element root = doc.createElement("students");
            doc.appendChild(root);
            processLines(reader, headers, doc, root);
            XmlUtils.writeDocument(doc, outputFilePath);

        } catch (IOException e) {
            throw new CsvToXmlException("Error al convertir CSV a XML", e);
        } catch (Exception e) {
            log.error("Error inesperado al convertir CSV a XML ({} -> {})", inputFilePath, outputFilePath, e);
            throw new CsvToXmlException("Error inesperado al convertir CSV a XML", e);
        }
    }

    private String[] readHeaders(BufferedReader reader) throws IOException {
        String headerLine = reader.readLine();
        if (headerLine == null) throw new IllegalArgumentException("CSV vacío");
        return headerLine.split(",");
    }

    private void processLines(BufferedReader reader, String[] headers, Document doc, Element root) throws IOException {
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
