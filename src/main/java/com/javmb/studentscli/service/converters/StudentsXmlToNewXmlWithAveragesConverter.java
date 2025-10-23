package com.javmb.studentscli.service.converters;

import com.javmb.studentscli.exception.CsvToXmlException;
import com.javmb.studentscli.exception.InvalidMarkException;
import com.javmb.studentscli.service.interfaces.StudentsConverter;
import com.javmb.studentscli.util.XmlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.Locale;

@Service
@Slf4j
public class StudentsXmlToNewXmlWithAveragesConverter implements StudentsConverter {

    @Override
    public void convert(String inputFilePath, String outputFilePath) {
        try {
            Document doc = XmlUtils.readDocument(inputFilePath);
            processStudentsWithAverages(doc);
            XmlUtils.writeDocument(doc, outputFilePath);
            log.info("Successfully created XML with averages - input={} output={}", inputFilePath, outputFilePath);
        } catch (Exception e) {
            log.error("Error al convertir XML a XML con medias - input={} output={}", inputFilePath, outputFilePath, e);
            throw new CsvToXmlException("Error al convertir XML a XML con medias", e);
        }
    }


    private void processStudentsWithAverages(Document doc) {
        NodeList studentNodes = doc.getElementsByTagName("student");

        for (int i = 0; i < studentNodes.getLength(); i++) {
            Element studentElement = (Element) studentNodes.item(i);

            double mark1 = extractMarkValue(studentElement, "mark1");
            double mark2 = extractMarkValue(studentElement, "mark2");
            double average = calculateAverage(mark1, mark2);

            Element averageElement = doc.createElement("markAverage");
            averageElement.setTextContent(String.format(Locale.US, "%.2f", average));
            studentElement.appendChild(averageElement);

            log.debug("Calculated average for student id={}: mark1={} mark2={} avg={}",
                    studentElement.getAttribute("id"), mark1, mark2, average);
        }
    }

    private double extractMarkValue(Element studentElement, String markTagName) {
        String studentId = studentElement.getAttribute("id");
        NodeList markNodes = studentElement.getElementsByTagName(markTagName);

        if (markNodes.getLength() == 0) {
            String errorMsg = String.format("Falta %s para estudiante id=%s", markTagName, studentId);
            log.error(errorMsg);
            throw new InvalidMarkException(errorMsg);
        }

        String markText = markNodes.item(0).getTextContent().trim();
        try {
            return Double.parseDouble(markText);
        } catch (NumberFormatException e) {
            String errorMsg = String.format("Valor inválido para %s en estudiante id=%s: '%s'",
                    markTagName, studentId, markText);
            log.error(errorMsg);
            throw new InvalidMarkException(errorMsg, e);
        }
    }

    // por ser solo esto lo dejo aqui pero si no lo sacaria por SRP
    private double calculateAverage(double mark1, double mark2) {
        return (mark1 + mark2) / 2.0;
    }
}
