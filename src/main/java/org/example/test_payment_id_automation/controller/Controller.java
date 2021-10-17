package org.example.test_payment_id_automation.controller;

import com.razorpay.RazorpayException;
import org.example.test_payment_id_automation.utils.CommonUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Controller {
    @Autowired
    Environment environment;

    @GetMapping("/get_valid_payment_id")
    public String getValidPaymentID() throws InterruptedException, RazorpayException {
        CommonUtilities commonUtilities = new CommonUtilities();
        commonUtilities.setRazorpayKey(environment.getProperty("razorpay.client.key"));
        commonUtilities.setRazorpaySecret(environment.getProperty("razorpay.client.secret"));

        return commonUtilities.performPayment();
    }

    @GetMapping("/get_invalid_payment_id")
    public String getInValidPaymentID() {
        return "INVALID_PAYMENT_ID";
    }
}
