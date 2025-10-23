// java
package com.javmb.studentscli.ui.handlers;

import com.javmb.studentscli.config.Config;
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

    public void handle(List<Student> students) {
        if (students == null || students.isEmpty()) {
            log.warn("No hay estudiantes para procesar. csv={} xml={}",
                    config.getFiles().getStudentsCsv(), config.getFiles().getStudentsXml());
            return;
        }


        try {
            studentsSerializer.serialize(students, config.getFiles().getStudentsCsv());
            studentsConverter.convert(config.getFiles().getStudentsCsv(), config.getFiles().getStudentsXml());

        } catch (IllegalArgumentException iae) {
            log.warn("Error de validación procesando {} estudiantes: {} csv={}",
                    students.size(), iae.getMessage(), config.getFiles().getStudentsCsv(), iae);
            throw iae;
        } catch (Exception ex) {
            log.error("Fallo procesando estudiantes - count={} csv={} xml={}",
                    students.size(),
                    config.getFiles().getStudentsCsv(),
                    config.getFiles().getStudentsXml(), ex);
            throw new IllegalStateException("No se pudo completar la operación. Revisa logs.", ex);
        }
    }
}