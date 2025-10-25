// java
package com.javmb.studentscli.ui.handlers;

import com.javmb.studentscli.config.Config;
import com.javmb.studentscli.exception.CsvToXmlException;
import com.javmb.studentscli.exception.CsvWriteException;
import com.javmb.studentscli.model.Student;
import com.javmb.studentscli.service.interfaces.StudentsConverter;
import com.javmb.studentscli.service.interfaces.StudentsSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class AddStudentsViaDomHandler {

    private final Config config;
    private final StudentsSerializer studentsSerializer;
    private final StudentsConverter studentsConverter;

    public AddStudentsViaDomHandler(
            Config config,
            StudentsSerializer studentsSerializer,
            @Qualifier("csvToXmlStudentsConverter") StudentsConverter studentsConverter) {
        this.config = Objects.requireNonNull(config, "config must not be null");
        this.studentsSerializer = Objects.requireNonNull(studentsSerializer, "studentsSerializer must not be null");
        this.studentsConverter = Objects.requireNonNull(studentsConverter, "studentsConverter must not be null");
    }

    /**
     * Procesa y guarda una lista de estudiantes en formato CSV y XML.
     *
     * @param students lista de estudiantes a procesar
     * @return true si el procesamiento fue exitoso, false en caso contrario
     */
    public boolean handle(List<Student> students) {
        if (students == null || students.isEmpty()) {
            log.warn("No hay estudiantes para procesar");
            return false;
        }

        try {
            studentsSerializer.serialize(students, config.getFiles().getStudentsCsv());
            studentsConverter.convert(config.getFiles().getStudentsCsv(), config.getFiles().getStudentsXml());
            log.info("Procesados {} estudiantes correctamente", students.size());
            return true;
        } catch (CsvWriteException ex) {
            log.error("Error escribiendo CSV: {}", ex.getMessage());
            return false;
        } catch (CsvToXmlException ex) {
            log.error("Error convirtiendo CSV a XML: {}", ex.getMessage());
            return false;
        } catch (Exception ex) {
            log.error("Error inesperado: {}", ex.getMessage());
            return false;
        }

    }
}