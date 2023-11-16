package com.example.Selenium.Package02;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CheckHostAD implements Runnable {
    private WebDriver driver;
    private WebDriverWait wait;
    private List<WebElement> element_solve1;
    private List<WebElement> element_solve2;

    private CountDownLatch countDownLatch;

    public CheckHostAD(WebDriver driver, WebDriverWait wait, CountDownLatch countDownLatch) {
        this.driver = driver;
        this.wait = wait;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try { // quảng cáo host 8 9
            System.out.println("Xem xét quảng cáo của host ");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[contains(@style,'width: 100vw')]")));
            element_solve1 = driver.findElements(By.xpath("//iframe[contains(@style,'width: 100vw')]"));
            if (element_solve1.size() > 0 && element_solve1.get(0).isDisplayed()) {
                System.out.println("Quảng của host có hiển thị");
                WebElement frame = driver.findElement(By.xpath("//iframe[contains(@style,'width: 100vw')]"));
                driver.switchTo().frame(frame);
                element_solve1 = driver.findElements(By.xpath("//div[@aria-label='Đóng quảng cáo']"));
                element_solve2 = driver.findElements(By.xpath("//div[@id='dismiss-button']"));
                if (element_solve1.size() > 0 && element_solve1.get(0).isDisplayed()) {
                    System.out.println("Hiển thị //div[@aria-label='Đóng quảng cáo']");
                    driver.findElement(By.xpath("//div[@aria-label='Đóng quảng cáo']")).click();
                    System.out.println("Đã tắt host - //div[@aria-label='Đóng quảng cáo']");
                } else if (element_solve2.size() > 0 && element_solve2.get(0).isDisplayed()) {
                    System.out.println("Hiển thị //div[@id='dismiss-button']");
                    driver.findElement(By.xpath("//div[@id='dismiss-button']")).click();
                    System.out.println("Đã tắt host - //div[@id='dismiss-button']");
                } else {
                    System.out.println("không có đống nào ở trên hiển thị phần tử ?");
                    /**
                     * test code
                     */
                    driver.switchTo().defaultContent(); // return default content
                    System.out.println("chuẩn bị click dismis button");
                    driver.findElement(By.xpath("//*[@id=\"dismiss-button\"]")).click();
                    System.out.println("thành công click dismis button");

                }
                driver.switchTo().defaultContent(); // return default content
                System.out.println("Return to default succes");
            }
            countDownLatch.countDown();
        } catch (Exception exception) {
            countDownLatch.countDown();
            System.out.println("Không hiển thị quảng cáo của host ");
            System.out.println(exception);
        }
    }
}

