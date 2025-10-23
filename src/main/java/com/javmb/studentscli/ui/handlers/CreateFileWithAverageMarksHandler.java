package com.javmb.studentscli.ui.handlers;

import com.javmb.studentscli.config.Config;
import com.javmb.studentscli.exception.XmlConversionException;
import com.javmb.studentscli.service.interfaces.StudentsConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CreateFileWithAverageMarksHandler {

    private final Config config;
    private final StudentsConverter studentsConverter;

    public CreateFileWithAverageMarksHandler(
            Config config,
            @Qualifier("studentsXmlToNewXmlWithAveragesConverter") StudentsConverter studentsConverter) {
        this.config = config;
        this.studentsConverter = studentsConverter;
    }

    public boolean handle() {
        try {
            studentsConverter.convert(config.getFiles().getStudentsXml(), config.getFiles().getStudentsAvgXml());
            log.info("Archivo con medias creado: {}", config.getFiles().getStudentsAvgXml());
            return true;
        } catch (XmlConversionException ex) {
            log.error("Error al convertir XML con medias: {}", ex.getMessage());
            return false;
        } catch (Exception ex) {
            log.error("Error inesperado: {}", ex.getMessage());
            return false;
        }
    }


}
