package com.javmb.studentscli.exception;

import java.io.IOException;

public class LoadingUserConfigException extends RuntimeException {
    public LoadingUserConfigException(String message, IOException e) {
        super(message);
    }
}
