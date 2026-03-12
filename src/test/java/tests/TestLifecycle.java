package tests;

import Pages.Basepage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

public class TestLifecycle {

    @BeforeMethod(alwaysRun = true)
    public void ensureDriverForTest() {
        // If a test didn't initialize the driver in its @BeforeMethod/@BeforeClass, make sure one exists
        if (Basepage.driver == null) {
            new Basepage().setUp();
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownAfterTest() {
        // Close browser after each test method to ensure isolation
        Basepage.quitDriver();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDownSuite() {
        // Ensure shared driver is closed at the end of the suite as a fallback
        Basepage.quitDriver();
    }
}
