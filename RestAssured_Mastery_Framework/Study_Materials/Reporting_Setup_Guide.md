# Reporting Setup Guide

This framework supports two types of reporting:
1. **Extent Reports 5 (Spark)** - Single HTML file, great for sharing.
2. **Allure Reports** - Interactive web-based report, industry standard.

## 1. Extent Reports
### Configuration
- Dependency: `com.aventstack:extentreports:5.1.1`
- Listener: `com.automation.reporting.SetupListener`
- Location: The report is automatically generated at `target/extent-report.html` after every run.

### How to View
1. Run tests using `mvn test` or via TestNG XML.
2. Open `target/extent-report.html` in any browser.

## 2. Allure Reports
### Configuration
- Dependency: `io.qameta.allure:allure-testng:2.24.0`
- Listener: `AllureRestAssured` filter added in `BaseTest`.

### Prerequisites
You need **Allure Commandline** installed.
- **Mac**: `brew install allure`
- **Windows**: Scoop (`scoop install allure`) or download from [Maven Central](https://repo.maven.apache.org/maven2/io/qameta/allure/allure-commandline/).

### How to Generate
1. Run tests: `mvn clean test`
    - This generates `allure-results` folder in the project root.
2. Serve the report (Temporary Web Server):
    ```bash
    allure serve allure-results
    ```
    *This will automatically open the report in your default browser.*

3. Generate Static Report (For CI/CD):
    ```bash
    allure generate allure-results --clean -o allure-report
    ```
    - Open `allure-report/index.html`.

## 3. CI/CD Integration (Jenkins)
### For Extent Results:
- Use "HTML Publisher Plugin".
- Point to `target/extent-report.html`.

### For Allure Results:
- Use "Allure Jenkins Plugin".
- Point to `allure-results` folder.
