package com.javmb.studentscli.util;

import lombok.experimental.UtilityClass;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Utilidades comunes para trabajar con XML usando DOM.
 */
@UtilityClass
public class XmlUtils {

    /**
     * Crea un nuevo documento XML vacío.
     *
     * @return Document vacío
     * @throws ParserConfigurationException si hay error al crear el DocumentBuilder
     */
    public static Document createDocument() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.newDocument();
    }

    /**
     * Lee un documento XML desde un archivo.
     *
     * @param filePath ruta del archivo XML
     * @return Document parseado
     * @throws Exception si el archivo no existe o hay error al parsear
     */
    public static Document readDocument(String filePath) throws Exception {
        Path path = Path.of(filePath);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("El archivo XML no existe: " + filePath);
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new File(filePath));
    }

    /**
     * Escribe un documento XML a un archivo con formato e indentación.
     *
     * @param doc Document a escribir
     * @param outputFilePath ruta del archivo de salida
     * @throws IOException si hay error al crear directorios
     * @throws TransformerException si hay error al escribir el XML
     */
    public static void writeDocument(Document doc, String outputFilePath) throws IOException, TransformerException {
        Path outputPath = Path.of(outputFilePath);

        // Crear directorios si no existen
        if (outputPath.getParent() != null) {
            Files.createDirectories(outputPath.getParent());
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // Configuración de formato
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(outputFilePath));
        transformer.transform(source, result);
    }
}

