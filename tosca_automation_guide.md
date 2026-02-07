# Tricentis Tosca Automation - Comprehensive Interview Guide

This guide is designed to provide a deep understanding of Tricentis Tosca, covering core concepts, UI automation, API testing, and Distributed Execution (DEX). It is structured to help you master the tool and excel in interviews.

---

## 1. Introduction to Tricentis Tosca

### What is Tosca?
Tricentis Tosca is a **scriptless, model-based automation tool** used for end-to-end testing of software applications. It supports UI, API, Mobile, Database, and Performance testing.

### Key Features (Interview Keywords)
- **Model-Based Test Automation (MBTA):** Uses a technical representation (model) of the application instead of writing scripts. If the app changes, you update the module, not the test cases.
- **Scriptless:** No coding required (C#/Java) for standard automation.
- **Test Case Design (TCD):** Separates data from test logic.
- **Risk-Based Testing:** Prioritizes tests based on business risk.
- **TBox Engine:** The newer, faster engine for automation (XScan).

### Tosca Workspaces
- **Single User Workspace:** For individual use. Local repository.
- **Multi-User Workspace:** Connected to a common repository (SQL, SQLite, Oracle) for team collaboration. Contains Check-in/Check-out mechanism.

---

## 2. Tosca Commander Architecture (The "Sections")

Tosca Commander is the IDE where you manage and execute tests. It has color-coded sections:

### 1. Requirements (Yellow) - Application Lifecycle Management
- Maps business requirements to test cases.
- Calculates **Requirement Coverage** and **Execution State**.

### 2. Modules (Orange) - The Technical "Map"
- Stores the technical details of the application (Properties of run-time elements).
- **Standard Modules:** Pre-installed modules (TBox Actions, File Ops, Timings).
- **User Defined Modules:** Created by scanning the application (XScan).

### 3. TestCases (Blue) - The "Instruction"
- Where you build the test logic by dragging and dropping Modules.
- **TestStep:** A single action (e.g., Click 'Login').
- **TestStepValue:** The data used (e.g., username="Admin").
- **ActionModes:** Defines what Tosca does with the value (Input, Verify, Buffer, etc.).

### 4. TestCaseDesign (Red) - The "Data"
- Centralized data management.
- Creates **TestSheets** to generate data permutations (Data-driven testing).
- Decouples data from the test logic.

### 5. ExecutionLists (Green) - The "Results"
- Where you run the TestCases (actually "ExecutionEntries").
- Logs pass/fail results and screenshots.

### 6. Issues (Purple) - Defect Tracking
- Can link failed tests to defects (often integrated with Jira).

---

## 3. UI Automation Concepts

### Scanning (XScan)
The process of capturing technical properties of UI elements.
- **Context Menu -> Scan -> Application**.
- **Identify by Properties:** Default (Id, Name, InnerText). *Best Practice: Use stable properties.*
- **Identify by Anchor:** Uses a stable neighbor element to find a dynamic element.
- **Identify by Image:** Uses pixel recognition (Least reliable, use as last resort).
- **Identify by Index:** Selects the nth occurrence (Fragile, avoid if possible).

### ActionModes (Crucial for Interviews)
| ActionMode | Description |
| :--- | :--- |
| **Input** | Enters data or performs an action (Click). |
| **Verify** | Checks if a value/property matches expected result. |
| **Buffer** | Stores a value into a variable for later use. |
| **WaitOn** | Waits for a property to have a specific value. |
| **Select** | Selects a dropdown item or checks a box. |
| **Constraint** | Limits search scope (used in tables/lists). |

### Steering (Synchronization)
- **Static Wait:** `TBox Wait` (Fixed time). *Avoid unless necessary.*
- **Dynamic Wait:** `WaitOn` ActionMode. *Best Practice.*
- **Synchronization Settings:** `Settings -> TBox -> Synchronization` (Global timeout).

### Tables & Lists
- To steer a table, scan the table element.
- Use **Constraint** (e.g., `Row 1`, `Col "Name"`) to find specific cells.
- Use **ResultCount** to verify number of rows.

---

## 4. API Testing in Tosca

Tosca supports REST and SOAP API testing via the **API Scan** tool and **TBox** engine.

### API Scan Workflow
1. **Open API Scan:** From Tosca Commander.
2. **Import Definition:** URL, Swagger, or WSDL.
3. **Configure Request:** Method (GET/POST), Endpoint, Headers (Auth), Payload (Body).
4. **Run:** Execute to see Response.
5. **Export:** Export to Tosca Commander as **API Module** and **TestCase**.

### TBox Automation for API
- **Modules created:** Request and Response modules.
- **Request Module:** Inputs payload, headers, params.
- **Response Module:** Verifies Status Code (e.g., 200), Payload elements (using XPaths/JSONPaths).
- **Dynamic Values:** Use Buffers to extract ID from a POST response and use it in a subsequent GET request.
  - Example: Response Body -> `id` -> Buffer `StoredID`.
  - Next Request -> URL -> `.../users/{B[StoredID]}`.

---

## 5. Advanced Concepts & Best Practices

### Buffers & Business Parameters
- **Buffers:** `XP[BufferName]` (Set), `{B[BufferName]}` (Use). Temporary runtime variables.
- **TCP (Test Configuration Parameters):** Global/Level variables (e.g., `Browser = Chrome`, `Env = QA`).

### Libraries & Reusable TestStepBlocks
- **Libraries:** Container for Reusable TestStepBlocks.
- **Reusable TestStepBlocks:** A chunk of steps (e.g., Login) reused across multiple TestCases. Updates in the block reflect everywhere.

### Recovery Scenarios
- Handles unexpected errors (popups, crashes).
- **Recovery Scenario:** Defined in `Settings` or `TestCollection`.
- **Cleanup Scenario:** Steps to run if a failure occurs (e.g., Close browser).

### TQL (Tosca Query Language)
- SQL-like search within Tosca.
- Example: `=>SUBPARTS:TestCase[Name=="Login"]` (Finds a TestCase named Login).

---

## 6. Distributed Execution (DEX) & CI/CD

### Why DEX?
Executes tests across multiple machines (agents) in parallel or distributed mode to reduce execution time.

### Architecture
1. **Tosca Commander (Client):** Triggers execution.
2. **DEX Server:** The traffic controller. Receives lists and assigns them to agents.
3. **Distribution Agents:** Machines (VMs) that actually run the tests.
4. **Common Repository:** Accessible by all components.

### Setup (Interview Flow)
1. Use **Multi-User Workspace**.
2. Configure **TestEvents** (Bundles ExecutionLists).
3. Check out the project.
4. Press **"Run in Scratchbook"** (for debugging) vs **"Execute on DEX"**.
5. Monitor via **DEX Monitor** web interface.

### CI/CD Integration
- **Tosca CI Client:** A command-line tool (`ToscaCIClient.exe`).
- **Jenkins/Azure DevOps:**
  - Create a pipeline.
  - Call `ToscaCIClient.exe` with arguments:
    - `-m` (Mode: Distributed)
    - `-c` (Configuration: e.g., "Chrome")
    - `-t` (TestEvent name)
  - Jenkins parses the JUnit/XML results generated by Tosca.

---

## 7. Top Interview Questions (Rapid Fire)

**Q: Difference between Buffer and TestSheet?**
A: Buffer is run-time storage (lost after execution manually, unless saved). TestSheet is design-time data storage for data-driven testing.

**Q: How do you handle dynamic IDs?**
A: Identify by Anchor, Identify by Index (last resort), or use wildcard `*` in the ID property if part of it is stable.

**Q: What is the difference between specific and generic items in Lists?**
A: Specific items (Explicit Name) are hardcoded. Generic items (Cell) execute on *all* or *filtered* rows.

**Q: How to execute a test repeatedly?**
A: Use **Repetitions** property on a Folder/TestStep, or use a `While`/`Do` loop standard module.

**Q: Explain the difference between 'Constraint' and 'WaitOn'.**
A: Constraint is used to *find* an element (filter). WaitOn assumes the element is found but waits for a *property* to change (e.g., Status changes to 'Completed').
