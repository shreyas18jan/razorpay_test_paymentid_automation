package org.example.test_payment_id_automation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin("*")
public class MainController {
    public static void main(String args[]) {
        SpringApplication.run(MainController.class);
    }
}
