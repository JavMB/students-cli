package com.javmb.studentscli.util.lib;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileUtils {

    public static List<File> getFilesList(String path) {
        File folder = new File(path);
        if (!folder.exists() || !folder.isDirectory()) {
            return new ArrayList<>();
        }
        return Arrays.asList(folder.listFiles());
    }

    public static String getExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex != -1 && dotIndex < filename.length() - 1) {
            return filename.substring(dotIndex + 1);
        } else {
            return "";
        }
    }


    public static List<File> getFilesByExtension(String path, String extension) {
        List<File> result = new ArrayList<>();
        List<File> allFiles = getFilesList(path);

        for (File file : allFiles) {
            if (file.isFile() && file.getName().endsWith(extension)) {
                result.add(file);
            }
        }
        return result;
    }


    public static List<String> readFileLines(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Archivo no encontrado: " + filePath);
        } catch (IOException e) {
            System.err.println("Error leyendo archivo: " + e.getMessage());
        }
        return lines;
    }

    public static List<String> readFileWords(String filePath) {
        List<String> words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineWords = line.split("\\s+");
                Collections.addAll(words, lineWords);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Archivo no encontrado: " + filePath);
        } catch (IOException e) {
            System.err.println("Error leyendo archivo: " + e.getMessage());
        }
        return words;
    }


    public static boolean writeToFile(String filePath, String text, boolean append) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, append))) {
            if (append) {
                bw.newLine();
            }
            bw.write(text);
            return true;
        } catch (IOException e) {
            System.err.println("Error escribiendo archivo: " + e.getMessage());
            return false;
        }
    }


    public static long getFileSize(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.err.println("Archivo no existe: " + filePath);
            return -1;
        }
        return file.length();
    }


    public static String getLastModified(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return "Archivo no existe";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(new Date(file.lastModified()));
    }


}