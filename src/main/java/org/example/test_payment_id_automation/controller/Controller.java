package org.example.test_payment_id_automation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Controller {
    @GetMapping("/get_valid_payment_id")
    public String getValidPaymentID() {
        return "";
    }

    @GetMapping("/get_invalid_payment_id")
    public String getInValidPaymentID() {
        return "INVALID_PAYMENT_ID";
    }
}
