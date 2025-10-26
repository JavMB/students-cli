package com.javmb.studentscli.shell;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jline.reader.LineReader;
import org.springframework.stereotype.Component;

/**
 * Servicio para iniciar el modo Shell interactivo.
 * Permite ejecutar comandos en un REPL (Read-Eval-Print Loop).
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ShellModeService {

    private final StudentsShellCommands shellCommands;
    private final LineReader lineReader;

    /**
     * Inicia el modo Shell interactivo.
     * El usuario puede ejecutar comandos hasta que escriba 'exit' o presione Ctrl+C.
     */
    public void startInteractiveMode() {
        printWelcome();

        try {
            while (true) {
                try {
                    String line = lineReader.readLine("students-cli:> ");

                    if (line == null || line.trim().equalsIgnoreCase("exit") || line.trim().equalsIgnoreCase("quit")) {
                        System.out.println("Saliendo del modo shell...\n");
                        break;
                    }

                    if (line.trim().isEmpty()) {
                        continue;
                    }


                    String result = processCommand(line.trim());
                    if (result != null && !result.isEmpty()) {
                        System.out.println(result);
                    }

                } catch (Exception e) {
                    log.debug("Error ejecutando comando: {}", e.getMessage());
                    System.out.println("[ ERROR ] " + e.getMessage());
                    System.out.println("Usa 'help' para ver los comandos disponibles\n");
                }
            }
        } catch (Exception e) {
            log.error("Error en modo shell interactivo", e);
            System.out.println("\nSaliendo del modo shell...\n");
        }
    }

    private String processCommand(String line) {
        String[] parts = line.split("\\s+", 2);
        String command = parts[0].toLowerCase();
        String args = parts.length > 1 ? parts[1] : "";

        return switch (command) {
            case "add-students" -> shellCommands.addStudents();
            case "create-averages" -> shellCommands.createAverages();
            case "list-students" -> shellCommands.listStudents();
            case "load-config" -> {
                if (args.isEmpty()) {
                    yield "[ ERROR ] Falta el parámetro <path>. Uso: load-config <path>";
                }
                yield shellCommands.loadConfig(args.trim());
            }
            case "export-config" -> {
                if (args.isEmpty()) {
                    yield "[ ERROR ] Falta el parámetro <path>. Uso: export-config <path>";
                }
                yield shellCommands.exportConfig(args.trim());
            }
            case "help" -> getHelpMessage(args.trim());
            default -> "[ ERROR ] Comando no reconocido: '" + command + "'. Usa 'help' para ver comandos disponibles.";
        };
    }

    private String getHelpMessage(String specificCommand) {
        if (specificCommand.isEmpty()) {
            return """
                
                COMANDOS DISPONIBLES:
                ===============================================================
                add-students          - Añadir estudiantes y notas
                create-averages       - Crear archivo con medias
                list-students         - Listar estudiantes con medias
                load-config <path>    - Cargar configuración desde archivo
                export-config <path>  - Exportar configuración a archivo
                help [comando]        - Mostrar esta ayuda o ayuda de un comando
                exit / quit           - Salir del modo shell
                ===============================================================
                """;
        }

        return switch (specificCommand) {
            case "add-students" -> """
                add-students
                  Permite añadir estudiantes con sus notas de forma interactiva.
                  El sistema solicitará ID, nombre y notas de cada estudiante.
                """;
            case "create-averages" -> """
                create-averages
                  Crea un archivo XML con las notas promedio de los estudiantes.
                  Lee el archivo students.xml y genera students_with_average.xml
                """;
            case "list-students" -> """
                list-students
                  Muestra en pantalla todos los estudiantes con sus notas promedio.
                  Lee los datos del archivo students_with_average.xml
                """;
            case "load-config" -> """
                load-config <path>
                  Carga la configuración desde un archivo.
                  
                  Parámetros:
                    path - Ruta del archivo de configuración (.properties, .yaml, .yml)
                  
                  Ejemplo:
                    load-config data/config.yaml
                """;
            case "export-config" -> """
                export-config <path>
                  Exporta la configuración actual a un archivo.
                  
                  Parámetros:
                    path - Ruta donde guardar la configuración
                  
                  Ejemplo:
                    export-config output/my-config.yaml
                """;
            default -> "[ ERROR ] Comando no encontrado: '" + specificCommand + "'. Usa 'help' para ver todos los comandos.";
        };
    }

    private void printWelcome() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("MODO SHELL INTERACTIVO ACTIVADO");
        System.out.println("=".repeat(60));
        System.out.println("Comandos disponibles:");
        System.out.println("  * add-students          - Añadir estudiantes y notas");
        System.out.println("  * create-averages       - Crear archivo con medias");
        System.out.println("  * list-students         - Listar estudiantes con medias");
        System.out.println("  * load-config <path>    - Cargar configuración");
        System.out.println("  * export-config <path>  - Exportar configuración");
        System.out.println("  * help                  - Ver ayuda de todos los comandos");
        System.out.println("  * help <comando>        - Ver ayuda de un comando específico");
        System.out.println("  * exit / quit           - Salir del modo shell");
        System.out.println("=".repeat(60));
        System.out.println("Tip: Usa TAB para autocompletar comandos");
        System.out.println("Tip: Usa las flechas para navegar por el historial\n");
    }
}

