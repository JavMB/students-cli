package com.javmb.studentscli;


import com.javmb.studentscli.ui.MenuManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentsCliApplication implements CommandLineRunner {


    private final MenuManager menuManager;

    public StudentsCliApplication(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    public static void main(String[] args) {
        SpringApplication.run(StudentsCliApplication.class, args);
    }

    @Override
    public void run(String... args) {
        menuManager.showMainMenu();
    }
}
