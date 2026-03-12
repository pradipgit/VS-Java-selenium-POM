package Pagefactory;
import org.openqa.selenium.WebDriver;

import Pages.RegisterPage;

public class Pagefactory {

WebDriver driver;
public RegisterPage registerPage;

public Pagefactory(WebDriver driver) {
    this.driver=driver;
}

public void setup(WebDriver driver) {
     registerPage= new RegisterPage(driver);
}


public void tearDown() {
    if (driver != null) {
        driver.quit();
    }
    
    
}
    
}
