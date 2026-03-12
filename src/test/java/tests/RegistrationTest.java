package tests;

import Pages.Basepage;
import Pages.RegisterPage;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RegistrationTest extends Basepage {
    private WebDriver driver;
    RegisterPage registerPage;

    @BeforeClass(alwaysRun = true)
    public void init() {
        // ensure the Basepage has initialized the driver
        setUp();
        driver = Basepage.driver;
        registerPage = new RegisterPage(driver);
    }

    @Test(groups = {"smoke", "regression"})
    public void testRegistration() {
        String regUrl = Basepage.navigateTo("/index.php?route=account/register", "https://naveenautomationlabs.com/opencart");
        driver.get(regUrl);
        String title = driver.getTitle();
        System.out.println("Registration page title: " + title);
        // Basic sanity check that the registration page loaded
        Assert.assertTrue(title.toLowerCase().contains("register") || driver.getCurrentUrl().contains("account/register"),
                "Expected to be on registration page but was: " + title + " / " + driver.getCurrentUrl());
    }

    @Test(groups = {"regression"})
    public void doRegistration() {
        try{
        driver.get("https://naveenautomationlabs.com/opencart/index.php?route=account/register");
        registerPage.enterFirstName("John");
        registerPage.enterLastName("Doe");
        registerPage.enterEmail("john.doe@example.com");
        registerPage.enterPhone("1234567890");
        registerPage.enterPassword("Password123");
        registerPage.enterConfirmPassword("Password123");
        registerPage.clickSubscribe();
        registerPage.clickPrivacyPolicy();
        registerPage.clickContinue();   
        } catch (Exception e) {
            Assert.fail("Registration failed due to exception: " + e.getMessage());
        }


        // This method can be implemented to perform the actual registration steps
        // using the RegisterPage object from the Pagefactory
    }

    @Test(groups = {"pkk"})
    public void doRegistration1() {
        try{
        driver.get("https://naveenautomationlabs.com/opencart/index.php?route=account/register");
        registerPage.enterFirstName("John");
        registerPage.enterLastName("Doe");
        registerPage.enterEmail("john.doe@example.com");
        registerPage.enterPhone("1234567890");
        registerPage.enterPassword("Password123");
        registerPage.enterConfirmPassword("Password123");
        registerPage.clickSubscribe();
        registerPage.clickPrivacyPolicy();
        registerPage.clickContinue();   
        } catch (Exception e) {
            Assert.fail("Registration failed due to exception: " + e.getMessage());
        }


        // This method can be implemented to perform the actual registration steps
        // using the RegisterPage object from the Pagefactory
    }

    @AfterClass
    public void cleanup() {
        if (driver != null) {
            driver.quit();
        }
    }
}
