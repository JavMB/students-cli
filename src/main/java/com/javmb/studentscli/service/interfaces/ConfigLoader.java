package com.javmb.studentscli.service.interfaces;

import java.io.IOException;
import java.nio.file.Path;

public interface ConfigLoader {

    void load(Path path) throws IOException;

}
