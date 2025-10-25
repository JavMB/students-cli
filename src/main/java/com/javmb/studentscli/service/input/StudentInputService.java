package com.javmb.studentscli.service.input;

import com.javmb.studentscli.exception.InvalidMarkException;
import com.javmb.studentscli.model.Student;
import com.javmb.studentscli.util.InputValidator;
import com.javmb.studentscli.util.lib.IO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentInputService {
    private static final int EXIT_ID = 0;
    private final InputValidator inputValidator;

    public List<Student> getStudentsFromInput() {
        List<Student> students = new ArrayList<>();
        printHeader();
        while (true) {
            int id = IO.readInt("ID del estudiante (0 para terminar): ");
            if (id == EXIT_ID) break;
            String name = readStudentName();
            Student student = getStudentFromInput(id, name);
            if (student != null) {
                students.add(student);
                printSuccess("Añadido: " + student);
            }
        }
        return Collections.unmodifiableList(students);
    }

    /**
     * Crea un objeto Student a partir de los datos introducidos por el usuario.
     *
     * @param id   identificador del estudiante
     * @param name nombre del estudiante
     * @return objeto Student o null si las notas no son válidas
     */
    public Student getStudentFromInput(int id, String name) {
        try {
            double mark1 = readAndValidateMark("Nota 1er trimestre: ");
            double mark2 = readAndValidateMark("Nota 2do trimestre: ");
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

    private double readAndValidateMark(String message) {
        double mark = IO.readDouble(message);
        if (!inputValidator.isValidMark(mark)) {
            throw new InvalidMarkException(String.format("La nota %.2f no está en el rango permitido. %s", mark, inputValidator.getMarkRangeInfo()));
        }
        return mark;
    }

    private String readStudentName() {
        while (true) {
            String name = IO.readNonEmptyString("Nombre completo: ").trim();
            if (inputValidator.isValidName(name)) {
                return name;
            }
            printError("El nombre no puede ser solo números. Intenta de nuevo.");
        }
    }

    private String readStudentConfigPath() {
        return IO.readNonEmptyString("Ruta del archivo de configuración: ").trim();
    }

    private void printHeader() {
        System.out.println("\n=== Añadir Estudiantes ===");
        System.out.println(inputValidator.getMarkRangeInfo());
        System.out.println();
    }

    private void printSuccess(String message) {
        System.out.println("[ * ] " + message);
    }

    private void printError(String message) {
        System.out.println("[ x ] " + message);
    }
}
