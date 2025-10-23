package com.javmb.studentscli.util;

import com.javmb.studentscli.config.Config;
import com.javmb.studentscli.exception.InvalidMarkException;
import com.javmb.studentscli.util.lib.IO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InputValidator {

    private final Config config;

    /**
     * Lee una nota validando contra los límites de Config.
     */
    public double readMark(String message) {
        double mark = IO.readDouble(message);

        if (!config.getValidation().isEnabled()) {
            return mark;
        }

        double min = config.getMark().getMin();
        double max = config.getMark().getMax();

        boolean minValid = min < 0 || mark >= min;
        boolean maxValid = max < 0 || mark <= max;

        if (minValid && maxValid) {
            return mark;
        }

        throw new InvalidMarkException(String.format("La nota %.2f debe estar entre %.2f y %.2f", mark, min, max));
    }

    public String getMarkRangeInfo() {
        if (!config.getValidation().isEnabled()) {
            return "Validación de notas desactivada.";
        }

        double min = config.getMark().getMin();
        double max = config.getMark().getMax();

        return String.format("Notas válidas: entre %.2f y %.2f", min, max);
    }
}
