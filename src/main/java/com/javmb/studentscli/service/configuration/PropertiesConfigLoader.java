package com.javmb.studentscli.service.configuration;

import com.javmb.studentscli.service.interfaces.ConfigLoader;

import java.io.IOException;
import java.nio.file.Path;

public class PropertiesConfigLoader implements ConfigLoader {


    @Override
    public void load(Path path) throws IOException {

    }

    @Override
    public String get(String key) {
        return "";
    }
}
