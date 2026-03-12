# Selenium TestNG Maven Project

This is a minimal Maven project scaffold configured for Selenium WebDriver tests using TestNG.

Files added:
- `pom.xml` — Maven configuration with Selenium, TestNG, and WebDriverManager.
- `testng.xml` — TestNG suite to run tests.
- `src/test/java/tests/SampleTest.java` — A sample headless Chrome test that opens example.com.

How to run

1. From the project root run:

```powershell
mvn test
```

Run by TestNG group (via Surefire):

```powershell
# Run only smoke tests
mvn -Dgroups=smoke test

# Run regression tests
mvn -Dgroups=regression test

# Run multiple groups (smoke OR ui)
mvn -Dgroups="smoke,ui" test
```

You can also control groups inside `testng.xml` by adding `<groups>` sections and include/exclude tags for more complex suites.

Pre-built TestNG suites
Two handy suite files are included at the project root:

- `testng-smoke.xml` — runs all tests tagged with the `smoke` group across the `tests` package.
- `testng-regression.xml` — runs all tests tagged with the `regression` group across the `tests` package.

Run a suite with Maven (Surefire will pick up the suite you specify):
Allure reporting
----------------
Allure is integrated. After running tests, generate the Allure report with:

```powershell
# Run tests (example)
mvn test

# Generate Allure report
mvn io.qameta.allure:allure-maven:report

# Or generate and open the report (if you have allure binary installed):
mvn io.qameta.allure:allure-maven:report && allure open target/site/allure-maven/index.html
```

Test results will be written under `target/site/allure-maven` by the plugin.

```powershell
# Run the smoke suite
mvn -Dsurefire.suiteXmlFiles=testng-smoke.xml test

# Run the regression suite
mvn -Dsurefire.suiteXmlFiles=testng-regression.xml test
```

2. To run with visible browser UI, edit `SampleTest.java` and remove or comment out the headless option:

```java
options.addArguments("--headless=new");
```

Notes

- Java 11+ is used in the POM. Adjust `maven.compiler.source`/`target` if you use a different JDK.
- WebDriverManager will automatically download the appropriate ChromeDriver binary.
