# Selenium Framework Guide - Part 2
## Parallel Execution + Sauce Labs + BDD Cucumber + Complete Architecture

---

# Parallel Execution

## 1. Thread Safety Concepts

### Theory
When running tests in parallel, each thread must have its own WebDriver instance to avoid conflicts.

**Problem without Thread Safety:**
```java
public class DriverManager {
    private static WebDriver driver; // WRONG - shared across threads
    
    public static WebDriver getDriver() {
        if (driver == null) {
            driver = new ChromeDriver();
        }
        return driver;
    }
}
// Multiple threads will share the same driver instance!
```

**Solution with ThreadLocal:**
```java
public class DriverManager {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    
    public static void setDriver(WebDriver driverInstance) {
        driver.set(driverInstance);
    }
    
    public static WebDriver getDriver() {
        return driver.get();
    }
    
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
```

---

## 2. ThreadLocal WebDriver Implementation

### Complete DriverFactory.java
```java
package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {
    
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    
    public static WebDriver getDriver() {
        return driver.get();
    }
    
    public static void setDriver(String browser) {
        WebDriver driverInstance = null;
        
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--disable-notifications");
                driverInstance = new ChromeDriver(chromeOptions);
                break;
                
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--start-maximized");
                driverInstance = new FirefoxDriver(firefoxOptions);
                break;
                
            case "edge":
                WebDriverManager.edgedriver().setup();
                driverInstance = new EdgeDriver();
                break;
                
            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }
        
        driver.set(driverInstance);
    }
    
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
```

### BaseTest.java with ThreadLocal
```java
package tests;

import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;
import utils.DriverFactory;

public class BaseTest {
    
    protected WebDriver driver;
    
    @Parameters("browser")
    @BeforeMethod
    public void setup(@Optional("chrome") String browser) {
        DriverFactory.setDriver(browser);
        driver = DriverFactory.getDriver();
        driver.get("https://example.com");
    }
    
    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
```

---

## 3. TestNG Parallel Execution

### Parallel Modes in testng.xml

#### 1. Parallel by Tests
```xml
<suite name="Parallel Suite" parallel="tests" thread-count="3">
    <test name="Chrome Test">
        <parameter name="browser" value="chrome"/>
        <classes>
            <class name="com.tests.LoginTest"/>
        </classes>
    </test>
    
    <test name="Firefox Test">
        <parameter name="browser" value="firefox"/>
        <classes>
            <class name="com.tests.LoginTest"/>
        </classes>
    </test>
    
    <test name="Edge Test">
        <parameter name="browser" value="edge"/>
        <classes>
            <class name="com.tests.LoginTest"/>
        </classes>
    </test>
</suite>
```

#### 2. Parallel by Classes
```xml
<suite name="Parallel Suite" parallel="classes" thread-count="5">
    <test name="Regression Tests">
        <classes>
            <class name="com.tests.LoginTest"/>
            <class name="com.tests.CheckoutTest"/>
            <class name="com.tests.ProfileTest"/>
            <class name="com.tests.SearchTest"/>
            <class name="com.tests.CartTest"/>
        </classes>
    </test>
</suite>
```

#### 3. Parallel by Methods
```xml
<suite name="Parallel Suite" parallel="methods" thread-count="10">
    <test name="All Tests">
        <classes>
            <class name="com.tests.LoginTest"/>
            <class name="com.tests.CheckoutTest"/>
        </classes>
    </test>
</suite>
```

#### 4. Parallel by Instances
```xml
<suite name="Parallel Suite" parallel="instances" thread-count="5">
    <test name="Tests">
        <classes>
            <class name="com.tests.LoginTest"/>
        </classes>
    </test>
</suite>
```

---

## 4. Data Isolation in Parallel Execution

### Thread-Safe Test Data Manager
```java
package utils;

import java.util.HashMap;
import java.util.Map;

public class TestDataManager {
    
    private static ThreadLocal<Map<String, Object>> testData = 
        ThreadLocal.withInitial(HashMap::new);
    
    public static void set(String key, Object value) {
        testData.get().put(key, value);
    }
    
    public static Object get(String key) {
        return testData.get().get(key);
    }
    
    public static void clear() {
        testData.get().clear();
        testData.remove();
    }
}

// Usage in tests
TestDataManager.set("username", "testuser@example.com");
String username = (String) TestDataManager.get("username");
```

