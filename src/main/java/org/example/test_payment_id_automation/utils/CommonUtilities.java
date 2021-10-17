package org.example.test_payment_id_automation.utils;

import com.razorpay.Invoice;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Setter;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Setter
public class CommonUtilities {
    String razorpayKey ="";
    String razorpaySecret ="";

    public String generateInvoiceUrl() throws RazorpayException {
        RazorpayClient razorpayClient = new RazorpayClient(razorpayKey, razorpaySecret);
        String receiptNumber = String.valueOf(System.currentTimeMillis());

        JSONObject customer = new JSONObject();
        customer.put("email", "user@example.com");

        JSONObject request = new JSONObject();
        request.put("customer", customer);
        request.put("amount", "10000"); // Amount in paise
        request.put("description", "Example slash Demo");
        request.put("currency", "INR");
        request.put("sms_notify", "0");
        request.put("receipt", receiptNumber);
        request.put("type", "link");

        Invoice invoice = razorpayClient.Invoices.create(request);
        return invoice.get("short_url");
    }

    public String performPayment() throws InterruptedException, RazorpayException {
        String url = generateInvoiceUrl();

        // Is payment Url generated?
        if(url == null) {
            throw new RazorpayException("FAILED TO GENERATE URL");
        }

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get(url);

        Thread.sleep(5000);

        // Change the focus to iFrame
        driver.switchTo().frame(0);

        WebDriverWait wait = new WebDriverWait(driver, 5);

        // Enter PhoneNumber
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("contact")));
        driver.findElement(By.id("contact")).sendKeys("1234567890");

        // Enter Email
        driver.findElement(By.id("email")).sendKeys("user@example.com");

        // Click on Proceed
        driver.findElement(By.id("footer")).click();
        Thread.sleep(2000);

        // Click on NetBanking
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(),'Netbanking')]")));
        driver.findElement(By.xpath("//div[contains(text(),'Netbanking')]")).click();

        // Click on SBI Bank
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(),'SBI')]")));
        driver.findElement(By.xpath("//div[contains(text(),'SBI')]")).click();

        // Click on Pay
        driver.findElement(By.id("footer")).click();
        Thread.sleep(5000);

        // Need Window Handler, switch to Window
        String parentWindow=driver.getWindowHandle();
        Set<String> allWindows=driver.getWindowHandles();
        allWindows.remove(parentWindow);

        allWindows.forEach( window -> {
            driver.switchTo().window(window);
            if(driver.findElement(By.xpath("/html/body/form/button[1]")).isDisplayed()) {
                // Click on Success
                driver.findElement(By.xpath("/html/body/form/button[1]")).click();
            }
        });

        // Switch back to parent window
        driver.switchTo().window(parentWindow);

        // Collect PaymentId from the page
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"scs-msg\"]/div[2]")));
        String paymentId = driver.findElement(By.xpath("//*[@id=\"scs-msg\"]/div[2]")).getText().replace("Payment ID: ", "");
        System.out.println("PaymentID = " + paymentId);

        driver.quit();

        return paymentId;
    }
}
