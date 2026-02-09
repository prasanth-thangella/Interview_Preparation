# Rest Assured Framework Walkthrough

I have successfully created a Study Roadmap, Theory/Mindmaps, and a Production-Grade Hybrid Automation Framework.

## 1. Study Materials Created
- **[RestAssured_Roadmap.md](file:///C:/Users/prasanth/.gemini/antigravity/brain/8a4756b1-9f9b-4e89-b654-37e4556902b1/RestAssured_Roadmap.md)**: 12-Day plan from basics to expert.
- **[RestAssured_Theory.md](file:///C:/Users/prasanth/.gemini/antigravity/brain/8a4756b1-9f9b-4e89-b654-37e4556902b1/RestAssured_Theory.md)**: Cheat sheet for HTTP, Auth, and Syntax.
- **[RestAssured_Mindmaps.md](file:///C:/Users/prasanth/.gemini/antigravity/brain/8a4756b1-9f9b-4e89-b654-37e4556902b1/RestAssured_Mindmaps.md)**: Visual architecture diagrams.
- **[Reporting_Setup_Guide.md](file:///C:/Users/prasanth/.gemini/antigravity/brain/8a4756b1-9f9b-4e89-b654-37e4556902b1/Reporting_Setup_Guide.md)**: How to generate Allure/Extent reports.

## 2. Framework Implementation
The framework is generated in `d:\InterviewPrep_antigravity\RestAssured_Mastery_Framework`.

### Architecture
- **Tech Stack**: Java 11, Rest Assured 5.3, TestNG 7.8, Lombok, Jackson, Extent Reports 5, Allure.
- **Design Pattern**:
    - **Singleton**: `ConfigManager` for properties.
    - **Wrapper**: `RestResource` helper to wrap Rest Assured calls.
    - **POJO**: JSON mapping using Jackson & Lombok.
    - **Listeners**: Auto-generating reports on test failure/success.

### Verification Results
I executed the sample CRUD suite against **JSONPlaceholder API** (switched from ReqRes due to 403 blocks).

```
Running TestSuite...
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
```

### Key Files Implemented
- **BaseTest**: Setup Allure/Extent listeners.
- **RestResource**: Handles `given().spec()...` logic to avoid code duplication.
- **ConfigManager**: Reads `base.uri` from `config.properties`.
- **Post**: POJO class for request/response mapping.

## 3. How to Run
1. Open Command Prompt / Terminal.
2. Navigate to: `d:\InterviewPrep_antigravity\RestAssured_Mastery_Framework`
3. Run: `mvn clean test`
4. Check Reports:
    - **Extent**: `target/extent-report.html`
    - **Allure**: Run `allure serve target/allure-results` (requires Allure CLI).