---

# Sauce Labs Integration

## 1. Sauce Labs Basics

### Theory
Sauce Labs is a cloud-based platform for running automated tests on multiple browsers, OS, and devices without maintaining local infrastructure.

**Benefits:**
- ✅ No local setup required
- ✅ Access to 1000+ browser/OS combinations
- ✅ Parallel execution (based on concurrency limit)
- ✅ Video recording and screenshots
- ✅ Detailed logs and reports

---

## 2. Sauce Labs Configuration

### SauceLabsDriver.java
```java
package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SauceLabsDriver {
    
    private static final String SAUCE_USERNAME = System.getenv("SAUCE_USERNAME");
    private static final String SAUCE_ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");
    private static final String SAUCE_URL = "https://ondemand.us-west-1.saucelabs.com:443/wd/hub";
    
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ThreadLocal<String> sessionId = new ThreadLocal<>();
    
    public static WebDriver createDriver(String browser, String browserVersion, 
                                        String platform, String testName) {
        try {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("browserName", browser);
            caps.setCapability("browserVersion", browserVersion);
            caps.setCapability("platformName", platform);
            
            // Sauce Labs specific options
            Map<String, Object> sauceOptions = new HashMap<>();
            sauceOptions.put("username", SAUCE_USERNAME);
            sauceOptions.put("accessKey", SAUCE_ACCESS_KEY);
            sauceOptions.put("name", testName);
            sauceOptions.put("build", "Build-" + System.currentTimeMillis());
            sauceOptions.put("tags", new String[]{"regression", "parallel"});
            sauceOptions.put("recordVideo", true);
            sauceOptions.put("recordScreenshots", true);
            sauceOptions.put("maxDuration", 3600);
            sauceOptions.put("commandTimeout", 300);
            sauceOptions.put("idleTimeout", 90);
            
            caps.setCapability("sauce:options", sauceOptions);
            
            URL url = new URL(SAUCE_URL);
            RemoteWebDriver remoteDriver = new RemoteWebDriver(url, caps);
            
            driver.set(remoteDriver);
            sessionId.set(remoteDriver.getSessionId().toString());
            
            return remoteDriver;
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to create Sauce Labs driver", e);
        }
    }
    
    public static WebDriver getDriver() {
        return driver.get();
    }
    
    public static String getSessionId() {
        return sessionId.get();
    }
    
    public static void updateTestStatus(boolean passed) {
        if (driver.get() != null) {
            ((RemoteWebDriver) driver.get()).executeScript(
                "sauce:job-result=" + (passed ? "passed" : "failed")
            );
        }
    }
    
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
            sessionId.remove();
        }
    }
}
```

---

## 3. Sauce Labs Test Implementation

### BaseTest with Sauce Labs
```java
package tests;

import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import utils.SauceLabsDriver;
import java.lang.reflect.Method;

public class SauceLabsBaseTest {
    
    protected WebDriver driver;
    
    @Parameters({"browser", "browserVersion", "platform"})
    @BeforeMethod
    public void setup(String browser, String browserVersion, 
                     String platform, Method method) {
        String testName = method.getName();
        driver = SauceLabsDriver.createDriver(browser, browserVersion, 
                                             platform, testName);
        driver.get("https://example.com");
    }
    
    @AfterMethod
    public void tearDown(ITestResult result) {
        boolean passed = result.isSuccess();
        SauceLabsDriver.updateTestStatus(passed);
        
        String sessionId = SauceLabsDriver.getSessionId();
        System.out.println("Sauce Labs Session: https://app.saucelabs.com/tests/" + sessionId);
        
        SauceLabsDriver.quitDriver();
    }
}
```

