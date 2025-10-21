package com.javmb.studentscli;

import com.javmb.studentscli.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class StudentsCliApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentsCliApplication.class, args);
    }

}
