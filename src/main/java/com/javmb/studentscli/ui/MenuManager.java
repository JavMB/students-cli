package com.javmb.studentscli.ui;

import com.javmb.studentscli.model.Student;
import com.javmb.studentscli.ui.handlers.AddStudentsViaDomHandler;
import com.javmb.studentscli.ui.handlers.CreateFileWithAverageMarksHandler;
import com.javmb.studentscli.service.input.StudentInputService;
import com.javmb.studentscli.util.lib.ConsoleMenu;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class MenuManager {

    private static final String SUCCESS_ICON = "[ * ]";
    private static final String ERROR_ICON = "[ x ]";

    private final AddStudentsViaDomHandler addStudentsHandler;
    private final CreateFileWithAverageMarksHandler avgMarksHandler;
    private final StudentInputService studentInputService;

    public void showMainMenu() {
        new ConsoleMenu("MAIN MENU", true)
                .addOption("Add students and marks via DOM", this::addStudentsOption)
                .addOption("Create file with average marks", this::createAveragesOption)
                .show();
    }

    private void addStudentsOption() {
        List<Student> students = studentInputService.getStudentsFromInput();
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


    private void printSuccess(String message) {
        System.out.println(SUCCESS_ICON + " " + message);
    }

    private void printError(String message) {
        System.out.println(ERROR_ICON + " " + message);
    }
}