### testng-saucelabs.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">
<suite name="Sauce Labs Parallel Suite" parallel="tests" thread-count="5">
    
    <test name="Chrome-Windows">
        <parameter name="browser" value="chrome"/>
        <parameter name="browserVersion" value="latest"/>
        <parameter name="platform" value="Windows 10"/>
        <classes>
            <class name="com.tests.LoginTest"/>
        </classes>
    </test>
    
    <test name="Firefox-Windows">
        <parameter name="browser" value="firefox"/>
        <parameter name="browserVersion" value="latest"/>
        <parameter name="platform" value="Windows 10"/>
        <classes>
            <class name="com.tests.LoginTest"/>
        </classes>
    </test>
    
    <test name="Safari-Mac">
        <parameter name="browser" value="safari"/>
        <parameter name="browserVersion" value="latest"/>
        <parameter name="platform" value="macOS 12"/>
        <classes>
            <class name="com.tests.LoginTest"/>
        </classes>
    </test>
    
    <test name="Edge-Windows">
        <parameter name="browser" value="MicrosoftEdge"/>
        <parameter name="browserVersion" value="latest"/>
        <parameter name="platform" value="Windows 11"/>
        <classes>
            <class name="com.tests.LoginTest"/>
        </classes>
    </test>
    
    <test name="Chrome-Android">
        <parameter name="browser" value="chrome"/>
        <parameter name="browserVersion" value="latest"/>
        <parameter name="platform" value="Android"/>
        <classes>
            <class name="com.tests.LoginTest"/>
        </classes>
    </test>
</suite>
```

---

## 4. Sauce Connect for Local Testing

### Theory
Sauce Connect creates a secure tunnel between Sauce Labs and your local environment, allowing tests to access applications running on localhost.

### Setup Sauce Connect
```bash
# Download Sauce Connect
# https://docs.saucelabs.com/secure-connections/sauce-connect/

# Start tunnel
sc -u YOUR_USERNAME -k YOUR_ACCESS_KEY -i tunnel-name

# In tests, use localhost URLs
driver.get("http://localhost:8080/myapp");
```

### Sauce Connect in Capabilities
```java
Map<String, Object> sauceOptions = new HashMap<>();
sauceOptions.put("tunnelIdentifier", "tunnel-name");
caps.setCapability("sauce:options", sauceOptions);
```

---

# BDD with Cucumber

## 1. Cucumber Basics

### Theory
Cucumber is a BDD (Behavior-Driven Development) framework that uses Gherkin syntax to write test scenarios in plain English.

**Key Components:**
- **Feature Files**: Written in Gherkin (.feature)
- **Step Definitions**: Java code that implements steps
- **Hooks**: Setup and teardown methods
- **Tags**: Organize and filter scenarios
- **Test Runner**: Execute feature files

---

## 2. Gherkin Syntax

### Feature File Structure
```gherkin
# login.feature
Feature: User Login
  As a registered user
  I want to login to the application
  So that I can access my account

  Background:
    Given I am on the login page

  @smoke @regression
  Scenario: Successful login with valid credentials
    When I enter username "user@test.com"
    And I enter password "password123"
    And I click the login button
    Then I should see the dashboard
    And I should see welcome message "Welcome, User"

  @regression
  Scenario: Failed login with invalid credentials
    When I enter username "invalid@test.com"
    And I enter password "wrongpass"
    And I click the login button
    Then I should see error message "Invalid credentials"
    And I should remain on the login page

  @smoke
  Scenario Outline: Login with multiple users
    When I enter username "<username>"
    And I enter password "<password>"
    And I click the login button
    Then I should see "<result>"

    Examples:
      | username          | password    | result                |
      | user1@test.com    | pass123     | Dashboard             |
      | user2@test.com    | pass456     | Dashboard             |
      | invalid@test.com  | wrong       | Invalid credentials   |
```

---

## 3. Step Definitions

### LoginSteps.java
```java
package stepdefinitions;

import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.LoginPage;
import pages.DashboardPage;
import utils.DriverFactory;

public class LoginSteps {
    
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    
    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        DriverFactory.getDriver().get("https://example.com/login");
        loginPage = new LoginPage(DriverFactory.getDriver());
    }
    
    @When("I enter username {string}")
    public void i_enter_username(String username) {
        loginPage.enterUsername(username);
    }
    
    @When("I enter password {string}")
    public void i_enter_password(String password) {
        loginPage.enterPassword(password);
    }
    
    @When("I click the login button")
    public void i_click_the_login_button() {
        dashboardPage = loginPage.clickLogin();
    }
    
    @Then("I should see the dashboard")
    public void i_should_see_the_dashboard() {
        Assert.assertTrue(dashboardPage.isLogoutButtonDisplayed());
    }
    
    @Then("I should see welcome message {string}")
    public void i_should_see_welcome_message(String expectedMessage) {
        String actualMessage = dashboardPage.getWelcomeMessage();
        Assert.assertTrue(actualMessage.contains(expectedMessage));
    }
    
    @Then("I should see error message {string}")
    public void i_should_see_error_message(String expectedError) {
        String actualError = loginPage.getErrorMessage();
        Assert.assertEquals(actualError, expectedError);
    }
    
    @Then("I should remain on the login page")
    public void i_should_remain_on_the_login_page() {
        String currentUrl = DriverFactory.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/login"));
    }
    
    @Then("I should see {string}")
    public void i_should_see(String expectedText) {
        String pageSource = DriverFactory.getDriver().getPageSource();
        Assert.assertTrue(pageSource.contains(expectedText));
    }
}
```

---

## 4. Cucumber Hooks

### Hooks.java
```java
package stepdefinitions;

