package com.javmb.studentscli.ui.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.javmb.studentscli.config.Config;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

@Component
@Slf4j
@RequiredArgsConstructor
public class ExportUserConfigHandler {
    private final Config config;

    public boolean handle(String exportPath) {
        File file = new File(exportPath);
        if (file.exists()) {
            log.error("El fichero ya existe y no se sobrescribirá: {}", exportPath);
            return false;
        }
        String lower = exportPath.toLowerCase();
        try {
            if (lower.endsWith(".yaml") || lower.endsWith(".yml")) {
                exportAsYaml(file);
            } else if (lower.endsWith(".properties")) {
                exportAsProperties(file);
            } else {
                log.error("Extensión no soportada: {}", exportPath);
                return false;
            }
            log.info("Configuración exportada correctamente a {}", exportPath);
            return true;
        } catch (Exception e) {
            log.error("Error exportando configuración: {}", e.getMessage());
            return false;
        }
    }

    private void exportAsYaml(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        var node = mapper.createObjectNode();
        var app = mapper.createObjectNode();
        var mark = mapper.createObjectNode();
        mark.put("min", config.getMark().getMin());
        mark.put("max", config.getMark().getMax());
        app.set("mark", mark);
        node.set("app", app);
        mapper.writeValue(file, node);
    }

    private void exportAsProperties(File file) throws IOException {
        Properties props = new Properties();
        props.setProperty("app.mark.min", String.valueOf(config.getMark().getMin()));
        props.setProperty("app.mark.max", String.valueOf(config.getMark().getMax()));
        try (FileWriter writer = new FileWriter(file)) {
            props.store(writer, "Exported config");
        }
    }
}
