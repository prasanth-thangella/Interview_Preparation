# Selenium Framework - Complete Interview Guide
## Selenium + Java + TestNG + POM + Maven + Sauce Labs + BDD Cucumber

> [!IMPORTANT]
> This guide covers everything you need to know about building and explaining a complete Selenium automation framework for senior-level interviews.

---

## Table of Contents
1. [Selenium WebDriver Fundamentals](#selenium-webdriver-fundamentals)
2. [TestNG Framework](#testng-framework)
3. [Page Object Model (POM)](#page-object-model-pom)
4. [Maven Build Tool](#maven-build-tool)
5. [Parallel Execution](#parallel-execution)
6. [Sauce Labs Integration](#sauce-labs-integration)
7. [BDD with Cucumber](#bdd-with-cucumber)
8. [Complete Framework Architecture](#complete-framework-architecture)

---

# Selenium WebDriver Fundamentals

## 1. WebDriver Architecture

### Theory
Selenium WebDriver is a W3C standard that provides a platform and language-neutral interface for controlling web browsers.

**Architecture Layers:**
```
Test Script (Java)
       ↓
Selenium WebDriver API (Java Bindings)
       ↓
JSON Wire Protocol / W3C WebDriver Protocol
       ↓
Browser Driver (ChromeDriver, GeckoDriver, etc.)
       ↓
Browser (Chrome, Firefox, etc.)
```

### Key Components
- **WebDriver Interface**: Main interface for browser automation
- **Browser Drivers**: Executable files that communicate with browsers
- **Locators**: Strategies to find elements (ID, Name, XPath, CSS, etc.)
- **WebElement**: Represents HTML elements on the page

### Code Example
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverBasics {
    public static void main(String[] args) {
        // Set ChromeDriver path
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        
        // ChromeOptions for configuration
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        
        // Initialize WebDriver
        WebDriver driver = new ChromeDriver(options);
        
        // Navigate to URL
        driver.get("https://example.com");
        
        // Get page title
        String title = driver.getTitle();
        System.out.println("Page Title: " + title);
        
        // Close browser
        driver.quit();
    }
}
```

---

## 2. Locator Strategies

### Theory
Locators are used to identify and interact with web elements.

### Locator Types (Priority Order)
1. **ID** - Most reliable, unique identifier
2. **Name** - Reliable if unique
3. **CSS Selector** - Fast, powerful
4. **XPath** - Flexible, can traverse DOM
5. **LinkText / PartialLinkText** - For links
6. **TagName** - For generic elements
7. **ClassName** - For styled elements

### Examples
```java
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

// ID
WebElement element = driver.findElement(By.id("username"));

// Name
WebElement element = driver.findElement(By.name("email"));

// CSS Selector
WebElement element = driver.findElement(By.cssSelector("input[type='password']"));
WebElement element = driver.findElement(By.cssSelector("#loginBtn"));
WebElement element = driver.findElement(By.cssSelector(".submit-button"));

// XPath
WebElement element = driver.findElement(By.xpath("//input[@id='username']"));
WebElement element = driver.findElement(By.xpath("//button[text()='Login']"));
WebElement element = driver.findElement(By.xpath("//div[@class='header']//a[contains(text(),'Sign In')]"));

// LinkText
WebElement element = driver.findElement(By.linkText("Forgot Password?"));

// PartialLinkText
WebElement element = driver.findElement(By.partialLinkText("Forgot"));

// TagName
List<WebElement> links = driver.findElements(By.tagName("a"));

// ClassName
WebElement element = driver.findElement(By.className("btn-primary"));
```

### XPath Axes (Advanced)
```java
// Parent
driver.findElement(By.xpath("//input[@id='username']/parent::div"));

// Following-sibling
driver.findElement(By.xpath("//label[text()='Username']/following-sibling::input"));

// Ancestor
driver.findElement(By.xpath("//input[@id='username']/ancestor::form"));

// Descendant
driver.findElement(By.xpath("//form/descendant::input[@type='submit']"));
```

---

## 3. Waits Strategy

### Theory
Waits are crucial for handling dynamic web applications where elements load asynchronously.

### Types of Waits

#### 1. Implicit Wait
```java
// Applied globally to all findElement calls
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
```

**Pros**: Simple, applies to all elements  
**Cons**: Not flexible, can slow down tests

#### 2. Explicit Wait
```java
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

// Wait for element to be visible
WebElement element = wait.until(
    ExpectedConditions.visibilityOfElementLocated(By.id("username"))
);

// Wait for element to be clickable
WebElement button = wait.until(
    ExpectedConditions.elementToBeClickable(By.id("submitBtn"))
);

// Wait for text to be present
wait.until(
    ExpectedConditions.textToBePresentInElementLocated(
        By.id("message"), "Success"
    )
);

// Wait for URL to contain
wait.until(ExpectedConditions.urlContains("dashboard"));

// Wait for alert
wait.until(ExpectedConditions.alertIsPresent());
```

**Pros**: Flexible, waits only when needed  
**Cons**: Requires code for each element

#### 3. Fluent Wait
```java
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.NoSuchElementException;

Wait<WebDriver> fluentWait = new FluentWait<>(driver)
    .withTimeout(Duration.ofSeconds(30))
    .pollingEvery(Duration.ofSeconds(2))
    .ignoring(NoSuchElementException.class);

WebElement element = fluentWait.until(driver -> 
    driver.findElement(By.id("dynamicElement"))
);
```

**Pros**: Most flexible, custom polling  
**Cons**: More complex

#### 4. Custom Wait Conditions
```java
public class CustomConditions {
    public static ExpectedCondition<Boolean> elementHasAttribute(
        By locator, String attribute, String value) {
        return driver -> {
            WebElement element = driver.findElement(locator);
            String attrValue = element.getAttribute(attribute);
            return attrValue != null && attrValue.equals(value);
        };
    }
    
    public static ExpectedCondition<Boolean> numberOfElementsToBe(
        By locator, int count) {
        return driver -> {
            List<WebElement> elements = driver.findElements(locator);
            return elements.size() == count;
        };
    }
}

// Usage
wait.until(CustomConditions.elementHasAttribute(
    By.id("status"), "class", "completed"
));
```

### Interview Question
**Q: When to use Implicit vs Explicit wait?**
- **Implicit**: Never recommended in modern frameworks
- **Explicit**: Always use for better control and faster execution
- **Best Practice**: Use explicit waits with custom conditions

---

## 4. WebElement Interactions

### Common Actions
```java
WebElement element = driver.findElement(By.id("username"));

// Input text
element.sendKeys("testuser");

// Clear text
element.clear();

// Click
element.click();

// Get text
String text = element.getText();

// Get attribute
String value = element.getAttribute("value");
String className = element.getAttribute("class");

// Check if displayed
boolean isDisplayed = element.isDisplayed();

// Check if enabled
boolean isEnabled = element.isEnabled();

// Check if selected (checkbox/radio)
boolean isSelected = element.isSelected();

// Submit form
element.submit();
```

### Advanced Interactions (Actions Class)
```java
import org.openqa.selenium.interactions.Actions;

Actions actions = new Actions(driver);

// Hover over element
actions.moveToElement(element).perform();

// Double click
actions.doubleClick(element).perform();

// Right click
actions.contextClick(element).perform();

// Drag and drop
WebElement source = driver.findElement(By.id("draggable"));
WebElement target = driver.findElement(By.id("droppable"));
actions.dragAndDrop(source, target).perform();

// Click and hold
actions.clickAndHold(element).perform();

// Release
actions.release().perform();

// Keyboard actions
actions.sendKeys(Keys.ENTER).perform();
actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform();

// Chain multiple actions
actions
    .moveToElement(menu)
    .pause(Duration.ofSeconds(1))
    .moveToElement(submenu)
    .click()
    .perform();
```

### JavaScript Executor
```java
import org.openqa.selenium.JavascriptExecutor;

JavascriptExecutor js = (JavascriptExecutor) driver;

// Click element
js.executeScript("arguments[0].click();", element);

// Scroll to element
js.executeScript("arguments[0].scrollIntoView(true);", element);

// Scroll to bottom
js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

// Get page title
String title = (String) js.executeScript("return document.title;");

// Highlight element
js.executeScript("arguments[0].style.border='3px solid red'", element);

// Set value directly
js.executeScript("arguments[0].value='test';", element);

// Remove attribute
js.executeScript("arguments[0].removeAttribute('readonly');", element);
```

---

# TestNG Framework

## 1. TestNG Basics

### Theory
TestNG is a testing framework inspired by JUnit but with more powerful features for test configuration, parallel execution, and reporting.

### Annotations Hierarchy
```
@BeforeSuite
    @BeforeTest
        @BeforeClass
            @BeforeMethod
                @Test
            @AfterMethod
        @AfterClass
    @AfterTest
@AfterSuite
```

### Example
```java
import org.testng.annotations.*;

public class TestNGExample {
    
    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Before Suite - Runs once before all tests");
    }
    
    @BeforeTest
    public void beforeTest() {
        System.out.println("Before Test - Runs before <test> tag");
    }
    
    @BeforeClass
    public void beforeClass() {
        System.out.println("Before Class - Runs once before class");
    }
    
    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Before Method - Runs before each @Test");
    }
    
    @Test(priority = 1)
    public void test1() {
        System.out.println("Test 1");
    }
    
    @Test(priority = 2, dependsOnMethods = "test1")
    public void test2() {
        System.out.println("Test 2");
    }
    
    @AfterMethod
    public void afterMethod() {
        System.out.println("After Method - Runs after each @Test");
    }
    
    @AfterClass
    public void afterClass() {
        System.out.println("After Class - Runs once after class");
    }
    
    @AfterTest
    public void afterTest() {
        System.out.println("After Test - Runs after <test> tag");
    }
    
    @AfterSuite
    public void afterSuite() {
        System.out.println("After Suite - Runs once after all tests");
    }
}
```

---

## 2. TestNG Assertions

### Hard Assertions
```java
import org.testng.Assert;

// Equals
Assert.assertEquals(actual, expected);
Assert.assertEquals(actual, expected, "Error message");

// True/False
Assert.assertTrue(condition);
Assert.assertFalse(condition);

// Null
Assert.assertNull(object);
Assert.assertNotNull(object);

// Same
Assert.assertSame(object1, object2);
Assert.assertNotSame(object1, object2);

// Fail
Assert.fail("Test failed intentionally");
```

### Soft Assertions
```java
import org.testng.asserts.SoftAssert;

SoftAssert softAssert = new SoftAssert();

softAssert.assertEquals(actual1, expected1);
softAssert.assertTrue(condition);
softAssert.assertNotNull(object);

// Must call assertAll() at the end
softAssert.assertAll();
```

---

## 3. Data-Driven Testing with DataProvider

### Basic DataProvider
```java
@DataProvider(name = "loginData")
public Object[][] getLoginData() {
    return new Object[][] {
        {"user1@test.com", "password1"},
        {"user2@test.com", "password2"},
        {"user3@test.com", "password3"}
    };
}

@Test(dataProvider = "loginData")
public void testLogin(String email, String password) {
    // Test will run 3 times with different data
    System.out.println("Email: " + email + ", Password: " + password);
}
```

### DataProvider from Excel
```java
import org.apache.poi.ss.usermodel.*;

@DataProvider(name = "excelData")
public Object[][] getExcelData() throws IOException {
    FileInputStream fis = new FileInputStream("testdata.xlsx");
    Workbook workbook = WorkbookFactory.create(fis);
    Sheet sheet = workbook.getSheetAt(0);
    
    int rowCount = sheet.getLastRowNum();
    int colCount = sheet.getRow(0).getLastCellNum();
    
    Object[][] data = new Object[rowCount][colCount];
    
    for (int i = 0; i < rowCount; i++) {
        Row row = sheet.getRow(i + 1);
        for (int j = 0; j < colCount; j++) {
            data[i][j] = row.getCell(j).toString();
        }
    }
    
    workbook.close();
    fis.close();
    
    return data;
}
```

---

## 4. Test Dependencies

### Method Dependencies
```java
@Test
public void login() {
    System.out.println("Login test");
}

@Test(dependsOnMethods = "login")
public void addToCart() {
    System.out.println("Add to cart - depends on login");
}

@Test(dependsOnMethods = "addToCart")
public void checkout() {
    System.out.println("Checkout - depends on addToCart");
}
```

### Group Dependencies
```java
@Test(groups = "smoke")
public void test1() {
    System.out.println("Smoke test 1");
}

@Test(groups = "smoke")
public void test2() {
    System.out.println("Smoke test 2");
}

@Test(dependsOnGroups = "smoke")
public void test3() {
    System.out.println("Depends on smoke group");
}
```

---

## 5. TestNG XML Configuration

### Basic testng.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">
<suite name="Automation Suite">
    <test name="Regression Tests">
        <classes>
            <class name="com.tests.LoginTest"/>
            <class name="com.tests.CheckoutTest"/>
        </classes>
    </test>
</suite>
```

### Advanced testng.xml with Parameters
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">
<suite name="Cross Browser Suite" parallel="tests" thread-count="3">
    
    <parameter name="environment" value="staging"/>
    
    <test name="Chrome Tests">
        <parameter name="browser" value="chrome"/>
        <classes>
            <class name="com.tests.LoginTest"/>
        </classes>
    </test>
    
    <test name="Firefox Tests">
        <parameter name="browser" value="firefox"/>
        <classes>
            <class name="com.tests.LoginTest"/>
        </classes>
    </test>
    
    <test name="Edge Tests">
        <parameter name="browser" value="edge"/>
        <classes>
            <class name="com.tests.LoginTest"/>
        </classes>
    </test>
</suite>
```

### Using Parameters in Test
```java
@Parameters({"browser", "environment"})
@BeforeMethod
public void setup(String browser, String environment) {
    System.out.println("Browser: " + browser);
    System.out.println("Environment: " + environment);
    
    if (browser.equalsIgnoreCase("chrome")) {
        driver = new ChromeDriver();
    } else if (browser.equalsIgnoreCase("firefox")) {
        driver = new FirefoxDriver();
    }
}
```

---

## 6. TestNG Listeners

### ITestListener Implementation
```java
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    
    @Override
    public void onStart(ITestContext context) {
        System.out.println("Test Suite Started: " + context.getName());
    }
    
    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Test Suite Finished: " + context.getName());
    }
    
    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Test Started: " + result.getName());
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test Passed: " + result.getName());
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test Failed: " + result.getName());
        // Take screenshot on failure
        takeScreenshot(result.getName());
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Test Skipped: " + result.getName());
    }
    
    private void takeScreenshot(String testName) {
        // Screenshot logic
    }
}
```

### Using Listener
```java
// Method 1: Annotation
@Listeners(TestListener.class)
public class LoginTest {
    @Test
    public void testLogin() {
        // Test code
    }
}

// Method 2: testng.xml
<suite name="Suite">
    <listeners>
        <listener class-name="com.listeners.TestListener"/>
    </listeners>
    <test name="Test">
        <classes>
            <class name="com.tests.LoginTest"/>
        </classes>
    </test>
</suite>
```

---

## 7. Retry Failed Tests

### IRetryAnalyzer Implementation
```java
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int retryCount = 0;
    private static final int maxRetryCount = 2;
    
    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            System.out.println("Retrying test: " + result.getName() + 
                             " for " + retryCount + " time");
            return true;
        }
        return false;
    }
}
```

### Using Retry Analyzer
```java
@Test(retryAnalyzer = RetryAnalyzer.class)
public void flakyTest() {
    // Test that might fail intermittently
}
```

---

# Page Object Model (POM)

## 1. POM Principles

### Theory
Page Object Model is a design pattern that creates an object repository for web elements, separating test logic from page-specific code.

**Benefits:**
- ✅ Code reusability
- ✅ Easy maintenance
- ✅ Improved readability
- ✅ Reduced code duplication

### Basic Structure
```
src/
├── main/
│   └── java/
│       └── pages/
│           ├── BasePage.java
│           ├── LoginPage.java
│           └── DashboardPage.java
└── test/
    └── java/
        └── tests/
            └── LoginTest.java