import io.cucumber.java.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utils.DriverFactory;

public class Hooks {
    
    @Before
    public void beforeScenario(Scenario scenario) {
        System.out.println("Starting scenario: " + scenario.getName());
        DriverFactory.setDriver("chrome");
    }
    
    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            // Take screenshot on failure
            byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver())
                .getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());
        }
        
        DriverFactory.quitDriver();
        System.out.println("Finished scenario: " + scenario.getName());
    }
    
    @Before("@smoke")
    public void beforeSmokeTest() {
        System.out.println("Running smoke test");
    }
    
    @After("@regression")
    public void afterRegressionTest() {
        System.out.println("Completed regression test");
    }
    
    @BeforeStep
    public void beforeStep() {
        // Runs before each step
    }
    
    @AfterStep
    public void afterStep(Scenario scenario) {
        // Runs after each step
        // Can take screenshot after each step if needed
    }
}
```

---

## 5. Data Tables in Cucumber

### Feature with Data Table
```gherkin
Scenario: Register user with details
  When I register a new user with following details:
    | Field       | Value              |
    | FirstName   | John               |
    | LastName    | Doe                |
    | Email       | john@test.com      |
    | Password    | Pass@123           |
    | Phone       | 1234567890         |
  Then registration should be successful
```

### Step Definition for Data Table
```java
@When("I register a new user with following details:")
public void i_register_new_user(io.cucumber.datatable.DataTable dataTable) {
    Map<String, String> userData = dataTable.asMap(String.class, String.class);
    
    registrationPage.enterFirstName(userData.get("FirstName"));
    registrationPage.enterLastName(userData.get("LastName"));
    registrationPage.enterEmail(userData.get("Email"));
    registrationPage.enterPassword(userData.get("Password"));
    registrationPage.enterPhone(userData.get("Phone"));
    registrationPage.clickRegister();
}

