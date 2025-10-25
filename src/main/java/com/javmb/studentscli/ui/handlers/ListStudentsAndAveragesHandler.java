package com.javmb.studentscli.ui.handlers;

import com.javmb.studentscli.config.Config;
import com.javmb.studentscli.model.Student;
import com.javmb.studentscli.service.parsers.StudentXmlParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class ListStudentsAndAveragesHandler {
    private final Config config;

    /**
     * Obtiene la lista de estudiantes con sus promedios desde el archivo XML.
     *
     * @return lista de estudiantes con sus notas promedio (vacía si ocurre un error al parsear)
     */
    public List<Student> handle() {
        List<Map<String, String>> maps = new ArrayList<>();
        List<Student> students = new ArrayList<>();
        try {
            var handler = new StudentXmlParser(maps, students, "student");
            var saxParser = SAXParserFactory.newInstance().newSAXParser();
            saxParser.parse(new File(config.getFiles().getStudentsAvgXml()), handler);
        } catch (ParserConfigurationException ex) {
            log.error("Parser configuration error al parsear el XML de estudiantes: {}", ex.getMessage(), ex);
        } catch (SAXException ex) {
            log.error("Error SAX al parsear el XML de estudiantes: {}", ex.getMessage(), ex);
        } catch (IOException ex) {
            log.error("Error de E/S al leer el archivo XML de estudiantes: {}", ex.getMessage(), ex);
        } catch (Exception ex) {
            log.error("Error inesperado al parsear el XML de estudiantes: {}", ex.getMessage(), ex);
        }
        return students;
    }
}