```

---

## 2. Page Object Implementation

### BasePage.java
```java
package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    protected void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }
    
    protected void type(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
    }
    
    protected String getText(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.getText();
    }
    
    protected boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
```

### LoginPage.java (Without Page Factory)
```java
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {
    
    // Locators
    private By usernameField = By.id("username");
    private By passwordField = By.id("password");
    private By loginButton = By.id("loginBtn");
    private By errorMessage = By.className("error-msg");
    
    // Constructor
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    
    // Page Actions
    public void enterUsername(String username) {
        WebElement element = driver.findElement(usernameField);
        type(element, username);
    }
    
    public void enterPassword(String password) {
        WebElement element = driver.findElement(passwordField);
        type(element, password);
    }
    
    public void clickLogin() {
        WebElement element = driver.findElement(loginButton);
        click(element);
    }
    
    public String getErrorMessage() {
        WebElement element = driver.findElement(errorMessage);
        return getText(element);
    }
    
    // Combined action
    public DashboardPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        return new DashboardPage(driver);
    }
}
```

---

## 3. Page Factory Pattern

### LoginPage.java (With Page Factory)
```java
package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {
    
    // Page Factory locators
    @FindBy(id = "username")
    private WebElement usernameField;
    
    @FindBy(id = "password")
    private WebElement passwordField;
    
    @FindBy(id = "loginBtn")
    private WebElement loginButton;
    
    @FindBy(className = "error-msg")
    private WebElement errorMessage;
    
    @FindBy(xpath = "//a[text()='Forgot Password?']")
    private WebElement forgotPasswordLink;
    
    // Constructor
    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    // Page Actions
    public LoginPage enterUsername(String username) {
        type(usernameField, username);
        return this;
    }
    
    public LoginPage enterPassword(String password) {
        type(passwordField, password);
        return this;
    }
    
    public DashboardPage clickLogin() {
        click(loginButton);
        return new DashboardPage(driver);
    }
    
    public String getErrorMessage() {
        return getText(errorMessage);
    }
    
    // Fluent interface
    public DashboardPage loginAs(String username, String password) {
        return this.enterUsername(username)
                   .enterPassword(password)
                   .clickLogin();
    }
}
```

### DashboardPage.java
```java
package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DashboardPage extends BasePage {
    
    @FindBy(className = "welcome-msg")
    private WebElement welcomeMessage;
    
    @FindBy(id = "logoutBtn")
    private WebElement logoutButton;
    
    @FindBy(xpath = "//a[text()='Profile']")
    private WebElement profileLink;
    
    public DashboardPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    public String getWelcomeMessage() {
        return getText(welcomeMessage);
    }
    
    public boolean isLogoutButtonDisplayed() {
        return isDisplayed(logoutButton);
    }
    
    public LoginPage logout() {
        click(logoutButton);
        return new LoginPage(driver);
    }
}
```

---

## 4. Test Implementation

### LoginTest.java
```java
package tests;

