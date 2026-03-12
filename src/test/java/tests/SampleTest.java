package tests;


import org.openqa.selenium.WebDriver;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Pages.Basepage;

public class SampleTest extends Basepage {
    private WebDriver driver;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        // initialize Basepage driver if not already initialized
        super.setUp();
        driver = Basepage.driver;
    }

    @Test(groups = {"google", "ui"})
    public void openGoogle() {
        driver.get("https://google.com");
        String title = driver.getTitle();
        System.out.println("Page title: " + title);
        Assert.assertTrue(title.toLowerCase().contains("google"), "Expected title to contain 'Google' but was: " + title);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
