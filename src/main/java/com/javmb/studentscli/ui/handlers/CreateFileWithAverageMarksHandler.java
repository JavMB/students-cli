package com.javmb.studentscli.ui.handlers;

import com.javmb.studentscli.config.Config;
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
            log.info("Archivo con medias creado - output={}", config.getFiles().getStudentsAvgXml());
            return true;
        } catch (Exception ex) {
            log.error("Error creando archivo con medias - output={}", config.getFiles().getStudentsAvgXml(), ex);
            return false;
        }
    }


}
