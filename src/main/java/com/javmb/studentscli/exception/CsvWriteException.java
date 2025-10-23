package com.javmb.studentscli.exception;

public class CsvWriteException extends RuntimeException {
    public CsvWriteException(String message, Throwable cause) {
        super(message, cause);
    }
}
