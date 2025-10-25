package com.javmb.studentscli.config;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
@Data
public class Config {
    private Mark mark;
    private Files files;

    @Data
    public static class Mark {
        @Min(0)
        private double min = 0.0;
        @Max(10)
        private double max = 10.0;
    }

    @Data
    public static class Files {
        private String studentsCsv = "data/students.csv";
        private String studentsXml = "data/students.xml";
        private String studentsAvgXml = "data/students_with_average.xml";
    }
}