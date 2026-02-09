# Rest Assured Transformation Roadmap

This roadmap is designed to take you from a beginner to an expert in Rest Assured for Automation Interviews.

## Phase 1: Foundations (Days 1-2)
### Goal: Understand API Basics & Rest Assured Syntax
- [ ] **API Concepts**:
    - HTTP Methods (GET, POST, PUT, DELETE, PATCH)
    - Status Codes (2xx, 3xx, 4xx, 5xx)
    - Headers vs Payload
    - JSON vs XML
- [ ] **Rest Assured Setup**:
    - Maven Dependencies (rest-assured, testng, hamcrest, jackson)
    - Static Imports (`io.restassured.RestAssured.*`)
- [ ] **Hello World**:
    - Writing your first GET request
    - Understanding BDD Syntax (`given()`, `when()`, `then()`)

## Phase 2: Core Validation Mastery (Days 3-4)
### Goal: Validate any response effectively
- [ ] **Response Validation**:
    - Status Code & Time
    - Headlines
    - Body validation using **Hamcrest Matchers** (`equalTo`, `hasItems`, `containsString`)
- [ ] **JsonPath (GPath)**:
    - Extracting single values
    - Extracting lists
    - Filtering specific nodes
- [ ] **Advanced Validation**:
    - Validating complex nested JSON
    - JsonSchema Validation (classpath file)

## Phase 3: Request Payload Handling (Days 5-6)
### Goal: handle dynamic request bodies
- [ ] **Ways to send payload**:
    - String
    - HashMap (Map<String, Object>)
    - Using POJO (Plain Old Java Object) **(CRITICAL FOR INTERVIEWS)**
    - External JSON file
- [ ] **Serialization & Deserialization**:
    - Object Mapper (Jackson / Gson)
    - Converting Response to POJO

## Phase 4: Authentication & Security (Day 7)
### Goal: Handle secured APIs
- [ ] **Auth Types**:
    - Basic Auth
    - Bearer Token
    - OAuth 2.0 (Get Access Token -> Use Token)
    - API Key
    - Digest Auth
    - CSRF Tokens (Form based)

## Phase 5: Framework Design (Days 8-10)
### Goal: Build a scalable Hybrid Framework
- [ ] **Components**:
    - `BaseTest`: Setup/Teardown, Suite config
    - `ConfigManager`: Property file reader
    - `APIUtils`: Reusable request methods
    - `Endpoints`: Constant routes
    - `PayloadManager`: Dynamic payload builder
- [ ] **Design Patterns**:
    - Singleton Pattern (for Config)
    - Builder Pattern (for Request Spec)
    - Data Driven Testing (DataProvider, Excel)

## Phase 6: Reporting & CI/CD (Days 11-12)
### Goal: Professional Reporting
- [ ] **Logging**:
    - Request/Response Logging
    - Log4j2 integration
- [ ] **Reporting**:
    - Extent Reports 5 (SparkReporter)
    - Allure Reports (Annotations, Steps, Attachment)
- [ ] **CI/CD**:
    - Maven profiles
    - Jenkins integration (Goals: `clean test`)
    - Docker containerization (Basic)

## Interview Preparation Checklist
- [ ] Explain the difference between `Put` and `Patch`.
- [ ] How to handle dynamic auth tokens in a framework?
- [ ] Explain Serialization vs Deserialization.
- [ ] How do you handle SSL certificate issues? (`relaxedHTTPSValidation`)
- [ ] How to pass data between tests? (ITestContext or Static maps)
