package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {

    private ScreenshotUtils() {
    }

    public static String captureScreenshot(WebDriver driver,
                                           String testName) {

        String timestamp =
                new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")
                        .format(new Date());

        String screenshotDir =
                System.getProperty("user.dir")
                        + File.separator
                        + "Reports"
                        + File.separator
                        + "Screenshots";

        new File(screenshotDir).mkdirs();

        String destination =
                screenshotDir
                        + File.separator
                        + testName
                        + "_"
                        + timestamp
                        + ".png";

        File source =
                ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.FILE);

        try {

            FileUtils.copyFile(source, new File(destination));

        } catch (IOException e) {

            e.printStackTrace();
        }

        return destination;
    }
}