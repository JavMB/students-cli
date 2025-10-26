package com.javmb.studentscli.shell;

import com.javmb.studentscli.model.Student;
import com.javmb.studentscli.service.input.StudentInputService;
import com.javmb.studentscli.ui.handlers.*;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

/**
 * Comandos de Spring Shell para gestión de estudiantes.
 * Proporciona una interfaz de línea de comandos interactiva.
 */
@ShellComponent
@RequiredArgsConstructor
public class StudentsShellCommands {

    private final AddStudentsViaDomHandler addStudentsHandler;
    private final CreateFileWithAverageMarksHandler avgMarksHandler;
    private final ListStudentsAndAveragesHandler listStudentsHandler;
    private final LoadUserConfigHandler loadUserConfigHandler;
    private final ExportUserConfigHandler exportUserConfigHandler;
    private final StudentInputService studentInputService;

    @ShellMethod(key = "add-students", value = "Add students and marks via DOM")
    public String addStudents() {
        List<Student> students = studentInputService.getStudentsFromInput();
        if (students.isEmpty()) {
            return "[ ERROR ] No se añadieron estudiantes";
        }

        if (addStudentsHandler.handle(students)) {
            return "[ OK ] " + students.size() + " estudiantes guardados correctamente";
        } else {
            return "[ ERROR ] Error al guardar estudiantes";
        }
    }

    @ShellMethod(key = "create-averages", value = "Create file with average marks")
    public String createAverages() {
        if (avgMarksHandler.handle()) {
            return "[ OK ] Archivo con medias creado correctamente";
        } else {
            return "[ ERROR ] Error al crear archivo con medias";
        }
    }

    @ShellMethod(key = "list-students", value = "List students with average marks on screen")
    public String listStudents() {
        List<Student> students = listStudentsHandler.handle();
        if (students.isEmpty()) {
            return "[ ERROR ] No hay estudiantes para mostrar";
        }

        StringBuilder result = new StringBuilder("\nListado de estudiantes:\n");
        result.append("-".repeat(50)).append("\n");
        students.forEach(student -> result.append("  ").append(student).append("\n"));
        result.append("-".repeat(50));
        result.append("\n[ OK ] Total: ").append(students.size()).append(" estudiantes");
        return result.toString();
    }

    @ShellMethod(key = "load-config", value = "Load configuration from file")
    public String loadConfig(
            @ShellOption(help = "Path to configuration file") String path) {
        if (loadUserConfigHandler.handle(path)) {
            return "[ OK ] Configuración cargada correctamente desde: " + path;
        } else {
            return "[ ERROR ] Error al cargar la configuración desde: " + path;
        }
    }

    @ShellMethod(key = "export-config", value = "Export current configuration to file")
    public String exportConfig(
            @ShellOption(help = "Path to save configuration file") String path) {
        if (exportUserConfigHandler.handle(path)) {
            return "[ OK ] Configuración exportada correctamente a: " + path;
        } else {
            return "[ ERROR ] Error al exportar la configuración a: " + path;
        }
    }

    @ShellMethod(key = "menu", value = "Return to main menu (exits shell mode)")
    public String exitShell() {
        return "Saliendo del modo shell... (usa Ctrl+C o escribe 'exit')";
    }
}

