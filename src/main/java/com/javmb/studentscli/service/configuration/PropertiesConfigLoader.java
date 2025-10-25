package com.javmb.studentscli.service.configuration;

import com.javmb.studentscli.config.Config;
import com.javmb.studentscli.service.interfaces.ConfigLoader;
import com.javmb.studentscli.util.StudentsInputValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class PropertiesConfigLoader implements ConfigLoader {
    private final Config config;
    private final StudentsInputValidator studentsInputValidator;

    @Override
    public void load(Path path) throws IOException {
        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(path)) {
            props.load(in);
        }
        String minS = props.getProperty("app.mark.min");
        String maxS = props.getProperty("app.mark.max");
        if (minS != null && maxS != null) {
            try {
                double min = Double.parseDouble(minS);
                double max = Double.parseDouble(maxS);
                if (studentsInputValidator.isValidMark(min) && studentsInputValidator.isValidMark(max) && min <= max) {
                    config.getMark().setMin(min);
                    config.getMark().setMax(max);
                }
            } catch (NumberFormatException e) {
                System.err.println("[Config] Formato inválido en app.mark.min o app.mark.max: " + e.getMessage());
            }
        }
    }
}
