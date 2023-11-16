package com.example.Selenium.Package02;

import com.example.Selenium.Package01.Check_ESC;
import com.example.Selenium.Package01.Check_HandAD;
import com.example.Selenium.Package01.Check_HostAD;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@RestController
@RequestMapping("/api/web")
public class Selenium {
    @GetMapping("/ttsfree_captcha_noForLoop_thread2")
    public ResponseEntity<?> ttsfree_captcha_noForLoop_Threads(@RequestParam Map<String, String> params) throws InterruptedException, IOException {
        WebDriverWait wait;
        List<WebElement> element_solve;
        String URL_WEBSITE = "https://ttsfree.com/vn";
        String user_name = "nam02test";
        String user_password = "IUtrpangmaimai02";
        String male_voice = "//*[@id=\"voice_name_bin\"]/div[2]/label";
        String female_voice = "//*[@id=\"voice_name_bin\"]/div[1]/label";
        String Vietnamese = "138";
        String xpath_vietnameseToText = "138. Vietnamese (Vietnam) - VN";
        JavascriptExecutor js;
        WebElement Element_inputText;
        String windowHandle;
        String string;
        File chosenFile = null;
        WebElement webElement;

        System.setProperty("webdriver.http.factory", "jdk-http-client");
        System.setProperty("webdriver.chrome.driver", "E:\\CongViecHocTap\\ChromeDriver\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("useAutomationExtension", false); // disable chrome running as automation
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation")); // disable chrome running as automation

        WebDriver driver = new ChromeDriver(options);

        wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        CountDownLatch latch = new CountDownLatch(2);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8)); // số giây mà 1 driver chờ để load 1 phần tử nếu không có thiết lập wait
        driver.manage().window().maximize();

        driver.get("https://ttsfree.com/vn");

        driver.findElement(By.xpath("(//a[@class='link mr-20 color-heading ml-10'])[1]")).click();

        Thread threadHostAD = new Thread(new CheckHostAD(driver, wait, latch));
        Thread threadCheckESC = new Thread(new CheckESC(driver, wait, latch));

        threadHostAD.start();
        threadCheckESC.start();

        try {
            latch.await();
        } catch (Exception e) {
            System.out.println(e);
        }

        driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys(user_name);
        driver.findElement(By.xpath("//input[@placeholder='Enter password']")).sendKeys(user_password);
        driver.findElement(By.xpath("//ins[@class='iCheck-helper']")).click();

        driver.findElement(By.xpath("//input[@id='btnLogin']")).click();

        element_solve = driver.findElements(By.xpath("//*[@id=\"frm_login\"]/div[2]/div/font"));
        if (element_solve.size() > 0 && element_solve.get(0).isDisplayed()) {
            webElement = driver.findElement(By.xpath("//*[@id=\"frm_login\"]/div[2]/div/font"));
            System.out.println(webElement.getText());
            Thread.sleep(3000);
            driver.close();
            Thread.sleep(3000);
            return ResponseEntity.ok(new String(webElement.getText()));

        }


        System.out.println("---------------------------------------------------------------------------------------");
        String text = params.get("Text");
        String voice = params.get("Voice");
        String fileName = params.get("FileName");
        System.out.println(text + " " + voice + " " + fileName);

        js = (JavascriptExecutor) driver; // work

        Element_inputText = driver.findElement(By.xpath("//*[@id=\"input_text\"]"));
        js.executeScript("arguments[0].scrollIntoView();", Element_inputText);

        driver.findElement(By.xpath("//textarea[@id='input_text']")).sendKeys(text);

        return ResponseEntity.ok(new String("END GAME"));

    }
}
