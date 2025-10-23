// java
package com.javmb.studentscli.ui;

import com.javmb.studentscli.model.Student;
import com.javmb.studentscli.ui.handlers.AddStudentsViaDomHandler;
import com.javmb.studentscli.ui.handlers.CreateFileWithAverageMarksHandler;
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

    private final AddStudentsViaDomHandler addStudentsHandler;
    private final CreateFileWithAverageMarksHandler avgMarksHandler;


    public void showMainMenu() {
        new ConsoleMenu("MAIN MENU", true)
                .addOption("Add students and marks via DOM", () ->
                        addStudentsHandler.handle(askForStudents()))
                .show();
    }

    private static List<Student> askForStudents() {
        List<Student> students = new ArrayList<>();

        while (true) {
            int id = IO.readInt("Enter student ID (0 to finish): ");
            if (id == 0) {
                break;
            }

            String name;
            do {
                name = IO.readString("Enter student name: ").trim();
                if (name.isEmpty()) {
                    System.out.println("Name cannot be empty. Try again.");
                }
            } while (name.isEmpty());

            double mark1 = IO.readDouble("Enter mark 1: ");
            double mark2 = IO.readDouble("Enter mark 2: ");

            Student student = Student.builder()
                    .id(id)
                    .name(name)
                    .mark1(mark1)
                    .mark2(mark2)
                    .build();
            log.info("User entered student: {}", student);
            students.add(student);
            System.out.println("Añadido: " + student);
        }

        return Collections.unmodifiableList(students);
    }
}