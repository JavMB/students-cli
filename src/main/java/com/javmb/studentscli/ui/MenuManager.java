package com.javmb.studentscli.ui;

import com.javmb.studentscli.exception.InvalidMarkException;
import com.javmb.studentscli.model.Student;
import com.javmb.studentscli.ui.handlers.AddStudentsViaDomHandler;
import com.javmb.studentscli.ui.handlers.CreateFileWithAverageMarksHandler;
import com.javmb.studentscli.util.InputValidator;
import com.javmb.studentscli.util.lib.ConsoleMenu;
import com.javmb.studentscli.util.lib.IO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class MenuManager {

    private static final int EXIT_ID = 0;
    private static final String SUCCESS_ICON = "[ * ]";
    private static final String ERROR_ICON = "[ x ]";

    private final AddStudentsViaDomHandler addStudentsHandler;
    private final CreateFileWithAverageMarksHandler avgMarksHandler;
    private final InputValidator inputValidator;

    public void showMainMenu() {
        new ConsoleMenu("MAIN MENU", true)
                .addOption("Add students and marks via DOM", this::addStudentsOption)
                .addOption("Create file with average marks", this::createAveragesOption)
                .show();
    }

    private void addStudentsOption() {
        List<Student> students = askForStudents();

        if (addStudentsHandler.handle(students)) {
            printSuccess("Estudiantes guardados correctamente");
        } else {
            printError("Error al guardar estudiantes");
        }
    }

    private void createAveragesOption() {
        if (avgMarksHandler.handle()) {
            printSuccess("Archivo con medias creado correctamente");
        } else {
            printError("Error al crear archivo con medias");
        }
    }

    private List<Student> askForStudents() {
        List<Student> students = new ArrayList<>();

        printHeader();

        while (true) {
            int id = readStudentId();
            if (id == EXIT_ID) break;

            String name = readStudentName();

            Student student = readStudentMarksAndBuild(id, name);
            if (student != null) {
                students.add(student);
                printSuccess("Añadido: " + student);
            }
        }

        log.info("Total de estudiantes ingresados: {}", students.size());
        return Collections.unmodifiableList(students);
    }

    private void printHeader() {
        System.out.println("\n=== Añadir Estudiantes ===");
        System.out.println(inputValidator.getMarkRangeInfo());
        System.out.println();
    }

    private int readStudentId() {
        return IO.readInt("ID del estudiante (0 para terminar): ");
    }

    private String readStudentName() {
        while (true) {
            String name = IO.readNonEmptyString("Nombre completo: ").trim();

            if (isValidName(name)) {
                return name;
            }

            printError("El nombre no puede ser solo números. Intenta de nuevo.");
        }
    }

    private boolean isValidName(String name) {
        // al menos una letra
        return name.matches(".*[a-zA-ZáéíóúÁÉÍÓÚñÑ].*");
    }

    private Student readStudentMarksAndBuild(int id, String name) {
        try {
            double mark1 = inputValidator.readMark("Nota 1er trimestre: ");
            double mark2 = inputValidator.readMark("Nota 2do trimestre: ");

            return Student.builder()
                    .id(id)
                    .name(name)
                    .mark1(mark1)
                    .mark2(mark2)
                    .build();

        } catch (InvalidMarkException e) {
            printError(e.getMessage());
            System.out.println("Estudiante no añadido. Intenta de nuevo.\n");
            return null;
        }
    }

    private void printSuccess(String message) {
        System.out.println(SUCCESS_ICON + " " + message);
    }

    private void printError(String message) {
        System.out.println(ERROR_ICON + " " + message);
    }
}