package com.sg.jdbctesting.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author naris
 */
@SpringBootApplication
public class App implements CommandLineRunner {
    
//    @Autowired
//    MeetingController controller;

    public static void main(String args[]) {
        SpringApplication.run(App.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
        //no controller
    }
    
}