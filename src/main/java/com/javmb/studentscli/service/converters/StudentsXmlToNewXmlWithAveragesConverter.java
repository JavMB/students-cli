package com.javmb.studentscli.service.converters;

import com.javmb.studentscli.exception.XmlConversionException;
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
            log.info("XML convertido con medias: {}", outputFilePath);
        } catch (XmlConversionException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al convertir XML: {} -> {}", inputFilePath, outputFilePath, e);
            throw new XmlConversionException("Error al convertir XML", e);
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
        }
    }

    /**
     * Extrae el valor de una nota del XML.
     * Asume que los datos ya fueron validados previamente (responsabilidad del InputValidator).
     */
    private double extractMarkValue(Element studentElement, String markTagName) {
        String studentId = studentElement.getAttribute("id");
        NodeList markNodes = studentElement.getElementsByTagName(markTagName);

        if (markNodes.getLength() == 0) {
            throw new XmlConversionException(String.format("Elemento XML mal formado: falta <%s> para estudiante id=%s", markTagName, studentId));
        }

        String markText = markNodes.item(0).getTextContent().trim();

        return Double.parseDouble(markText);

    }

    // por ser solo esto lo dejo aqui pero si no lo sacaria por SRP
    private double calculateAverage(double mark1, double mark2) {
        return (mark1 + mark2) / 2.0;
    }
}
