package com.javmb.studentscli.util;

import com.javmb.studentscli.config.Config;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentsInputValidator {

    private final Config config;

    /**
     * Valida si la nota está dentro de los límites configurados.
     *
     * @param mark nota a validar
     * @return true si la nota es válida, false en caso contrario
     */
    public boolean isValidMark(double mark) {
        double min = config.getMark().getMin();
        double max = config.getMark().getMax();
        return mark >= min && mark <= max;
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
