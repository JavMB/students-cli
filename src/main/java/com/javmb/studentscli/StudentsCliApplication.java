package com.javmb.studentscli;

import com.javmb.studentscli.config.Config;
import com.javmb.studentscli.util.lib.ConsoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class StudentsCliApplication implements CommandLineRunner {
    @Autowired
    private Config config;

    public static void main(String[] args) {
        SpringApplication.run(StudentsCliApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {



    }
}
