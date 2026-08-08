package pages;

import java.io.File;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonActions {

    private static final Logger logger =
            LoggerFactory.getLogger(CommonActions.class);

    private final WebDriver driver;
    private final WebDriverWait wait;

    public CommonActions(WebDriver driver) {

        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    }

    public void click(By locator) {

        logger.info("Clicking element : {}", locator);

        wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        ).click();

    }

    public void uploadFile(By uploadArea,
                           By fileInput,
                           String filePath) {

        logger.info("Clicking upload area");

        wait.until(
                ExpectedConditions.elementToBeClickable(uploadArea)
        ).click();

        File file = new File(filePath);

        if (!file.exists()) {
            throw new IllegalArgumentException(
                    "File not found : " + file.getAbsolutePath());
        }

        logger.info("Uploading file : {}", file.getAbsolutePath());

        WebElement uploadElement =
                wait.until(
                        ExpectedConditions.presenceOfElementLocated(fileInput)
                );

        uploadElement.sendKeys(file.getAbsolutePath());

        logger.info("File uploaded successfully");
    }

}