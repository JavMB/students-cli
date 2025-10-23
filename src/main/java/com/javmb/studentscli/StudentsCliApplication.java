package com.javmb.studentscli;


import com.javmb.studentscli.ui.MenuManager;
import com.javmb.studentscli.util.lib.ConsoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void run(String... args) throws Exception {
        menuManager.showMainMenu();


    }
}