import org.testng.annotations.*;
import org.testng.Assert;
import pages.LoginPage;
import pages.DashboardPage;

public class LoginTest extends BaseTest {
    
    private LoginPage loginPage;
    
    @BeforeMethod
    public void setupTest() {
        driver.get("https://example.com/login");
        loginPage = new LoginPage(driver);
    }
    
    @Test(priority = 1)
    public void testValidLogin() {
        DashboardPage dashboard = loginPage.loginAs("user@test.com", "password123");
        
        Assert.assertTrue(dashboard.isLogoutButtonDisplayed());
        Assert.assertTrue(dashboard.getWelcomeMessage().contains("Welcome"));
    }
    
    @Test(priority = 2)
    public void testInvalidLogin() {
        loginPage.enterUsername("invalid@test.com");
        loginPage.enterPassword("wrongpass");
        loginPage.clickLogin();
        
        String errorMsg = loginPage.getErrorMessage();
        Assert.assertEquals(errorMsg, "Invalid credentials");
    }
    
    @Test(priority = 3, dataProvider = "loginData")
    public void testLoginWithMultipleUsers(String email, String password, boolean shouldPass) {
        if (shouldPass) {
            DashboardPage dashboard = loginPage.loginAs(email, password);
            Assert.assertTrue(dashboard.isLogoutButtonDisplayed());
        } else {
            loginPage.loginAs(email, password);
            Assert.assertTrue(loginPage.getErrorMessage().length() > 0);
        }
    }
    
    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return new Object[][] {
            {"user1@test.com", "pass123", true},
            {"user2@test.com", "pass456", true},
            {"invalid@test.com", "wrong", false}
        };
    }
}
```

---

# Maven Build Tool

## 1. Maven Basics

### Theory
Maven is a build automation and dependency management tool for Java projects.

**Key Concepts:**
- **POM (pom.xml)**: Project Object Model - configuration file
- **Dependencies**: External libraries
- **Plugins**: Extend Maven functionality
- **Build Lifecycle**: clean, compile, test, package, install, deploy

---

## 2. pom.xml Structure

### Complete pom.xml for Selenium Framework
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.automation</groupId>
    <artifactId>selenium-framework</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>Selenium Automation Framework</name>

    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <selenium.version>4.15.0</selenium.version>
        <testng.version>7.8.0</testng.version>
        <cucumber.version>7.14.0</cucumber.version>
    </properties>

    <dependencies>
        <!-- Selenium WebDriver -->
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-java</artifactId>
            <version>${selenium.version}</version>
        </dependency>

        <!-- TestNG -->
        <dependency>
            <groupId>org.testng</groupId>
            <artifactId>testng</artifactId>
            <version>${testng.version}</version>
        </dependency>

        <!-- Cucumber Java -->
        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-java</artifactId>
            <version>${cucumber.version}</version>
        </dependency>

        <!-- Cucumber TestNG -->
        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-testng</artifactId>
            <version>${cucumber.version}</version>
        </dependency>

        <!-- WebDriverManager -->
        <dependency>
            <groupId>io.github.bonigarcia</groupId>
            <artifactId>webdrivermanager</artifactId>
            <version>5.6.2</version>
        </dependency>

        <!-- Apache POI for Excel -->
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml</artifactId>
            <version>5.2.5</version>
        </dependency>

        <!-- Extent Reports -->
        <dependency>
            <groupId>com.aventstack</groupId>
            <artifactId>extentreports</artifactId>
            <version>5.1.1</version>
        </dependency>

        <!-- Log4j -->
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-core</artifactId>
            <version>2.22.0</version>
        </dependency>

        <!-- JSON -->
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.10.1</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <!-- Maven Compiler Plugin -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.11.0</version>
                <configuration>
                    <source>11</source>
                    <target>11</target>
                </configuration>
            </plugin>

            <!-- Maven Surefire Plugin for TestNG -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.2.2</version>
                <configuration>
                    <suiteXmlFiles>
                        <suiteXmlFile>testng.xml</suiteXmlFile>
                    </suiteXmlFiles>
                    <!-- Parallel execution -->
                    <parallel>methods</parallel>
                    <threadCount>5</threadCount>
                </configuration>
            </plugin>
        </plugins>
    </build>

    <!-- Profiles for different environments -->
    <profiles>
        <profile>
            <id>smoke</id>
            <build>
                <plugins>
                    <plugin>
                        <groupId>org.apache.maven.plugins</groupId>
                        <artifactId>maven-surefire-plugin</artifactId>
                        <configuration>
                            <suiteXmlFiles>
                                <suiteXmlFile>smoke-tests.xml</suiteXmlFile>
                            </suiteXmlFiles>
                        </configuration>
                    </plugin>
                </plugins>
            </build>
        </profile>

        <profile>
            <id>regression</id>
            <build>
                <plugins>
                    <plugin>
                        <groupId>org.apache.maven.plugins</groupId>
                        <artifactId>maven-surefire-plugin</artifactId>
                        <configuration>
                            <suiteXmlFiles>
                                <suiteXmlFile>regression-tests.xml</suiteXmlFile>
                            </suiteXmlFiles>
                        </configuration>
                    </plugin>
                </plugins>
            </build>
        </profile>
    </profiles>
</project>
```

---

## 3. Maven Commands

### Common Commands
```bash
# Clean project
mvn clean

# Compile code
mvn compile

# Run tests
mvn test

# Clean and test
mvn clean test

# Package (create JAR)
mvn package

# Install to local repository
mvn install

# Run specific test suite
mvn test -DsuiteXmlFile=testng.xml

# Run with profile
mvn clean test -Psmoke
mvn clean test -Pregression

# Skip tests
mvn clean install -DskipTests

# Run specific test class
mvn test -Dtest=LoginTest

# Run specific test method
mvn test -Dtest=LoginTest#testValidLogin

# Parallel execution
mvn test -Dparallel=methods -DthreadCount=5

# Generate reports
mvn surefire-report:report
```

---

This is Part 1 of the guide. The content continues with Parallel Execution, Sauce Labs Integration, BDD Cucumber, and Complete Framework Architecture. Would you like me to continue with the remaining sections?
