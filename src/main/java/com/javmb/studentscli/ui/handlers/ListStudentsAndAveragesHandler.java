package com.javmb.studentscli.ui.handlers;

import com.javmb.studentscli.config.Config;
import com.javmb.studentscli.exception.XmlParseException;
import com.javmb.studentscli.model.Student;
import com.javmb.studentscli.service.parsers.StudentXmlParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.xml.parsers.SAXParserFactory;
import java.io.File;
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
     * @return lista de estudiantes con sus notas promedio
     * @throws XmlParseException si ocurre un error al parsear el XML
     */
    public List<Student> handle() {
        try {
            List<Map<String, String>> maps = new ArrayList<>();
            List<Student> students = new ArrayList<>();
            var handler = new StudentXmlParser(maps, students, "student");
            var saxParser = SAXParserFactory.newInstance().newSAXParser();
            saxParser.parse(new File(config.getFiles().getStudentsAvgXml()), handler);
            return students;
        } catch (Exception e) {
            log.error("Error al parsear el XML de estudiantes", e);
            throw new XmlParseException("No se pudo parsear el XML de estudiantes: " + e.getMessage(), e);
        }
    }
}
