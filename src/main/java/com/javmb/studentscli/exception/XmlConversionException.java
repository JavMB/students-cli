package com.javmb.studentscli.exception;
/**
 * Excepción lanzada cuando ocurre un error al convertir XML a XML con medias.
 */
public class XmlConversionException extends RuntimeException {
    public XmlConversionException(String message) {
        super(message);
    }
    public XmlConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}
