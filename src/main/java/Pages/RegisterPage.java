package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage {
    
    WebDriver driver;

    public RegisterPage(WebDriver driver) {
        this.driver=driver;
    }

    By fName=By.id("input-firstname");
    By lName=By.id("input-lastname");
    By email=By.id("input-email");
    By phone=By.id("input-telephone");
    By password=By.id("input-password");    
    By confirmPassword=By.id("input-confirm");
    By subscribe =By.xpath("//input[@name='newsletter' and @value='0']");
    By privacyPolicy=By.name("agree");  
    By continueButton=By.xpath("//input[@value='Continue']");


    public void enterFirstName(String firstName) {
        driver.findElement(fName).sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        driver.findElement(lName).sendKeys(lastName);
    }

    public void enterEmail(String emailAddress) {
        driver.findElement(email).sendKeys(emailAddress);
    }

    public void enterPhone(String phoneNumber) {
        driver.findElement(phone).sendKeys(phoneNumber);
    }

    public void enterPassword(String passwordValue) {
        driver.findElement(password).sendKeys(passwordValue);
    }

    public void enterConfirmPassword(String confirmPasswordValue) {
        driver.findElement(confirmPassword).sendKeys(confirmPasswordValue);
    }

    public void clickSubscribe() {
        driver.findElement(subscribe).click();
    }

    public void clickPrivacyPolicy() {
        driver.findElement(privacyPolicy).click();
    }

    public void clickContinue() {
        driver.findElement(continueButton).click();
    }   

    

}
