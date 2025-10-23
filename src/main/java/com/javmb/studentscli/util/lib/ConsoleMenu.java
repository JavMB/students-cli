package com.javmb.studentscli.util.lib;



import java.util.ArrayList;
import java.util.List;


/**
 * Framework ligero para crear menús de consola interactivos con opciones y sub‑menús.
 *
 * <p>Permite definir rápidamente la estructura de un menú y encadenar sub‑menús de forma
 * fluida, manteniendo la legibilidad y evitando código repetido.
 *
 * <p><strong>Ejemplo de uso:</strong>
 * <pre>{@code
 * // Definición de un submenú de consultas
 * ConsoleMenu consultas = new ConsoleMenu("CONSULTAS")
 *     .addOption("Ver empleado",    this::verEmpleado)
 *     .addOption("Listado hijos",   this::listarHijos);
 *
 * // Menú principal con submenú
 * new ConsoleMenu("GESTIÓN EMPLEADOS")
 *     .addOption("Nuevo empleado",   this::nuevoEmpleado)
 *     .addOption("Nuevo hijo",       this::nuevoHijo)
 *     .addOption("Modificar sueldo", this::modificarSueldo)
 *     .addOption("Borrar empleado",  this::borrarEmpleado)
 *     .addOption("Borrar hijo",      this::borrarHijo)
 *     .addSubMenu("Consultas",       consultas)
 *     .show();
 * }</pre>
 *
 * @author Javier
 * @version 1.0
 * @since 2025-04-21
 */
public class ConsoleMenu {
    private final String title;
    private final boolean showExitOption;
    private final List<MenuItem> items = new ArrayList<>();

    /**
     * Crea un menú.
     *
     * @param title          Texto que aparecerá en la cabecera
     * @param showExitOption true para incluir la opción 0 = Salir
     */
    public ConsoleMenu(String title, boolean showExitOption) {
        this.title = title;
        this.showExitOption = showExitOption;
    }

    /**
     * Crea un menú que muestra la opción 0 = Salir por defecto.
     *
     * @param title Texto que aparecerá en la cabecera
     */
    public ConsoleMenu(String title) {
        this(title, true);
    }

    /**
     * Añade una opción que ejecuta una acción Runnable.
     *
     * @param text   Texto de la opción
     * @param action Acción a ejecutar al seleccionar
     * @return este menú (para encadenar llamadas)
     */
    public ConsoleMenu addOption(String text, Runnable action) {
        items.add(new MenuItem(text, action));
        return this;
    }

    /**
     * Añade una sub‑opción que muestra otro ConsoleMenu.
     *
     * @param text    Texto de la opción
     * @param submenu Menú que se mostrará al elegir esta opción
     * @return este menú (para encadenar llamadas)
     */
    public ConsoleMenu addSubMenu(String text, ConsoleMenu submenu) {
        return addOption(text, submenu::show);
    }

    /**
     * Muestra el menú y procesa la selección hasta que se salga (si está habilitado).
     */
    public void show() {
        while (true) {
            printHeader();
            printOptions();
            int min = showExitOption ? 0 : 1;
            int max = items.size();
            int choice = IO.readInt("Elige una opción: ", min, max);
            if (showExitOption && choice == 0) {
                System.out.println("Saliendo…");
                break;
            }
            // Ajustar índice (1-based en pantalla → 0-based en lista)
            items.get(choice - 1).action.run();
        }
    }

    // Imprime la cabecera con asteriscos centrados
    private void printHeader() {
        String line = "* " + title + " *";
        String border = "*".repeat(line.length());
        System.out.println();
        System.out.println(border);
        System.out.println(line);
        System.out.println(border);
    }

    // Imprime cada opción numerada
    private void printOptions() {
        for (int i = 0; i < items.size(); i++) {
            System.out.printf("%2d. %s%n", i + 1, items.get(i).text);
        }
        System.out.println("-".repeat(5 + title.length()));
        if (showExitOption) {
            System.out.println(" 0. Salir");
        }
    }

    // Clase interna para guardar texto y acción de cada opción
    private record MenuItem(String text, Runnable action) {
    }


}