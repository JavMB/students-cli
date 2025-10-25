package com.javmb.studentscli.util;

import com.javmb.studentscli.config.Config;
import com.javmb.studentscli.exception.InvalidMarkException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InputValidator {

    private final Config config;

    /**
     * Valida si la nota está dentro de los límites configurados.
     *
     * @param mark nota a validar
     * @return true si la nota es válida, false en caso contrario
     */
    public boolean isValidMark(double mark) {
        if (!config.getValidation().isEnabled()) {
            return true;
        }
        double min = config.getMark().getMin();
        double max = config.getMark().getMax();
        boolean minValid = min < 0 || mark >= min;
        boolean maxValid = max < 0 || mark <= max;
        return minValid && maxValid;
    }

    public String getMarkRangeInfo() {
        if (!config.getValidation().isEnabled()) {
            return "Validación de notas desactivada.";
        }

        double min = config.getMark().getMin();
        double max = config.getMark().getMax();

        return String.format("Notas válidas: entre %.2f y %.2f", min, max);
    }

    /**
     * Valida si el nombre contiene al menos una letra.
     *
     * @param name nombre a validar
     * @return true si el nombre es válido, false en caso contrario
     */
    public boolean isValidName(String name) {
        // al menos una letra
        return name != null && name.matches(".*[a-zA-ZáéíóúÁÉÍÓÚñÑ].*");
    }
}
