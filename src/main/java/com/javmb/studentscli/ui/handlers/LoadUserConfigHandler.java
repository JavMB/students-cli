package com.javmb.studentscli.ui.handlers;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.stereotype.Component;

import com.javmb.studentscli.service.configuration.YamlConfigLoader;
import com.javmb.studentscli.service.configuration.PropertiesConfigLoader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class LoadUserConfigHandler {

    private final YamlConfigLoader yamlConfigLoader;
    private final PropertiesConfigLoader propertiesConfigLoader;

    public boolean handle(String configPath) {
        Path path = Path.of(configPath);
        try {
            String fileName = path.getFileName().toString().toLowerCase();
            if (fileName.endsWith(".yml") || fileName.endsWith(".yaml")) {
                yamlConfigLoader.load(path);
            } else if (fileName.endsWith(".properties")) {
                propertiesConfigLoader.load(path);
            } else {
                log.error("No se pudo determinar el tipo de archivo de configuración para: {}", path);
                return false;
            }
            return true;

        } catch (IOException e) {
            log.error("Error loading config file: {}", e.getMessage(), e);
            return false;
        } catch (Exception e) {
            log.error("Error inesperado al cargar el archivo de config: {}", e.getMessage(), e);
            return false;
        }
    }

}
