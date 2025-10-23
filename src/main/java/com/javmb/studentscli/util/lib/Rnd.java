package com.javmb.studentscli.util.lib;

import java.util.Random;

/**
 * Clase utilitaria para operaciones matemáticas y generación de números aleatorios.
 * Todos los métodos son estáticos para facilitar su uso.
 */
public class Rnd {
    private static final Random random = new Random();

    /**
     * Genera un número entero aleatorio dentro de un rango.
     *
     * @param min Valor mínimo (inclusive)
     * @param max Valor máximo (inclusive)
     * @return Número aleatorio entre min y max
     */
    public static int getInt(int min, int max) {
        // Asegurarse de que min no sea mayor que max
        if (min > max) {
            throw new IllegalArgumentException("El valor mínimo no puede ser mayor que el máximo.");
        }
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * Genera un número decimal aleatorio dentro de un rango.
     *
     * @param min Valor mínimo (inclusive)
     * @param max Valor máximo (exclusive)
     * @return Número aleatorio entre min y max
     */
    public static double getDouble(double min, double max) {
        if (min >= max) {
            throw new IllegalArgumentException("El valor mínimo debe ser menor que el máximo.");
        }
        return min + (max - min) * random.nextDouble();
    }

    /**
     * Genera un valor booleano aleatorio.
     *
     * @return true o false aleatoriamente
     */
    public static boolean getBoolean() {
        return random.nextBoolean();
    }

    /**
     * Redondea un número a una cantidad específica de decimales.
     * Nota: Puede tener imprecisiones debido a la aritmética de punto flotante.
     * Para alta precisión, considere usar BigDecimal.
     *
     * @param value    Número a redondear
     * @param decimals Número de decimales deseado (no negativo)
     * @return Valor redondeado
     * @throws IllegalArgumentException si decimals es negativo
     */
    public static double round(double value, int decimals) {
        if (decimals < 0) {
            throw new IllegalArgumentException("El número de decimales no puede ser negativo.");
        }
        double scale = Math.pow(10, decimals);
        return Math.round(value * scale) / scale;
    }

    /**
     * Calcula el factorial de un número de forma iterativa.
     *
     * @param n Número para calcular factorial (0-20)
     * @return Factorial de n
     * @throws IllegalArgumentException Si n es negativo o mayor que 20 (ya que long se desbordaría)
     */
    public static long factorial(int n) {
        if (n < 0 || n > 20) {
            throw new IllegalArgumentException("El factorial solo está definido y cabe en long para 0 ≤ n ≤ 20");
        }
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    /**
     * Verifica si un número es primo.
     *
     * @param number Número a verificar (se asume positivo)
     * @return true si es primo, false si no lo es
     */
    public static boolean isPrime(int number) {
        if (number <= 1) return false;
        if (number == 2) return true;
        if (number % 2 == 0) return false;
        // Solo necesitamos comprobar divisores impares hasta la raíz cuadrada
        for (int i = 3; i * i <= number; i += 2) {
            if (number % i == 0) return false;
        }
        return true;
    }
    // --- CÁLCULOS DE PRECIOS, DESCUENTOS E INCREMENTOS ---

    /**
     * Modifica un precio aplicando un cambio porcentual.
     *
     * @param precioOriginal El precio base. Debe ser >= 0.
     * @param porcentaje     Porcentaje de cambio: positivo para incremento (ej. 20 para +20%),
     *                       negativo para descuento (ej. -15 para 15% descuento).
     *                       Un descuento no puede resultar en precio negativo.
     * @return El nuevo precio.
     * @throws IllegalArgumentException Si los inputs son inválidos o el descuento es excesivo.
     */
    public static double aplicarCambioPorcentual(double precioOriginal, double porcentaje) {
        if (precioOriginal < 0) {
            throw new IllegalArgumentException("El precio original no puede ser negativo.");
        }
        // Si es un descuento (porcentaje < 0), no puede ser más del 100%
        if (porcentaje < -100) {
            throw new IllegalArgumentException("Un descuento no puede exceder el 100%.");
        }

        double cambio = precioOriginal * (porcentaje / 100.0);
        double precioFinal = precioOriginal + cambio;


        if (precioFinal < 0 && porcentaje < 0) {
            return 0.0;
        }
        return precioFinal;
    }


    /**
     * Calcula la diferencia porcentual entre un precio original y un precio final.
     *
     * @param precioOriginal El precio inicial. Debe ser > 0.
     * @param precioFinal    El precio final. Debe ser >= 0.
     * @return El cambio porcentual (positivo si es aumento, negativo si es disminución).
     * @throws IllegalArgumentException Si precioOriginal es <= 0 o precioFinal es < 0.
     */
    public static double calcularDiferenciaPorcentual(double precioOriginal, double precioFinal) {
        if (precioOriginal <= 0) {
            throw new IllegalArgumentException("El precio original debe ser positivo para calcular la diferencia porcentual.");
        }
        if (precioFinal < 0) {
            throw new IllegalArgumentException("El precio final no puede ser negativo.");
        }

        return ((precioFinal - precioOriginal) / precioOriginal) * 100.0;
    }

    /**
     * Extrae la cantidad de IVA de un precio total, dado el tipo de IVA aplicado.
     *
     * @param precioConIVA  Precio total con IVA incluido. Debe ser >= 0.
     * @param porcentajeIVA Tipo de IVA aplicado (ej. 21 para 21%). Debe ser > 0.
     * @return La porción del precioConIVA que corresponde al IVA.
     * @throws IllegalArgumentException Si los inputs son inválidos.
     */
    public static double extraerIVADePrecioTotal(double precioConIVA, double porcentajeIVA) {
        if (precioConIVA < 0) {
            throw new IllegalArgumentException("El precio con IVA no puede ser negativo.");
        }
        if (porcentajeIVA <= 0) {
            throw new IllegalArgumentException("El porcentaje de IVA debe ser positivo.");
        }
        // IVA = PrecioConIVA * (porcentajeIVA / (100 + porcentajeIVA))
        return precioConIVA * (porcentajeIVA / (100.0 + porcentajeIVA));
    }


}