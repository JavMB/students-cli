package com.javmb.studentscli.service.configuration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.javmb.studentscli.config.Config;
import com.javmb.studentscli.service.interfaces.ConfigLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class YamlConfigLoader implements ConfigLoader {
    private final Config config;

    @Override
    public void load(Path path) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        JsonNode rootNode = mapper.readTree(path.toFile());
        JsonNode appNode = rootNode.get("app");
        if (appNode == null) return;


        JsonNode markNode = appNode.get("mark");
        if (markNode == null) return;
        JsonNode minNode = markNode.get("min");
        JsonNode maxNode = markNode.get("max");
        if (minNode == null || maxNode == null) return;

        try {
            double min = minNode.asDouble();
            double max = maxNode.asDouble();
            if (min <= max) {
                config.getMark().setMin(min);
                config.getMark().setMax(max);
            } else {
                System.err.println("[Config] min debe ser menor o igual que max en el YAML");
            }
        } catch (Exception e) {
            System.err.println("[Config] Formato inválido en min o max del YAML: " + e.getMessage());
        }
    }
}
