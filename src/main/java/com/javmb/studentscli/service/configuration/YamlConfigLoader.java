package com.javmb.studentscli.service.configuration;

import com.javmb.studentscli.service.interfaces.ConfigLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
@Service
public class YamlConfigLoader implements ConfigLoader {
    @Override
    public void load(Path path) throws IOException {



    }

    @Override
    public String get(String key) {
        return "";
    }
}
