package com.javmb.studentscli.util.lib;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public final class Fechas {
    // Formateador estándar (inmutable y thread-safe)
    private static final DateTimeFormatter FORMATO_BARRAS = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Constructor privado para evitar instanciación
    private Fechas() {}

    /**
     * Convierte un String en formato "dd/MM/yyyy" a LocalDate.
     * @param fechaStr String con la fecha en formato "dd/MM/yyyy"
     * @return LocalDate representando la fecha
     * @throws IllegalArgumentException si el formato es inválido o la fecha es nula
     */
    public static LocalDate parsearFecha(String fechaStr) {
        Objects.requireNonNull(fechaStr, "La fecha no puede ser nula");
        try {
            return LocalDate.parse(fechaStr, FORMATO_BARRAS);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Use dd/MM/yyyy", e);
        }
    }

    /**
     * Formatea una LocalDate a String con formato "dd/MM/yyyy".
     * @param fecha LocalDate a formatear
     * @return String con la fecha formateada
     * @throws IllegalArgumentException si la fecha es nula
     */
    public static String formatearFecha(LocalDate fecha) {
        Objects.requireNonNull(fecha, "La fecha no puede ser nula");
        return fecha.format(FORMATO_BARRAS);
    }

    /**
     * Calcula la edad en años a partir de una fecha de nacimiento.
     * @param fechaNacimientoStr Fecha de nacimiento en formato "dd/MM/yyyy"
     * @return Edad en años completos
     * @throws IllegalArgumentException si la fecha es inválida, nula o futura
     */
    public static int calcularEdad(String fechaNacimientoStr) {
        LocalDate fechaNacimiento = parsearFecha(fechaNacimientoStr);
        LocalDate hoy = LocalDate.now();

        if (fechaNacimiento.isAfter(hoy)) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser futura");
        }

        return Period.between(fechaNacimiento, hoy).getYears();
    }

    /**
     * Suma días a una fecha dada.
     * @param fechaStr Fecha inicial en formato "dd/MM/yyyy"
     * @param dias Número de días a sumar (puede ser negativo)
     * @return Nueva fecha en formato "dd/MM/yyyy"
     */
    public static String sumarDias(String fechaStr, long dias) {
        LocalDate fecha = parsearFecha(fechaStr);
        return formatearFecha(fecha.plusDays(dias));
    }

    /**
     * Verifica si un String representa una fecha válida en formato "dd/MM/yyyy".
     * @param fechaStr String a validar
     * @return true si es válida, false en caso contrario
     */
    public static boolean esFechaValida(String fechaStr) {
        try {
            parsearFecha(fechaStr);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Compara dos fechas.
     * @param fecha1Str Primera fecha en formato "dd/MM/yyyy"
     * @param fecha2Str Segunda fecha en formato "dd/MM/yyyy"
     * @return -1 si fecha1 es anterior, 0 si son iguales, 1 si fecha1 es posterior
     */
    public static int compararFechas(String fecha1Str, String fecha2Str) {
        LocalDate fecha1 = parsearFecha(fecha1Str);
        LocalDate fecha2 = parsearFecha(fecha2Str);
        return fecha1.compareTo(fecha2);
    }

    /**
     * Obtiene la fecha actual en formato "dd/MM/yyyy".
     * @return String con la fecha actual
     */
    public static String hoy() {
        return LocalDate.now().format(FORMATO_BARRAS);
    }

    /**
     * Calcula los días entre dos fechas.
     * @param fechaInicioStr Fecha inicial en formato "dd/MM/yyyy"
     * @param fechaFinStr Fecha final en formato "dd/MM/yyyy"
     * @return Número de días entre las fechas
     */
    public static long diasEntre(String fechaInicioStr, String fechaFinStr) {
        LocalDate inicio = parsearFecha(fechaInicioStr);
        LocalDate fin = parsearFecha(fechaFinStr);
        return Math.abs(fin.toEpochDay() - inicio.toEpochDay());
    }

    /**
     * Verifica si una fecha está entre dos fechas dadas (inclusive).
     * @param fechaStr Fecha a verificar en formato "dd/MM/yyyy"
     * @param inicioStr Fecha inicial en formato "dd/MM/yyyy"
     * @param finStr Fecha final en formato "dd/MM/yyyy"
     * @return true si la fecha está en el rango
     */
    public static boolean estaEnRango(String fechaStr, String inicioStr, String finStr) {
        LocalDate fecha = parsearFecha(fechaStr);
        LocalDate inicio = parsearFecha(inicioStr);
        LocalDate fin = parsearFecha(finStr);

        return !fecha.isBefore(inicio) && !fecha.isAfter(fin);
    }
}