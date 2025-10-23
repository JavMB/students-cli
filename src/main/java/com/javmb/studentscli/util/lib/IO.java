package com.javmb.studentscli.util.lib;

import java.util.Scanner;

/**
 * <h2>IO</h2>
 * Utilidades minimalistas para leer datos por consola con validación automática.
 * <p>
 *     Cada método <b>pregunta en bucle</b> hasta que el usuario introduce un valor correcto;
 *     por eso nunca devuelve valores inválidos ni lanza <code>NumberFormatException</code>.
 * </p>
 * <p><b>Ejemplo rápido</b></p>
 * <pre>
 * int edad = IO.readInt("Edad:", 0, 120);
 * double peso = IO.readDouble("Peso (kg): ");
 * String nombre = IO.readNonEmptyString("Nombre: ");
 * boolean continuar = IO.readBoolean("¿Deseas continuar?");
 * </pre>
 * <p>
 *     Todas las funciones son <code>static</code>, de modo que la clase no se instancia.
 *     Internamente mantiene un único {@link Scanner} compartido para evitar fugas de recursos.
 * </p>
 */
public final class IO {

    private static final Scanner scanner = new Scanner(System.in);

    private IO() {}

    // ------------------------------------------------------------------
    //  ENTEROS
    // ------------------------------------------------------------------

    /**
     * Lee un número entero, repitiendo la pregunta hasta que el usuario escriba un valor válido.
     *
     * @param message texto que se muestra como prompt (no incluye salto de línea)
     * @return el valor entero introducido
     */
    public static int readInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Debes introducir un número entero válido.");
            }
        }
    }

    /**
     * Lee un entero y comprueba que esté dentro de un rango (ambos extremos incluidos).
     *
     * @param message texto de prompt
     * @param min     valor mínimo permitido
     * @param max     valor máximo permitido
     * @return entero dentro del rango [min, max]
     */
    public static int readInt(String message, int min, int max) {
        while (true) {
            int num = readInt(message);
            if (num >= min && num <= max) {
                return num;
            }
            System.out.println("Error: El número debe estar entre " + min + " y " + max);
        }
    }

    // ------------------------------------------------------------------
    //  DECIMALES (double)
    // ------------------------------------------------------------------

    /**
     * Lee un número decimal (<code>double</code>), validando la entrada.
     *
     * @param message prompt a mostrar
     * @return número decimal introducido
     */
    public static double readDouble(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Debes introducir un número decimal válido.");
            }
        }
    }

    /**
     * Lee un double dentro de un rango cerrado [min, max].
     *
     * @param message prompt
     * @param min     mínimo aceptable
     * @param max     máximo aceptable
     * @return número dentro del rango
     */
    public static double readDouble(String message, double min, double max) {
        while (true) {
            double num = readDouble(message);
            if (num >= min && num <= max) {
                return num;
            }
            System.out.println("Error: El número debe estar entre " + min + " y " + max);
        }
    }

    // ------------------------------------------------------------------
    //  STRINGS
    // ------------------------------------------------------------------

    /**
     * Lee una línea de texto (puede ser vacía).
     *
     * @param message prompt
     * @return la cadena introducida (sin recortar espacios)
     */
    public static String readString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    /**
     * Lee un texto asegurando que no esté vacío ni sean solo espacios.
     *
     * @param message prompt
     * @return cadena no vacía
     */
    public static String readNonEmptyString(String message) {
        while (true) {
            String input = readString(message);
            if (!input.trim().isEmpty()) {
                return input;
            }
            System.out.println("Error: No puedes dejar este campo vacío.");
        }
    }

    // ------------------------------------------------------------------
    //  CARÁCTER ÚNICO
    // ------------------------------------------------------------------

    /**
     * Lee un único carácter; si el usuario introduce más de uno, vuelve a preguntar.
     *
     * @param message prompt
     * @return carácter leído
     */
    public static char readChar(String message) {
        while (true) {
            String input = readString(message);
            if (input.length() == 1) {
                return input.charAt(0);
            }
            System.out.println("Error: Debes introducir exactamente un carácter.");
        }
    }

    // ------------------------------------------------------------------
    //  BOOLEANO (sí/no)
    // ------------------------------------------------------------------

    /**
     * Pregunta al usuario por una respuesta de tipo sí/no. Acepta 's'/'S' y 'n'/'N'.
     *
     * @param message mensaje mostrado antes de "(s/n):"
     * @return <code>true</code> si el usuario responde 's' (sí); <code>false</code> si responde 'n' (no)
     */
    public static boolean readBoolean(String message) {
        while (true) {
            String input = readString(message + " (s/n): ").toLowerCase();
            if (input.equals("s")) {
                return true;
            } else if (input.equals("n")) {
                return false;
            }
            System.out.println("Error: Debes introducir 's' o 'n'.");
        }
    }
}
