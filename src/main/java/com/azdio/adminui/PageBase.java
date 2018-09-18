package com.azdio.adminui;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.*;


public abstract class PageBase {

    public static WebDriver driver;

    @Before
    public   void openBrowser() {
        Config config = new Config();

        String browser = config.getConfigProperty("browser");

        if ("ch".equals(browser)) {
            System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
            ChromeOptions chromeOptions = new ChromeOptions();
            String isHeadless = config.getConfigProperty("isHeadless").toUpperCase();
            if (isHeadless.equals("YES")) {
                chromeOptions.addArguments("--headless");
            }
            driver = new ChromeDriver(chromeOptions);
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        } else if ("ff".equals(browser)) {
        } else if ("sf".equals(browser)) {
        } else {
        }

    }
}