// Alternative: List of Maps
@When("I add multiple products:")
public void i_add_multiple_products(DataTable dataTable) {
    List<Map<String, String>> products = dataTable.asMaps(String.class, String.class);
    
    for (Map<String, String> product : products) {
        String name = product.get("ProductName");
        String quantity = product.get("Quantity");
        cartPage.addProduct(name, Integer.parseInt(quantity));
    }
}
```

---

## 6. Cucumber Test Runner

### TestRunner.java
```java
package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"stepdefinitions"},
    tags = "@smoke or @regression",
    plugin = {
        "pretty",
        "html:target/cucumber-reports/cucumber.html",
        "json:target/cucumber-reports/cucumber.json",
        "junit:target/cucumber-reports/cucumber.xml",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
    },
    monochrome = true,
    dryRun = false
)
public class TestRunner extends AbstractTestNGCucumberTests {
    
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
```

### Cucumber Options Explained
- **features**: Path to feature files
- **glue**: Package containing step definitions
- **tags**: Filter scenarios by tags
- **plugin**: Report formats
- **monochrome**: Readable console output
- **dryRun**: Check if all steps have definitions without running tests
- **parallel**: Enable parallel execution

---

## 7. Running Cucumber Tests

### Maven Command
```bash
# Run all tests
mvn test -Dcucumber.filter.tags="@smoke"

# Run specific tags
mvn test -Dcucumber.filter.tags="@smoke and @regression"

# Exclude tags
mvn test -Dcucumber.filter.tags="not @wip"

# Parallel execution
mvn test -Ddataproviderthreadcount=5
```

---

# Complete Framework Architecture

## Project Structure
```
selenium-framework/
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── pages/
│   │       │   ├── BasePage.java
│   │       │   ├── LoginPage.java
│   │       │   └── DashboardPage.java
│   │       ├── utils/
│   │       │   ├── DriverFactory.java
│   │       │   ├── SauceLabsDriver.java
│   │       │   ├── ConfigReader.java
│   │       │   ├── ExcelReader.java
│   │       │   └── ExtentManager.java
│   │       └── constants/
│   │           └── FrameworkConstants.java
│   └── test/
│       ├── java/
│       │   ├── tests/
│       │   │   ├── BaseTest.java
│       │   │   ├── LoginTest.java
│       │   │   └── CheckoutTest.java
│       │   ├── stepdefinitions/
│       │   │   ├── Hooks.java
│       │   │   └── LoginSteps.java
│       │   ├── runners/
│       │   │   └── TestRunner.java
│       │   └── listeners/
│       │       ├── TestListener.java
│       │       └── RetryAnalyzer.java
│       └── resources/
│           ├── features/
│           │   └── login.feature
│           ├── config/
│           │   └── config.properties
│           └── testdata/
│               └── testdata.xlsx
├── testng.xml
├── testng-parallel.xml
├── testng-saucelabs.xml
└── pom.xml
```

---

## Interview Questions & Answers

### Q1: Explain your framework architecture
**Answer:**
"Our framework follows a hybrid approach combining Page Object Model with BDD Cucumber. The architecture has:

1. **Page Layer**: Page Object classes with Page Factory for element management
2. **Test Layer**: TestNG test classes and Cucumber step definitions
3. **Utility Layer**: DriverFactory with ThreadLocal for parallel execution, ConfigReader, ExcelReader
4. **Reporting**: Extent Reports integrated with Cucumber
5. **CI/CD**: Maven for build management, Jenkins for execution
6. **Cloud Integration**: Sauce Labs for cross-browser testing

We use ThreadLocal pattern for thread-safe parallel execution and maintain data isolation across threads."

### Q2: How do you handle parallel execution?
**Answer:**
"We use TestNG's parallel execution with ThreadLocal pattern:

1. **ThreadLocal WebDriver**: Each thread gets its own driver instance
2. **TestNG Configuration**: parallel='methods' or 'tests' with thread-count
3. **Data Isolation**: ThreadLocal for test data management
4. **Sauce Labs**: Parallel execution on cloud with concurrency limits
5. **Synchronization**: Proper waits and no shared state

Example: 100 tests running with thread-count=10 reduces execution from 5 hours to 30 minutes."

### Q3: Explain Page Object Model vs Page Factory
**Answer:**
"**Page Object Model (POM)**:
- Locators defined as By objects
- Elements found at runtime using driver.findElement()
- More flexible, better for dynamic elements

**Page Factory**:
- Uses @FindBy annotations
- Elements initialized using PageFactory.initElements()
- Lazy initialization with proxies
- Cleaner syntax but less flexible

I prefer POM for complex scenarios and Page Factory for stable pages."

### Q4: How do you integrate with Sauce Labs?
**Answer:**
"Sauce Labs integration involves:

1. **RemoteWebDriver**: Connect to Sauce Labs hub URL
2. **Desired Capabilities**: Browser, version, platform, sauce:options
3. **Authentication**: Username and access key from environment variables
4. **Test Status**: Update pass/fail using executeScript
5. **Parallel Execution**: Multiple tests run simultaneously based on concurrency
6. **Sauce Connect**: Tunnel for testing localhost applications

We parameterize browser/platform in testng.xml for cross-browser testing."

### Q5: Explain Cucumber hooks and their execution order
**Answer:**
"Cucumber hooks execution order:

1. **@BeforeAll**: Once before all scenarios (TestNG @BeforeSuite)
2. **@Before**: Before each scenario
3. **@Before('tag')**: Tagged hooks
4. **@BeforeStep**: Before each step
5. **Scenario steps execute**
6. **@AfterStep**: After each step
7. **@After('tag')**: Tagged hooks
8. **@After**: After each scenario (screenshot on failure)
9. **@AfterAll**: Once after all scenarios

We use @After hook for screenshot capture and driver cleanup."

---

This completes the Selenium Framework guide. Would you like me to create the visual mind map now?
